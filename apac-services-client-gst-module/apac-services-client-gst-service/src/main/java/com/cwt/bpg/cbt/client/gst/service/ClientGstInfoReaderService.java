package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.OrgType;
import com.cwt.bpg.cbt.client.gst.model.ValidationError;
import com.cwt.bpg.cbt.exceptions.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static com.cwt.bpg.cbt.client.gst.service.Constants.*;

public abstract class ClientGstInfoReaderService<W extends Closeable, S extends Iterable<R>, R> {

    private static final int ROWS_TO_SKIP_GST_DATA = 2;

    private static final String REPLACE_WITH_SPACE_REGEX = "[\\s\\p{Z}]";
    private static final String REPLACE_WITH_EMPTY_STRING_REGEX = "[\\p{Cf}]";
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    private Logger logger;

    public ClientGstInfoReaderService() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Autowired
    private ClientGstInfoListValidatorService validatorService;

    protected abstract W createWorkbook(InputStream inputStream);

    protected abstract S createSheet(W workbook);

    protected abstract String getValue(R row, int index);

    public List<ClientGstInfo> readFile(InputStream inputStream, boolean validate)
            throws Exception {
        if(inputStream == null) {
            return null;
        }
        try(W workbook = createWorkbook(new BufferedInputStream(inputStream))) {
            return extractClientGstInfo(workbook, validate);
        }
    }

    private List<ClientGstInfo> extractClientGstInfo(W workbook, boolean validate)
            throws FileUploadException {
        S sheet = createSheet(workbook);
        List<ClientGstInfo> clientGstInfo = new LinkedList<>();
        List<ValidationError> validationErrors = new LinkedList<>();
        int rowIteration = 0;
        for(R row: sheet) {
            if (++rowIteration <= ROWS_TO_SKIP_GST_DATA) {
                continue;
            }
            ClientGstInfo info = extractFromRow(row);
            if(info.allValuesNull()) {
                break;
            }
            clientGstInfo.add(info);
            if(validate) {
                validatorService.validate(rowIteration, info, validationErrors);
            }
        }
        if(!validationErrors.isEmpty()) {
            throw new FileUploadException("Validation error", validationErrors);
        }
        return clientGstInfo;
    }

    private ClientGstInfo extractFromRow(R row) {
        ClientGstInfo info = new ClientGstInfo();
        info.setClient(getAndCleanValue(row, CLIENT_INDEX));
        info.setGstin(getAndCleanValue(row, GSTIN_INDEX));
        String gstin = info.getGstin();
        if (!StringUtils.isEmpty(gstin)) {
            info.setGstin(gstin.toUpperCase());
        }
        info.setClientEntityName(getAndCleanValue(row, ENTITY_NAME_INDEX));
        info.setBusinessPhoneNumber(getAndCleanValue(row, BUSINESS_PHONE_INDEX));
        info.setBusinessEmailAddress(getAndCleanValue(row, EMAIL_ADDRESS_INDEX));
        info.setEntityAddressLine1(getAndCleanValue(row, ADDRESS_LINE_1_INDEX));
        info.setEntityAddressLine2(getAndCleanValue(row, ADDRESS_LINE2_INDEX));
        info.setPostalCode(getAndCleanValue(row, POSTAL_CODE_INDEX));
        info.setCity(getAndCleanValue(row, CITY_INDEX));
        info.setState(getAndCleanValue(row, STATE_INDEX));
        String orgType = getAndCleanValue(row, ORGTYPE_INDEX);
        if (!StringUtils.isEmpty(orgType)) {
            info.setOrgType(OrgType.valueOf(orgType));
        }
        return info;
    }

    private String cleanWhiteSpaces(String value) {
        if(value == null) {
            return null;
        }
        return value
                .replaceAll(REPLACE_WITH_EMPTY_STRING_REGEX, EMPTY_STRING)
                .replaceAll(REPLACE_WITH_SPACE_REGEX, SPACE).trim();
    }

    private String getAndCleanValue(R row, int index) {
        return cleanWhiteSpaces(getValue(row, index));
    }
}

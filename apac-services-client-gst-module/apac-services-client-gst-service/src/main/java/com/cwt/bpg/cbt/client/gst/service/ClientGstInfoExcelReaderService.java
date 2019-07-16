package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.OrgType;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static com.cwt.bpg.cbt.client.gst.service.Constants.*;

@Service
public class ClientGstInfoExcelReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoExcelReaderService.class);

    private static final int ROW_CACHE_SIZE = 100;
    private static final int BUFFER_SIZE = 4096;
    private static final int GST_DATA_SHEET_INDEX = 0;
    private static final int ROWS_TO_SKIP_GST_DATA = 2;

    private static final String NON_ALPHANUMERIC_REGEX = "[^0-9a-zA-Z]";
    private static final String LINE_BREAK_REGEX = "\\r\\n|\\r|\\n";
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    public List<ClientGstInfo> readExcelFile(InputStream inputStream) {
        try(InputStream is = new BufferedInputStream(inputStream)) {
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(ROW_CACHE_SIZE)
                    .bufferSize(BUFFER_SIZE)
                    .open(is);
            return extractClientGstInfo(workbook);
        } catch (Exception e) {
            LOGGER.error("An error occurred while reading excel file", e);
            return null;
        }
    }

    private static List<ClientGstInfo> extractClientGstInfo(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(GST_DATA_SHEET_INDEX);
        List<ClientGstInfo> clientGstInfo = new LinkedList<>();
        int rowIteration = 0;
        for (Row row : sheet) {
            if (++rowIteration <= ROWS_TO_SKIP_GST_DATA) {
                continue;
            }
            ClientGstInfo info = extractFromRow(row);
            if (info.allValuesNull()) {
                break; //stop reading excel if no values can be extracted
            }
            else if (!StringUtils.isEmpty(info.getGstin())) {
                clientGstInfo.add(info);
            }
        }
        return clientGstInfo;
    }

    private static ClientGstInfo extractFromRow(Row row) {
        ClientGstInfo info = new ClientGstInfo();
        info.setClient(getValue(row, CLIENT_INDEX));
        info.setGstin(getValue(row, GSTIN_INDEX));
        if (info.getGstin() != null) {
            info.setGstin(info.getGstin().replaceAll(NON_ALPHANUMERIC_REGEX, EMPTY_STRING));
            info.setGstin(info.getGstin().toUpperCase());
        }
        info.setClientEntityName(getValue(row, ENTITY_NAME_INDEX));
        info.setBusinessPhoneNumber(getValue(row, BUSINESS_PHONE_INDEX));
        info.setBusinessEmailAddress(getValue(row, EMAIL_ADDRESS_INDEX));
        info.setEntityAddressLine1(getValue(row, ADDRESS_LINE_1_INDEX));
        info.setEntityAddressLine2(getValue(row, ADDRESS_LINE2_INDEX));
        info.setPostalCode(getValue(row, POSTAL_CODE_INDEX));
        info.setCity(getValue(row, CITY_INDEX));
        info.setState(getValue(row, STATE_INDEX));
        if (getValue(row, ORGTYPE_INDEX) != null)
        {
            info.setOrgType(OrgType.valueOf(getValue(row, ORGTYPE_INDEX)));
        }
        return info;
    }

    private static String getValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue().trim().replaceAll(LINE_BREAK_REGEX, SPACE);
        }
        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.format("%.0f", cell.getNumericCellValue());
        }
        return null;
    }
}

package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.GstAirline;
import com.cwt.bpg.cbt.client.gst.model.GstLookup;
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

@Service
public class ClientGstInfoExcelReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoExcelReaderService.class);

    private static final int ROW_CACHE_SIZE = 100;
    private static final int BUFFER_SIZE = 4096;
    private static final int GST_DATA_SHEET_INDEX = 0;
    private static final int GST_AIRLINE_CODES_INDEX = 1;
    private static final int ROWS_TO_SKIP_GST_DATA = 2;
    private static final int ROWS_TO_SKIP_AIRLINE_CODES = 1;
    private static final int CLIENT_INDEX = 0;
    private static final int GSTIN_INDEX = 1;
    private static final int AIRLINE_CODE_INDEX = 0;
    private static final int ENTITY_NAME_INDEX = 2;
    private static final int BUSINESS_PHONE_INDEX = 3;
    private static final int EMAIL_ADDRESS_INDEX = 4;
    private static final int ADDRESS_LINE_1_INDEX = 5;
    private static final int ADDRESS_LINE2_INDEX = 6;
    private static final int POSTAL_CODE_INDEX = 7;
    private static final int CITY_INDEX = 8;
    private static final int STATE_INDEX = 9;
    private static final String NON_ALPHANUMERIC_REGEX = "[^0-9a-zA-Z]";
    private static final String LINE_BREAK_REGEX = "\\r\\n|\\r|\\n";
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String TRAIN = "train";

    public GstLookup readExcelFile(InputStream inputStream, boolean includeGstAirlines) {
        GstLookup gstLookup = new GstLookup();
        try(InputStream is = new BufferedInputStream(inputStream)) {
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(ROW_CACHE_SIZE)
                    .bufferSize(BUFFER_SIZE)
                    .open(is);
            List<ClientGstInfo> clientGstInfo = extractClientGstInfo(workbook);
            gstLookup.setClientGstInfo(clientGstInfo);
            if(includeGstAirlines) {
                List<GstAirline> gstAirlines = extractGstAirlines(workbook);
                gstLookup.setGstAirlines(gstAirlines);
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while reading excel file", e);
        }
        return gstLookup;
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

    private static List<GstAirline> extractGstAirlines(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(GST_AIRLINE_CODES_INDEX);
        List<GstAirline> gstAirlines = new LinkedList<>();
        int rowIteration = 0;
        for (Row row : sheet) {
            if (++rowIteration <= ROWS_TO_SKIP_AIRLINE_CODES) {
                continue; //stop reading excel if no values can be extracted
            }
            GstAirline gstAirline = new GstAirline();
            gstAirline.setCode(getValue(row, AIRLINE_CODE_INDEX));
            if (StringUtils.isEmpty(gstAirline.getCode())) {
                break;
            }
            if(!TRAIN.equalsIgnoreCase(gstAirline.getCode())) {
                gstAirlines.add(gstAirline);
            }
        }
        return gstAirlines;
    }

    private static ClientGstInfo extractFromRow(Row row) {
        ClientGstInfo info = new ClientGstInfo();
        info.setClient(getValue(row, CLIENT_INDEX));
        info.setGstin(getValue(row, GSTIN_INDEX));
        if (info.getGstin() != null) {
            info.setGstin(info.getGstin().replaceAll(NON_ALPHANUMERIC_REGEX, EMPTY_STRING));
        }
        info.setClientEntityName(getValue(row, ENTITY_NAME_INDEX));
        info.setBusinessPhoneNumber(getValue(row, BUSINESS_PHONE_INDEX));
        info.setBusinessEmailAddress(getValue(row, EMAIL_ADDRESS_INDEX));
        info.setEntityAddressLine1(getValue(row, ADDRESS_LINE_1_INDEX));
        info.setEntityAddressLine2(getValue(row, ADDRESS_LINE2_INDEX));
        info.setPostalCode(getValue(row, POSTAL_CODE_INDEX));
        info.setCity(getValue(row, CITY_INDEX));
        info.setState(getValue(row, STATE_INDEX));
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
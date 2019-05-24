package com.cwt.bpg.cbt.client.gst.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoResponse;
import com.cwt.bpg.cbt.client.gst.model.GstAirline;
import com.cwt.bpg.cbt.client.gst.model.GstLookup;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.client.gst.repository.GstAirlineRepository;




@Service
public class ClientGstInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoService.class);

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

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;
    
    @Autowired
    private GstAirlineRepository gstAirlineRepository;
    
    @Cacheable(cacheNames = "gst-airlines", key="#root.methodName")
    public List<ClientGstInfo> getAll() {
    	return clientGstInfoRepository.getAll();
    }

    public ClientGstInfoResponse getClientGstInfo(String gstin, List<String> airlineCodes) {
        ClientGstInfo clientGstInfo = getClientGstInfo(gstin);
        if(clientGstInfo == null) {
            return null;
        }
        ClientGstInfoResponse response = new ClientGstInfoResponse();
        List<String> validAirlineCodes = new LinkedList<>();
        Set<String> gstAirlines = getGstAirlines();
        for(String airlineCode: airlineCodes) {
            if(gstAirlines.contains(airlineCode)){
                validAirlineCodes.add(airlineCode);
            }
        }
        response.setClientGstInfo(clientGstInfo);
        response.setAirlineCodes(validAirlineCodes);
        return response;
    }

    @Cacheable(cacheNames = "gst-airlines")
    public Set<String> getGstAirlines() {
        Set<String> gstAirlineSet = new HashSet<>();
        List<GstAirline> gstAirlineList = gstAirlineRepository.getAll();
        for(GstAirline gstAirline: gstAirlineList) {
            gstAirlineSet.add(gstAirline.getCode());
        }
        return gstAirlineSet;
    }

    @Cacheable(cacheNames = "client-gst-info", key = "#gstin")
    public ClientGstInfo getClientGstInfo(String gstin) {
        return clientGstInfoRepository.get(gstin);
    }

    public ClientGstInfo save(ClientGstInfo clientGstInfo) {
        return clientGstInfoRepository.put(clientGstInfo);
    }
    
    @CacheEvict(cacheNames = "client-gst-info", key = "#gstin")
    public String remove(String gstin) {
    	 return clientGstInfoRepository.remove(gstin);
    }

    @Async
    @CacheEvict(cacheNames = {"client-gst-info", "gst-airlines"}, allEntries = true)
    public void saveFromExcelFile(InputStream inputStream, boolean includeGstAirlines) {
        GstLookup gstLookup = extractFromExcelFile(inputStream, includeGstAirlines);
        if (!gstLookup.getClientGstInfo().isEmpty()) {
            clientGstInfoRepository.backupCollection();
            clientGstInfoRepository.putAll(gstLookup.getClientGstInfo());
        }
        if (includeGstAirlines && !gstLookup.getGstAirlines().isEmpty()) {
            gstAirlineRepository.dropCollection();
            gstAirlineRepository.putAll(gstLookup.getGstAirlines());
        }
    }

    private static GstLookup extractFromExcelFile(InputStream fileInputStream, boolean includeGstAirlines) {
        long start = System.currentTimeMillis();
        GstLookup gstLookup = new GstLookup();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            LOGGER.info("Loaded excel in {} ms", System.currentTimeMillis() - start);
            List<ClientGstInfo> clientGstInfo = extractClientGstInfo(workbook);
            gstLookup.setClientGstInfo(clientGstInfo);
            if(includeGstAirlines) {
                List<GstAirline> gstAirlines = extractGstAirlines(workbook);
                gstLookup.setGstAirlines(gstAirlines);
            }
        }
        catch (IOException e) {
            LOGGER.error("An error occurred while reading excel file", e);
        }
        finally {
            closeStream(fileInputStream);
        }
        return gstLookup;
    }

    private static List<ClientGstInfo> extractClientGstInfo(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(GST_DATA_SHEET_INDEX);
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

    private static List<GstAirline> extractGstAirlines(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(GST_AIRLINE_CODES_INDEX);
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

    private static void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        catch (Exception e) {
            LOGGER.error("An error occurred while closing input stream", e);
        }
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
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue().trim().replaceAll(LINE_BREAK_REGEX, SPACE);
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.format("%.0f", cell.getNumericCellValue());
        }
        return null;
      }
}

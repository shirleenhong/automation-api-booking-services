package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClientGstInfoService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoService.class);

    private static final int GST_DATA_SHEET_INDEX = 0;
    private static final int ROWS_TO_SKIP = 2;
    private static final int CLIENT_INDEX = 0;
    private static final int GSTIN_INDEX = 1;
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

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;

    @Cacheable(cacheNames = "client-gst-info")
    public ClientGstInfo getClientGstInfo(String gstin)
    {
        return clientGstInfoRepository.get(gstin);
    }

    public ClientGstInfo save(ClientGstInfo clientGstInfo)
    {
        return clientGstInfoRepository.put(clientGstInfo);
    }

    @Async
    @CacheEvict(cacheNames = "client-gst-info", allEntries = true)
    public void saveFromExcelFile(InputStream inputStream)
    {
        List<ClientGstInfo> clientGstInfo = extractFromExcelFile(inputStream);
        if (!clientGstInfo.isEmpty())
        {
            long start = System.currentTimeMillis();
            clientGstInfoRepository.backupCollection();
            clientGstInfoRepository.putAll(clientGstInfo);
            LOGGER.info("Saving to db took {} ms", System.currentTimeMillis() - start);
        }
    }

    private static List<ClientGstInfo> extractFromExcelFile(InputStream fileInputStream)
    {
        List<ClientGstInfo> clientGstInfo = new LinkedList<>();
        long start = System.currentTimeMillis();
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            LOGGER.info("Loaded excel in {} ms", System.currentTimeMillis() - start);
            XSSFSheet sheet = workbook.getSheetAt(GST_DATA_SHEET_INDEX);
            int rowIteration = 0;
            for (Row row : sheet)
            {
                if (++rowIteration <= ROWS_TO_SKIP)
                {
                    continue;
                }
                ClientGstInfo info = extractFromRow(row);
                if (info.allValuesNull())
                {
                    break; //stop reading excel if no values can be extracted
                }
                else if (!StringUtils.isEmpty(info.getGstin()))
                {
                    clientGstInfo.add(info);
                }
            }
        }
        catch (IOException e)
        {
            LOGGER.error("An error occurred while reading excel file", e);
        }
        finally
        {
            closeStream(fileInputStream);
        }
        LOGGER.info("Reading from excel took {} ms", System.currentTimeMillis() - start);
        return clientGstInfo;
    }

    private static void closeStream(InputStream inputStream)
    {
        try
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        catch (Exception e)
        {
            LOGGER.error("An error occurred while closing input stream", e);
        }
    }

    private static ClientGstInfo extractFromRow(Row row)
    {
        ClientGstInfo info = new ClientGstInfo();
        info.setClient(getValue(row, CLIENT_INDEX));
        info.setGstin(getValue(row, GSTIN_INDEX));
        if (info.getGstin() != null)
        {
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

    private static String getValue(Row row, int index)
    {
        Cell cell = row.getCell(index);
        if (cell == null)
        {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
        {
            return cell.getStringCellValue().trim()
                    .replaceAll(LINE_BREAK_REGEX, SPACE);
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
        {
            return String.format("%.0f", cell.getNumericCellValue());
        }
        return null;
    }
}

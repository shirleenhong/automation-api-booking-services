package com.cwt.bpg.cbt.client.gst.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.monitorjbl.xlsx.StreamingReader;

@Service
public class ClientGstInfoExcelReaderService extends ClientGstInfoReaderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoExcelReaderService.class);

    private static final int ROW_CACHE_SIZE = 100;
    private static final int BUFFER_SIZE = 4096;
    private static final int GST_DATA_SHEET_INDEX = 0;


    public List<ClientGstInfo> readFile(InputStream inputStream) {
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

    private List<ClientGstInfo> extractClientGstInfo(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(GST_DATA_SHEET_INDEX);
        List<ClientGstInfo> clientGstInfo = new LinkedList<>();
        int rowIteration = 0;
        for (Row row : sheet) {
        	rowIteration++;
        	populateClientGstInfo(rowIteration, row, clientGstInfo);
        }
        return clientGstInfo;
    }

    public String getValue(Object obj, int index) {
    	Row row = (Row) obj;
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

package com.cwt.bpg.cbt.client.gst.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;

@Service
public class ClientGstInfoExcelReaderService extends ClientGstInfoReaderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoExcelReaderService.class);

    private static final int GST_DATA_SHEET_INDEX = 0;

    public List<ClientGstInfo> readFile(InputStream inputStream) {
        try{
        	Workbook workbook = createWorkBook(inputStream);
            return extractClientGstInfo(workbook);
        } catch (Exception e) {
            LOGGER.error("An error occurred while reading excel file", e);
            return null;
        }
    }

	private Workbook createWorkBook(InputStream inputStream) throws IOException {
		try {
			return new XSSFWorkbook(inputStream);
		}catch (Exception e) {
			return new HSSFWorkbook(inputStream);
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

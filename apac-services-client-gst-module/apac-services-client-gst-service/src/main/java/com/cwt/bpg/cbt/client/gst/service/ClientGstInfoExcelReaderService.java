package com.cwt.bpg.cbt.client.gst.service;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ClientGstInfoExcelReaderService extends ClientGstInfoReaderService<Workbook, Sheet, Row> {

    private static final int ROW_CACHE_SIZE = 100;
    private static final int BUFFER_SIZE = 4096;
    private static final int GST_DATA_SHEET_INDEX = 0;

    public Workbook createWorkbook(InputStream inputStream) {
        return StreamingReader.builder()
                .rowCacheSize(ROW_CACHE_SIZE)
                .bufferSize(BUFFER_SIZE)
                .open(inputStream);
    }

    @Override
    protected Sheet createSheet(Workbook workbook) {
        return workbook.getSheetAt(GST_DATA_SHEET_INDEX);
    }

    public String getValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.format("%.0f", cell.getNumericCellValue());
        }
        return null;
    }
}

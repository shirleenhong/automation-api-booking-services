package com.cwt.bpg.cbt.client.gst.service;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class ClientGstInfoCsvReaderService extends ClientGstInfoReaderService<CSVReader, CSVReader, String[]> {

    @Override
    protected CSVReader createWorkbook(InputStream inputStream) {
        return new CSVReader(new InputStreamReader(inputStream));
    }

    @Override
    protected CSVReader createSheet(CSVReader workbook) {
        return workbook;
    }

    @Override
    protected String getValue(String[] row, int index) {
        String cell = row[index];
        if (StringUtils.isEmpty(cell)) {
            return null;
        }
        return cell;
    }
}

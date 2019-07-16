package com.cwt.bpg.cbt.client.gst.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientGstInfoReaderConfig {

	public static final String CSV = "csv";
    public static final String EXCEL_WORKBOOK = "xlsx";
    public static final String MACRO_ENABLED_WORKBOOK = "xlsm";
    
    @Autowired
    private ClientGstInfoExcelReaderService clientGstInfoExcelReaderService;

    @Autowired
    private ClientGstInfoCsvReaderService clientGstInfoCsvReaderService;
    
    @Bean("clientGstInfoReaderServiceMap")
    public Map<String, ClientGstInfoReaderService> clientGstInfoReaderMap(){
    	Map<String, ClientGstInfoReaderService> map = new HashMap<>();
    	map.put(CSV, clientGstInfoCsvReaderService);
    	map.put(EXCEL_WORKBOOK, clientGstInfoExcelReaderService);
    	map.put(MACRO_ENABLED_WORKBOOK, clientGstInfoExcelReaderService);
		return map;
    }
    
}

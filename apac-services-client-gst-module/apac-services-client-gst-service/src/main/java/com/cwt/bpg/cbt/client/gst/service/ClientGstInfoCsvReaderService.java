package com.cwt.bpg.cbt.client.gst.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.opencsv.CSVReader;

@Service
public class ClientGstInfoCsvReaderService extends ClientGstInfoReaderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoCsvReaderService.class);

	public List<ClientGstInfo> readFile(InputStream inputStream) {
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));) {
			return extractClientGstInfo(csvReader);
		} catch (Exception e) {
			LOGGER.error("An error occurred while reading csv file", e);
			return null;
		}
	}

	private List<ClientGstInfo> extractClientGstInfo(CSVReader csvReader) throws IOException {

		List<ClientGstInfo> clientGstInfo = new ArrayList<>();
		String[] row = null;
		int rowIteration = 0;
		while ((row = csvReader.readNext()) != null) {
			rowIteration++;
			populateClientGstInfo(rowIteration, row, clientGstInfo);
		}
		return clientGstInfo;
	}

	@Override
	protected String getValue(Object obj, int index) {
		String[] row = (String[]) obj;
		String cell = row[index];
		if (cell == null) {
			return null;
		}
		return cell.toString().trim().replaceAll(LINE_BREAK_REGEX, SPACE);
	}
}

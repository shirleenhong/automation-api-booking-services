package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientGstInfoFileWriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoFileWriterService.class);

    private static final String FILENAME = "GSTLookup.csv";

    public WriteClientGstInfoFileResponse writeToFile(List<ClientGstInfo> clientGstInfo) throws ApiServiceException {
        List<String> lines = new ArrayList<>(clientGstInfo.size());
        populateFirstRowLabels(lines);
        populateSecondRowLabels(lines);
        for(ClientGstInfo info: clientGstInfo){
            populateClientGstInfo(info, lines);
        }
        byte[] data = transformToBytes(lines);
        return new WriteClientGstInfoFileResponse(FILENAME, data, lines);
    }

    private byte[] transformToBytes(List<String> lines) throws ApiServiceException {
        final String newLine = System.lineSeparator();
        try(final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
            for(String line: lines) {
                writer.write(line);
                writer.write(newLine);
            }
            writer.flush();
            return  baos.toByteArray();
        } catch (Exception e) {
            LOGGER.error("An error occurred while writing file", e);
            throw new ApiServiceException(e.getMessage());
        }
    }

    private void populateClientGstInfo(ClientGstInfo info, List<String> lines) {
        String row = createRow(Arrays.asList(
                info.getClient(),
                info.getGstin(),
                info.getClientEntityName(),
                info.getBusinessPhoneNumber(),
                info.getBusinessEmailAddress(),
                info.getEntityAddressLine1(),
                info.getEntityAddressLine2(),
                info.getPostalCode(),
                info.getCity(),
                info.getState(),
                info.getOrgType() != null ? info.getOrgType().toString() : StringUtils.EMPTY
        ));
        lines.add(row);
    }

    private void populateFirstRowLabels(List<String> lines) {
        String row = createRow(Arrays.asList(
                "Free Format",
                "GSTIN Numbers",
                "Client Entity Name",
                "Business Phone Number",
                "Business Email Address",
                "Entity Address Line 1",
                "Entity Address Line 1",
                "Postal Code",
                "City",
                "State",
                StringUtils.EMPTY
        ));
        lines.add(row);
    }

    private void populateSecondRowLabels(List<String> lines) {
        String row = createRow(Arrays.asList(
                "Client",
                "FORMAT 15 Alpha numeric",
                "FORMAT 35 Alpha Numeric",
                "FORMAT 15 Numeric (No Space or Hyphens etc)",
                "FORMAT 35 Alpha numeric",
                "FORMAT 35 Alpha numeric",
                "FORMAT 25 Alpha Numeric",
                "FORMAT 17 Alpha Numeric",
                "FORMAT 25 Alpha",
                "FORMAT 25 Alpha",
                "OrgType"
        ));
        lines.add(row);
    }

    private String createRow(List<String> entries) {
        List<String> entriesCopy = entries.stream()
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.toList());
        return String.join(",", entriesCopy);
    }

    private String escapeSpecialCharacters(String data) {
        if(data == null) {
            return StringUtils.EMPTY;
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}

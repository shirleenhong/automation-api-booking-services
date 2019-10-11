package com.cwt.bpg.cbt.client.gst.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.client.gst.exception.ClientGstInfoBackupException;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoBackup;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exceptions.FileUploadException;
import com.cwt.bpg.cbt.service.CommonBackupService;

@Service
public class ClientGstInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoService.class);

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;

    @Autowired
    private CommonBackupService<ClientGstInfo, ClientGstInfoBackup> clientGstInfoBackupService;

    @Autowired
    private ClientGstInfoFileWriterService clientGstInfoFileWriterService;

    @Autowired
    @Qualifier("clientGstInfoReaderServiceMap")
    private Map<String, ClientGstInfoReaderService> clientGstInfoReaderServiceMap;

    public List<ClientGstInfo> getAllClientGstInfo() {
        return clientGstInfoRepository.getAll();
    }

    @Cacheable(cacheNames = "client-gst-info", key = "#gstin")
    public ClientGstInfo getClientGstInfo(String gstin) {
        return clientGstInfoRepository.get(gstin);
    }

    public ClientGstInfo save(ClientGstInfo clientGstInfo) {
        String formattedGstin = clientGstInfo.getGstin().toUpperCase().trim();
        clientGstInfo.setGstin(formattedGstin);
        return clientGstInfoRepository.put(clientGstInfo);
    }

    public String remove(String gstin) {
        return clientGstInfoRepository.remove(gstin);
    }

    public WriteClientGstInfoFileResponse writeFile() throws ApiServiceException {
        List<ClientGstInfo> clientGstInfo = clientGstInfoRepository.getAll();
        if (CollectionUtils.isEmpty(clientGstInfo)) {
            return null;
        }
        return clientGstInfoFileWriterService.writeToFile(clientGstInfo);
    }

    @SuppressWarnings("unchecked")
    public void saveFromFile(InputStream inputStream, String extension, boolean validate) throws Exception {
        ClientGstInfoReaderService reader = clientGstInfoReaderServiceMap.get(extension);
        if (reader == null) {
            throw new IllegalArgumentException("File must be in excel or csv format");
        }
        String batchId = UUID.randomUUID().toString();
        final List<ClientGstInfo> clientGstInfoBackupList = new ArrayList<>();
        try {
            List<ClientGstInfo> clientGstInfo = reader.readFile(inputStream, validate);
            clientGstInfoBackupService.archive(clientGstInfoBackupList, batchId, true);
            clientGstInfoRepository.putAll(clientGstInfo);
        }
        catch (FileUploadException e) {
            clientGstInfoBackupService.rollback(clientGstInfoBackupList, batchId);
            LOGGER.error("Error in creating backup of client gst info from file", e);
            throw e;
        }
        catch (Exception e) {
            clientGstInfoBackupService.rollback(clientGstInfoBackupList, batchId);
            LOGGER.error("Error in creating backup of client gst info from file", e);
            throw new ClientGstInfoBackupException("Error in creating backup of client gst info from file",
                    e);
        }
    }
}

package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoBackup;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.mongodb.CommandResult;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ClientGstInfoService {

    private static final int BATCH_SIZE = 1000;
    private static final String COUNT_STAT = "count";

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;

    @Autowired
    private ClientGstInfoBackupRepository clientGstInfoBackupRepository;

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
    public void saveFromFile(InputStream inputStream, String extension, boolean validate)
            throws Exception {
        ClientGstInfoReaderService reader = clientGstInfoReaderServiceMap.get(extension);
        if(reader == null) {
            throw new IllegalArgumentException("File must be in excel or csv format");
        }
        List<ClientGstInfo> clientGstInfo = reader.readFile(inputStream, validate);
        if (!CollectionUtils.isEmpty(clientGstInfo)) {
            backupClientGstInfo();
            clientGstInfoRepository.dropCollection();
            clientGstInfoRepository.putAll(clientGstInfo);
        }
    }

    private void backupClientGstInfo() {
        CommandResult stats = clientGstInfoRepository.getStats();
        Integer size = (Integer) stats.get(COUNT_STAT);
        if (size == null || size == 0) {
            return; //size will be null if collection doesn't exist
        }
        clientGstInfoBackupRepository.dropCollection();
        Instant currentDateTime = Instant.now();
        int batches = (int) Math.ceil(size / (double) BATCH_SIZE);
        int currentBatch = 0;
        int skip = 0;
        int limit = BATCH_SIZE;
        while (++currentBatch <= batches) {
            FindOptions options = new FindOptions().skip(skip).limit(limit);
            List<ClientGstInfo> toBackup = clientGstInfoRepository.getAll(options);
            List<ClientGstInfoBackup> backups = createBackupList(toBackup, currentDateTime);
            clientGstInfoBackupRepository.putAll(backups);
            skip = skip + limit;
            limit = limit + BATCH_SIZE;
        }
    }

    private List<ClientGstInfoBackup> createBackupList(List<ClientGstInfo> toBackup, Instant dateTime) {
        List<ClientGstInfoBackup> backups = new LinkedList<>();
        for (ClientGstInfo clientGstInfo : toBackup) {
            ClientGstInfoBackup backup = new ClientGstInfoBackup();
            backup.setDateCreated(dateTime);
            backup.setClientGstInfo(clientGstInfo);
            backups.add(backup);
        }
        return backups;
    }
}

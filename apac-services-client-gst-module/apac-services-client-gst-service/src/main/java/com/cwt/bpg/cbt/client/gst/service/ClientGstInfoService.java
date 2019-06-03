package com.cwt.bpg.cbt.client.gst.service;

import java.io.*;
import java.time.Instant;
import java.util.*;

import com.cwt.bpg.cbt.client.gst.model.*;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.mongodb.CommandResult;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.client.gst.repository.GstAirlineRepository;

@Service
public class ClientGstInfoService {

    private static final int BATCH_SIZE = 1000;
    private static final String COUNT_STAT = "count";

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;
    
    @Autowired
    private GstAirlineRepository gstAirlineRepository;

    @Autowired
    private ClientGstInfoBackupRepository clientGstInfoBackupRepository;

    @Autowired
    private ClientGstInfoExcelReaderService clientGstInfoExcelReaderService;
    
    public List<ClientGstInfo> getAllClientGstInfo() {
    	return clientGstInfoRepository.getAll();
    }

    public ClientGstInfoResponse getClientGstInfo(String gstin, List<String> airlineCodes) {
        ClientGstInfo clientGstInfo = getClientGstInfo(gstin);
        if(clientGstInfo == null) {
            return null;
        }
        ClientGstInfoResponse response = new ClientGstInfoResponse();
        Set<String> validAirlineCodes = new HashSet<>();
        Set<String> gstAirlines = getGstAirlines();
        for(String airlineCode: airlineCodes) {
            if(gstAirlines.contains(airlineCode)){
                validAirlineCodes.add(airlineCode);
            }
        }
        response.setClientGstInfo(clientGstInfo);
        response.setAirlineCodes(validAirlineCodes);
        return response;
    }

    @Cacheable(cacheNames = "gst-airlines")
    public Set<String> getGstAirlines() {
        Set<String> gstAirlineSet = new HashSet<>();
        List<GstAirline> gstAirlineList = gstAirlineRepository.getAll();
        for(GstAirline gstAirline: gstAirlineList) {
            gstAirlineSet.add(gstAirline.getCode());
        }
        return gstAirlineSet;
    }

    @Cacheable(cacheNames = "client-gst-info", key = "#gstin")
    public ClientGstInfo getClientGstInfo(String gstin) {
        return clientGstInfoRepository.get(gstin);
    }

    @CacheEvict(cacheNames = "client-gst-info", allEntries = true)
    public ClientGstInfo save(ClientGstInfo clientGstInfo) {
        return clientGstInfoRepository.put(clientGstInfo);
    }
    
    @CacheEvict(cacheNames = "client-gst-info", allEntries = true)
    public String remove(String gstin) {
    	 return clientGstInfoRepository.remove(gstin);
    }

    @Async
    @CacheEvict(cacheNames = {"client-gst-info", "gst-airlines"}, allEntries = true)
    public void saveFromExcelFile(InputStream inputStream, boolean includeGstAirlines) {
        GstLookup gstLookup = clientGstInfoExcelReaderService
                .readExcelFile(inputStream, includeGstAirlines);
        if (!gstLookup.getClientGstInfo().isEmpty()) {
            backupClientGstInfo();
            clientGstInfoRepository.dropCollection();
            clientGstInfoRepository.putAll(gstLookup.getClientGstInfo());
        }
        if (includeGstAirlines && !gstLookup.getGstAirlines().isEmpty()) {
            gstAirlineRepository.dropCollection();
            gstAirlineRepository.putAll(gstLookup.getGstAirlines());
        }
    }

    private void backupClientGstInfo() {
        CommandResult stats = clientGstInfoRepository.getStats();
        Integer size = (Integer)stats.get(COUNT_STAT);
        if(size == null || size == 0) {
            return; //size will be null if collection doesn't exist
        }
        clientGstInfoBackupRepository.dropCollection();
        Instant currentDateTime = Instant.now();
        int batches = (int)Math.ceil(size/(double)BATCH_SIZE);
        int currentBatch = 0;
        int skip = 0;
        int limit = BATCH_SIZE;
        while(++currentBatch <= batches) {
            FindOptions options = new FindOptions()
                    .skip(skip)
                    .limit(limit);
            List<ClientGstInfo> toBackup = clientGstInfoRepository.getAll(options);
            List<ClientGstInfoBackup> backups = createBackupList(toBackup, currentDateTime);
            clientGstInfoBackupRepository.putAll(backups);
            skip = skip + limit;
            limit = limit + BATCH_SIZE;
        }
    }

    private List<ClientGstInfoBackup> createBackupList(List<ClientGstInfo> toBackup, Instant dateTime) {
        List<ClientGstInfoBackup> backups = new LinkedList<>();
        for(ClientGstInfo clientGstInfo: toBackup) {
            ClientGstInfoBackup backup = new ClientGstInfoBackup();
            backup.setDateCreated(dateTime);
            backup.setClientGstInfo(clientGstInfo);
            backups.add(backup);
        }
        return backups;
    }
}

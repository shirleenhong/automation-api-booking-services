package com.cwt.bpg.cbt.client.gst.service;

import java.io.*;
import java.util.*;

import com.cwt.bpg.cbt.client.gst.model.GstLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoResponse;
import com.cwt.bpg.cbt.client.gst.model.GstAirline;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.client.gst.repository.GstAirlineRepository;

@Service
public class ClientGstInfoService {

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;
    
    @Autowired
    private GstAirlineRepository gstAirlineRepository;

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
            clientGstInfoRepository.backupCollection();
            clientGstInfoRepository.putAll(gstLookup.getClientGstInfo());
        }
        if (includeGstAirlines && !gstLookup.getGstAirlines().isEmpty()) {
            gstAirlineRepository.dropCollection();
            gstAirlineRepository.putAll(gstLookup.getGstAirlines());
        }
    }
}

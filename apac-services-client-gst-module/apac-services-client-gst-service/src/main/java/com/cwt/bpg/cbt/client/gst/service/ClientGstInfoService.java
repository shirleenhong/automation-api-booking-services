package com.cwt.bpg.cbt.client.gst.service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
public class ClientGstInfoService {

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;

    @Cacheable(cacheNames = "client-gst-info")
    public ClientGstInfo getClientGstInfo(String gstin) {
        return clientGstInfoRepository.get(gstin);
    }

    @CachePut(cacheNames = "client-gst-info", key = "#clientGstInfo.gstin")
    public ClientGstInfo save(ClientGstInfo clientGstInfo) {
        return clientGstInfoRepository.put(clientGstInfo);
    }
}

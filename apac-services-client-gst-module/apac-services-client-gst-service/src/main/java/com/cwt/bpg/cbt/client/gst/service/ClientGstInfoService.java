package com.cwt.bpg.cbt.client.gst.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.client.gst.exception.ClientGstInfoBackupException;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.WriteClientGstInfoFileResponse;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exceptions.FileUploadException;
import com.cwt.bpg.cbt.upload.model.CollectionGroup;

@Service
public class ClientGstInfoService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientGstInfoService.class);

    @Autowired
    private ClientGstInfoRepository clientGstInfoRepository;

    @Autowired
    private ClientGstInfoGroupService groupService;

    @Autowired
    private ClientGstInfoFileWriterService clientGstInfoFileWriterService;

    @SuppressWarnings("rawtypes")
    @Autowired
    @Qualifier("clientGstInfoReaderServiceMap")
    private Map<String, ClientGstInfoReaderService> clientGstInfoReaderServiceMap;

    public List<ClientGstInfo> getAllClientGstInfo()
    {
        CollectionGroup activeGroup = groupService.getActiveCollectionGroup();
        return clientGstInfoRepository.getAll(activeGroup.getGroupId());
    }

    @Cacheable(cacheNames = "client-gst-info", key = "#gstin")
    public ClientGstInfo getClientGstInfo(String gstin)
    {
        CollectionGroup activeGroup = groupService.getActiveCollectionGroup();
        return clientGstInfoRepository.getByGstin(activeGroup.getGroupId(), gstin);
    }

    public ClientGstInfo save(ClientGstInfo clientGstInfo)
    {
        CollectionGroup activeGroup = groupService.getActiveCollectionGroup();
        String formattedGstin = clientGstInfo.getGstin().toUpperCase().trim();
        clientGstInfo.setGstin(formattedGstin);
        clientGstInfo.setGroupId(activeGroup.getGroupId());
        return clientGstInfoRepository.put(clientGstInfo);
    }

    public String remove(String gstin)
    {
        CollectionGroup activeGroup = groupService.getActiveCollectionGroup();
        ClientGstInfo toDelete = clientGstInfoRepository.getByGstin(activeGroup.getGroupId(), gstin);

        if (toDelete != null)
        {
            return clientGstInfoRepository.remove(toDelete.getId());
        }

        return null;
    }

    public WriteClientGstInfoFileResponse writeFile() throws ApiServiceException
    {
        List<ClientGstInfo> clientGstInfo = clientGstInfoRepository.getAll();
        if (CollectionUtils.isEmpty(clientGstInfo))
        {
            return null;
        }
        return clientGstInfoFileWriterService.writeToFile(clientGstInfo);
    }

    @SuppressWarnings("unchecked")
    public void saveFromFile(InputStream inputStream, String extension, boolean validate) throws Exception
    {
        @SuppressWarnings("rawtypes")
        ClientGstInfoReaderService reader = clientGstInfoReaderServiceMap.get(extension);
        if (reader == null)
        {
            throw new IllegalArgumentException("File must be in excel or csv format");
        }

        try
        {
            List<ClientGstInfo> clientGstInfo = reader.readFile(inputStream, validate);
            groupService.saveCollectionGroup(clientGstInfo);
        }
        catch (FileUploadException e)
        {
            LOGGER.error("Error in creating backup of client gst info from file", e);
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error("Error in creating backup of client gst info from file", e);
            throw new ClientGstInfoBackupException("Error in creating backup of client gst info from file",
                    e);
        }
    }
}

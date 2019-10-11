package com.cwt.bpg.cbt.client.gst.service;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoBackup;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoBackupRepository;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.service.CommonBackupService;

@Service
public class ClientGstInfoBackupService extends CommonBackupService<ClientGstInfo, ClientGstInfoBackup> {

    @Autowired
    public ClientGstInfoBackupService(ClientGstInfoRepository repository,
            ClientGstInfoBackupRepository backupRepository) {
        super(repository, backupRepository);
    }

    @Override
    public List<ClientGstInfoBackup> createBackupList(List<ClientGstInfo> toBackup, Instant dateTime,
            String batchId) {
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

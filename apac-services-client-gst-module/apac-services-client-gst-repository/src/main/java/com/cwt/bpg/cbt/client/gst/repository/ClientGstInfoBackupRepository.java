package com.cwt.bpg.cbt.client.gst.repository;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoBackup;
import com.cwt.bpg.cbt.repository.CommonBackupRepository;

@Repository
public class ClientGstInfoBackupRepository extends CommonBackupRepository<ClientGstInfoBackup, String> {

    private static final String KEY_COLUMN = "id";

    public ClientGstInfoBackupRepository() {
        super(ClientGstInfoBackup.class, KEY_COLUMN);
    }
}

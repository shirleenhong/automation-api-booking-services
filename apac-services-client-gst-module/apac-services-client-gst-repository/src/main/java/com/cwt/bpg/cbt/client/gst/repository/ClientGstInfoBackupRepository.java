package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoBackup;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientGstInfoBackupRepository extends CommonRepository<ClientGstInfoBackup, String> {

    private static final String KEY_COLUMN = "id";

    public ClientGstInfoBackupRepository() {
        super(ClientGstInfoBackup.class, KEY_COLUMN);
    }
}

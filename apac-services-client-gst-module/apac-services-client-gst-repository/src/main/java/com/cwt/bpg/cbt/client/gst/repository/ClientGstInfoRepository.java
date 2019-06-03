package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientGstInfoRepository extends CommonRepository<ClientGstInfo, String> {

    private static final String KEY_COLUMN = "gstin";

    public ClientGstInfoRepository() {
        super(ClientGstInfo.class, KEY_COLUMN);
    }
}

package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGst;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientGstRepository extends CommonRepository<ClientGst, String> {

    public static final String KEY_COLUMN = "gstin";

    public ClientGstRepository() {
        super(ClientGst.class, KEY_COLUMN);
    }
}

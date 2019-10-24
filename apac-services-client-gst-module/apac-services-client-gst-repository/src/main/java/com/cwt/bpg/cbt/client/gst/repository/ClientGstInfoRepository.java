package com.cwt.bpg.cbt.client.gst.repository;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class ClientGstInfoRepository extends CommonRepository<ClientGstInfo, String>
{

    private static final String KEY_COLUMN = "gstin";
    private static final String GROUP_ID_FEILD = "groupId";

    public ClientGstInfoRepository()
    {
        super(ClientGstInfo.class, KEY_COLUMN);
    }

    public List<ClientGstInfo> getAll(String groupId)
    {
        final Query<ClientGstInfo> query = morphia.getDatastore().createQuery(ClientGstInfo.class);
        query.field(GROUP_ID_FEILD).equal(groupId);
        return query.asList();
    }

    public ClientGstInfo getByGstin(String groupId, String gstin)
    {
        final Query<ClientGstInfo> query = morphia.getDatastore().createQuery(ClientGstInfo.class);
        query.field(GROUP_ID_FEILD).equal(groupId);
        query.field(KEY_COLUMN).equal(gstin);
        return query.get();
    }
}

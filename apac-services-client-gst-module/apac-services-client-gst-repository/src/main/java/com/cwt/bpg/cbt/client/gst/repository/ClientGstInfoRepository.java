package com.cwt.bpg.cbt.client.gst.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class ClientGstInfoRepository extends CommonRepository<ClientGstInfo, ObjectId>
{
    private static final String ID = "id";
    private static final String GSTIN_FIELD = "gstin";
    private static final String GROUP_ID_FEILD = "groupId";

    public ClientGstInfoRepository()
    {
        super(ClientGstInfo.class, ID);
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
        query.field(GSTIN_FIELD).equal(gstin);
        return query.get();
    }
}

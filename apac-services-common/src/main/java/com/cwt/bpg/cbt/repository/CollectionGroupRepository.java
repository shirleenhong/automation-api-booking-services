package com.cwt.bpg.cbt.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.upload.model.CollectionGroup;

public class CollectionGroupRepository extends CommonRepository<CollectionGroup, ObjectId>
{
    private static final String ID_FIELD = "id";
    private static final String COLLECTION_NAME_FIELD = "collectionName";
    private static final String IS_ACTIVE_FIELD = "isActive";
    private static final String CREATION_TIMESTAMP_FIELD = "creationTimestamp";

    public CollectionGroupRepository()
    {
        super(CollectionGroup.class, ID_FIELD);
    }

    public CollectionGroup getActiveCollectionGroup(String collectionName)
    {
        Query<CollectionGroup> query = morphia.getDatastore().createQuery(CollectionGroup.class);
        query.field(COLLECTION_NAME_FIELD).equal(collectionName);
        query.field(IS_ACTIVE_FIELD).equal(true);
        query.order(Sort.descending(CREATION_TIMESTAMP_FIELD));

        List<CollectionGroup> collectionGroup = query.asList(new FindOptions().limit(1));

        if (!CollectionUtils.isEmpty(collectionGroup))
        {
            return collectionGroup.get(0);
        }

        return null;
    }
}

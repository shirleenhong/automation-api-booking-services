package com.cwt.bpg.cbt.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.springframework.util.CollectionUtils;

import com.cwt.bpg.cbt.upload.model.CollectionGroup;

public abstract class CollectionGroupRepository extends CommonBackupRepository<CollectionGroup, ObjectId>
{
    private static final String ID_COLUMN = "id";

    public CollectionGroupRepository()
    {
        super(CollectionGroup.class, ID_COLUMN);
    }

    protected CollectionGroup createCollectionGroup(String collectionName)
    {
        CollectionGroup activeGroup = getActiveCollectionGroup(collectionName);
        if (activeGroup != null)
        {
            activeGroup.setActive(false);
            put(activeGroup);
        }

        CollectionGroup newGroup = new CollectionGroup();
        newGroup.setGroupId(UUID.randomUUID().toString());
        newGroup.setCollectionName(collectionName);
        newGroup.setCreationTimestamp(Instant.now());
        newGroup.setActive(true);

        return put(newGroup);
    }

    protected CollectionGroup getActiveCollectionGroup(String collectionName)
    {
        Query<CollectionGroup> query = morphia.getDatastore().createQuery(CollectionGroup.class);
        query.field("collectionName").equal(collectionName);
        query.field("isActive").equal(true);
        query.order(Sort.descending("creationTimestamp"));

        List<CollectionGroup> collectionGroup = query.asList(new FindOptions().limit(1));

        if (!CollectionUtils.isEmpty(collectionGroup))
        {
            return collectionGroup.get(0);
        }

        return null;
    }

    protected void restorePreviousCollection(String groupId)
    {
        Query<CollectionGroup> activeCollectionQuery = morphia.getDatastore().createQuery(CollectionGroup.class);
        activeCollectionQuery.field("isActive").equal(true);

        List<CollectionGroup> activeCollections = activeCollectionQuery.asList();

        if (!CollectionUtils.isEmpty(activeCollections))
        {
            activeCollections.stream().forEach(c -> {
                c.setActive(false);
                put(c);
            });
        }

        Query<CollectionGroup> restorePrevCollectionQuery = morphia.getDatastore().createQuery(CollectionGroup.class);
        restorePrevCollectionQuery.field("groupId").equal(groupId);

        CollectionGroup prevCollection = restorePrevCollectionQuery.get();

        if (prevCollection != null)
        {
            prevCollection.setActive(true);
            put(prevCollection);
        }
    }
}

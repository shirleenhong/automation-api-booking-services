package com.cwt.bpg.cbt.upload.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(value = "collectionGroup", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class CollectionGroup
{
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String groupId;
    private String branchVersion;
    private String collectionName;
    private boolean isActive;
    private Instant creationTimestamp;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getBranchVersion()
    {
        return branchVersion;
    }

    public void setBranchVersion(String branchVersion)
    {
        this.branchVersion = branchVersion;
    }

    public String getCollectionName()
    {
        return collectionName;
    }

    public void setCollectionName(String collectionName)
    {
        this.collectionName = collectionName;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public Instant getCreationTimestamp()
    {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }
}

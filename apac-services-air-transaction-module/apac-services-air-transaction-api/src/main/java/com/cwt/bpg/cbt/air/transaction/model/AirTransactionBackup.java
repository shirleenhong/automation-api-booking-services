package com.cwt.bpg.cbt.air.transaction.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(value = "airTransactionsBackup", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class AirTransactionBackup
{

    @Id
    private ObjectId id;
    
    private String batchId;

    private Instant dateCreated;

    private AirTransaction airTransaction;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getBatchId()
    {
        return batchId;
    }

    public void setBatchId(String batchId)
    {
        this.batchId = batchId;
    }

    public Instant getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public AirTransaction getAirTransaction()
    {
        return airTransaction;
    }

    public void setAirTransaction(AirTransaction airTransaction)
    {
        this.airTransaction = airTransaction;
    }
}

package com.cwt.bpg.cbt.client.gst.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(value = "clientGstInfoBackup", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class ClientGstInfoBackup implements Serializable {

    private static final long serialVersionUID = -8509961686724512791L;

    @Id
    private ObjectId id;

    private Instant dateCreated;

    private ClientGstInfo clientGstInfo;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ClientGstInfo getClientGstInfo() {
        return clientGstInfo;
    }

    public void setClientGstInfo(ClientGstInfo clientGstInfo) {
        this.clientGstInfo = clientGstInfo;
    }
}

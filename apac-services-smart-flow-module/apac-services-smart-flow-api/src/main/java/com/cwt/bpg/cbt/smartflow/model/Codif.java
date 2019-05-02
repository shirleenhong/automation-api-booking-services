package com.cwt.bpg.cbt.smartflow.model;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;

@Entity(value = "codif", noClassnameStored = true)
@Indexes(@Index(fields = { @Field("harpNo"), @Field("keyType"), @Field("gdsPropId") }))
public class Codif implements Serializable {

    private static final long serialVersionUID = 5943172847055937530L;

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @ApiModelProperty(hidden = true)
    private ObjectId id;

    @NotEmpty
    private String harpNo;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String keyType;

    private String crsCode;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String gdsPropId;

    private String lastUpdated;

    private String status;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getHarpNo() {
        return harpNo;
    }

    public void setHarpNo(String harpNo) {
        this.harpNo = harpNo;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getCrsCode() {
        return crsCode;
    }

    public void setCrsCode(String crsCode) {
        this.crsCode = crsCode;
    }

    public String getGdsPropId() {
        return gdsPropId;
    }

    public void setGdsPropId(String gdsPropId) {
        this.gdsPropId = gdsPropId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

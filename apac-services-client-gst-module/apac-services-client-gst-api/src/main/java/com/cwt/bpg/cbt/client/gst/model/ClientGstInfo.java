package com.cwt.bpg.cbt.client.gst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import java.beans.Transient;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(value = "clientGstInfo", noClassnameStored = true)
@Indexes(@Index(fields = @Field("gstin")))
public class ClientGstInfo implements Serializable
{

    private static final long serialVersionUID = 1600168082099452654L;

    @Id
    @NotEmpty
    @ApiModelProperty(required = true)
    private String gstin;
    private String client;
    private String clientEntityName;
    private String businessPhoneNumber;
    private String businessEmailAddress;
    private String entityAddressLine1;
    private String entityAddressLine2;
    private String postalCode;
    private String city;
    private String state;

    public String getGstin()
    {
        return gstin;
    }

    public void setGstin(String gstin)
    {
        this.gstin = gstin;
    }

    public String getClient()
    {
        return client;
    }

    public void setClient(String client)
    {
        this.client = client;
    }

    public String getClientEntityName()
    {
        return clientEntityName;
    }

    public void setClientEntityName(String clientEntityName)
    {
        this.clientEntityName = clientEntityName;
    }

    public String getBusinessPhoneNumber()
    {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber)
    {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getBusinessEmailAddress()
    {
        return businessEmailAddress;
    }

    public void setBusinessEmailAddress(String businessEmailAddress)
    {
        this.businessEmailAddress = businessEmailAddress;
    }

    public String getEntityAddressLine1()
    {
        return entityAddressLine1;
    }

    public void setEntityAddressLine1(String entityAddressLine1)
    {
        this.entityAddressLine1 = entityAddressLine1;
    }

    public String getEntityAddressLine2()
    {
        return entityAddressLine2;
    }

    public void setEntityAddressLine2(String entityAddressLine2)
    {
        this.entityAddressLine2 = entityAddressLine2;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    @Transient
    @JsonIgnore
    public boolean allValuesNull()
    {
        return gstin == null &&
                client == null &&
                clientEntityName == null &&
                businessPhoneNumber == null &&
                businessEmailAddress == null &&
                entityAddressLine1 == null &&
                entityAddressLine2 == null &&
                postalCode == null &&
                city == null &&
                state == null;
    }
}

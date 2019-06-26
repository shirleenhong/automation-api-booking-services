package com.cwt.bpg.cbt.client.gst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.mongodb.morphia.annotations.*;

import javax.validation.constraints.*;
import java.beans.Transient;
import java.io.Serializable;

import static com.cwt.bpg.cbt.client.gst.model.ValidationConstants.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(value = "clientGstInfo", noClassnameStored = true)
@Indexes(@Index(fields = @Field("gstin")))
public class ClientGstInfo implements Serializable {

    private static final long serialVersionUID = 1600168082099452654L;

    @Id
    @NotEmpty(message = GSTIN_EMPTY_ERROR_MSG)
    @Pattern(regexp = GSTIN_REGEX, message = GSTIN_FORMAT_ERROR_MSG)
    @Size(min = GSTIN_LENGTH, max = GSTIN_LENGTH, message = GSTIN_LENGTH_ERROR_MSG)
    @ApiModelProperty(required = true)
    private String gstin;

    private String client;

    @NotEmpty(message = CLIENT_ENTITY_NAME_EMPTY_ERROR_MSG)
    @Pattern(regexp = CLIENT_ENTITY_NAME_REGEX, message = CLIENT_ENTITY_NAME_FORMAT_ERROR_MSG)
    @Size(max = CLIENT_ENTITY_NAME_MAX_LENGTH, message = CLIENT_ENTITY_NAME_LENGTH_ERROR_MSG)
    private String clientEntityName;

    @NotEmpty(message = BUSINESS_PHONE_NUM_EMPTY_ERROR_MSG)
    @Size(max = BUSINESS_PHONE_NUM_MAX_LENGTH, message = BUSINESS_PHONE_NUM_LENGTH_ERROR_MSG)
    @Pattern(regexp = BUSINESS_PHONE_NUM_REGEX, message = BUSINESS_PHONE_NUM_FORMAT_ERROR_MSG)
    private String businessPhoneNumber;

    @NotEmpty(message = BUSINESS_EMAIL_EMPTY_ERROR_MSG)
    @Email(message = BUSINESS_EMAIL_FORMAT_ERROR_MSG)
    @Pattern(regexp = BUSINESS_EMAIL_REGEX, message = BUSINESS_EMAIL_FORMAT_ERROR_MSG)
    @Size(max = BUSINESS_EMAIL_ADDRESS_MAX_LENGTH, message = BUSINESS_EMAIL_LENGTH_ERROR_MSG)
    private String businessEmailAddress;

    @NotEmpty(message = ADDRESS_LINE1_EMPTY_ERROR_MSG)
    @Pattern(regexp = ADDRESS_LINE1_REGEX, message = ADDRESS_LINE1_FORMAT_ERROR_MSG)
    @Size(max = ADDRESS_LINE1_MAX_LENGTH, message = ADDRESS_LINE1_LENGTH_ERROR_MSG)
    private String entityAddressLine1;

    @Pattern(regexp = ADDRESS_LINE2_REGEX, message = ADDRESS_LINE2_FORMAT_ERROR_MSG)
    @Size(max = ADDRESS_LINE2_MAX_LENGTH, message = ADDRESS_LINE2_LENGTH_ERROR_MSG)
    private String entityAddressLine2;

    @NotEmpty(message = POSTAL_CODE_EMPTY_ERROR_MSG)
    @Pattern(regexp = POSTAL_CODE_REGEX, message = POSTAL_CODE_FORMAT_ERROR_MSG)
    @Size(max = POSTAL_CODE_MAX_LENGTH, message = POSTAL_CODE_LENGTH_ERROR_MSG)
    private String postalCode;

    @NotEmpty(message = CITY_EMPTY_ERROR_MSG)
    @Pattern(regexp = CITY_REGEX, message = CITY_FORMAT_ERROR_MSG)
    @Size(max = CITY_MAX_LENGTH, message = CITY_LENGTH_ERROR_MSG)
    private String city;

    @NotEmpty(message = STATE_EMPTY_ERROR_MSG)
    @Pattern(regexp = STATE_REGEX, message = STATE_FORMAT_ERROR_MSG)
    @Size(max = STATE_MAX_LENGTH, message = STATE_LENGTH_ERROR_MSG)
    private String state;

    @ApiModelProperty(allowableValues = "S, P, U")
    private OrgType orgType;

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientEntityName() {
        return clientEntityName;
    }

    public void setClientEntityName(String clientEntityName) {
        this.clientEntityName = clientEntityName;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getBusinessEmailAddress() {
        return businessEmailAddress;
    }

    public void setBusinessEmailAddress(String businessEmailAddress) {
        this.businessEmailAddress = businessEmailAddress;
    }

    public String getEntityAddressLine1() {
        return entityAddressLine1;
    }

    public void setEntityAddressLine1(String entityAddressLine1) {
        this.entityAddressLine1 = entityAddressLine1;
    }

    public String getEntityAddressLine2() {
        return entityAddressLine2;
    }

    public void setEntityAddressLine2(String entityAddressLine2) {
        this.entityAddressLine2 = entityAddressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrgType getOrgType()
    {
        return orgType;
    }

    public void setOrgType(OrgType orgType)
    {
        this.orgType = orgType;
    }

    @Transient
    @JsonIgnore
    public boolean allValuesNull() {
        return gstin == null &&
                client == null &&
                clientEntityName == null &&
                businessPhoneNumber == null &&
                businessEmailAddress == null &&
                entityAddressLine1 == null &&
                entityAddressLine2 == null &&
                postalCode == null &&
                city == null &&
                state == null &&
                orgType == null;
    }
}

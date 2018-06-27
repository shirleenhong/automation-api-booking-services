package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = @Field("eoNumber")))
public class ExchangeOrder implements Serializable {

    private static final long serialVersionUID = 79442657760597469L;
    
    @Id
    private String eoNumber;
    private BigDecimal commission;
    private BigDecimal gstAmount;
    private BigDecimal merchantFee;
    private String countryCode;

    @NotEmpty
    @ApiModelProperty(allowableValues = "CX,CC,INV", required = true)
    private String fopType;

    @NotNull
    @ApiModelProperty(required = true)
    private CreditCard creditCard;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String description;
    private String btaDescription;
    
    
    @NotNull
    @ApiModelProperty(value = "Date in UTC", example="2008-05-29T00:00:00.000Z", required = true)
    @JsonSerialize(using=DateSerializer.class)     
    @JsonDeserialize(using=DateDeserializer.class)
    private Instant additionalInfoDate;
    private String vendorContactPerson;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String productCode;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String vendorCode;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String pnr;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String accountNumber;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String passengerName;
    
    @ApiModelProperty(hidden = true, value = "Date in UTC", example="2008-05-29T14:09:000Z")
    @JsonSerialize(using=DateSerializer.class)     
    @JsonDeserialize(using=DateDeserializer.class) 
    private Instant createDateTime;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String agentId;
    
    @NotEmpty
    @ApiModelProperty(required = true)
    private String pcc;
    private String vendorEmail;
    private String faxNumber;

    private List<Remark> remarks;


    public String getEoNumber() {
		return eoNumber;
	}

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
	}

	public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getMerchantFee() {
        return merchantFee;
    }

    public void setMerchantFee(BigDecimal merchantFee) {
        this.merchantFee = merchantFee;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFopType() {
        return fopType;
    }

    public void setFopType(String fopType) {
        this.fopType = fopType;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBtaDescription() {
        return btaDescription;
    }

    public void setBtaDescription(String btaDescription) {
        this.btaDescription = btaDescription;
    }

    public Instant getAdditionalInfoDate() {
        return additionalInfoDate;
    }

    public void setAdditionalInfoDate(Instant date) {
        this.additionalInfoDate = date;
    }

    public String getVendorContactPerson() {
        return vendorContactPerson;
    }

    public void setVendorContactPerson(String vendorContactPerson) {
        this.vendorContactPerson = vendorContactPerson;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Instant getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Instant createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPcc() {
        return pcc;
    }

    public void setPcc(String pcc) {
        this.pcc = pcc;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }
}

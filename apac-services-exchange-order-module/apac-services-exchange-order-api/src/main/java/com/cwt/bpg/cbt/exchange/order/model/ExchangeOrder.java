package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
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
	@Valid
	@ApiModelProperty(required = true)
	private CreditCard creditCard;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String description;
	private String btaDescription;

	@NotNull
	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z", required = true)
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
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
	private String accountNumber;
	@NotEmpty
	@ApiModelProperty(required = true)
	private String passengerName;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant createDateTime;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String agentId;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String pcc;
	private String vendorEmail;
	private String faxNumber;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String agentName;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String recordLocator;

	@NotNull
	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal nettCost;

	@NotNull
	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal total;

	@NotNull
	@Valid
	@ApiModelProperty(required = true)
	private Header header;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String eoAction;
	
	@NotEmpty
	@ApiModelProperty(required = true)
	private String status;

	@ApiModelProperty(required = false)
	private String raiseCheque;

	@ApiModelProperty(required = false)
	private String tax1;
	
	@ApiModelProperty(required = false)
	private String tax2;

	@NotNull
	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal sellingPrice;

	@NotNull
	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal totalSellingPrice;

	@ApiModelProperty(required = false, value = "CWT Absorb Tag for GST")
	private String absorbGst;

	@ApiModelProperty(required = false, value = "CWT Absorb Tag for Merchant Fee")
	private String absorbMerchantFee;
	
	private List<String> eo
    s;

	private List<String> itineraryRemarks;

	@ApiModelProperty(hidden = true, value = "For reports purposes only")
	private Vendor vendor;
	
	@ApiModelProperty(hidden = true, value = "For testing purposes only. Remove when final template is complete.")
	private String test;
	
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

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

	public void setAdditionalInfoDate(Instant additionalInfoDate) {
		this.additionalInfoDate = additionalInfoDate;
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public BigDecimal getNettCost() {
		return nettCost;
	}

	public void setNettCost(BigDecimal nettCost) {
		this.nettCost = nettCost;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getEoAction() {
		return eoAction;
	}

	public void setEoAction(String eoAction) {
		this.eoAction = eoAction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRaiseCheque() {
		return raiseCheque;
	}

	public void setRaiseCheque(String raiseCheque) {
		this.raiseCheque = raiseCheque;
	}

	public String getTax1() {
		return tax1;
	}

	public void setTax1(String tax1) {
		this.tax1 = tax1;
	}

	public String getTax2() {
		return tax2;
	}

	public void setTax2(String tax2) {
		this.tax2 = tax2;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getTotalSellingPrice() {
		return totalSellingPrice;
	}

	public void setTotalSellingPrice(BigDecimal totalSellingPrice) {
		this.totalSellingPrice = totalSellingPrice;
	}

	public String getAbsorbGst() {
		return absorbGst;
	}

	public void setAbsorbGst(String absorbGst) {
		this.absorbGst = absorbGst;
	}

	public String getAbsorbMerchantFee() {
		return absorbMerchantFee;
	}

	public void setAbsorbMerchantFee(String absorbMerchantFee) {
		this.absorbMerchantFee = absorbMerchantFee;
	}

	public List<String> getEoRemarks() {
		return eoRemarks;
	}

	public void setEoRemarks(List<String> eoRemarks) {
		this.eoRemarks = eoRemarks;
	}

	public List<String> getItineraryRemarks() {
		return itineraryRemarks;
	}

	public void setItineraryRemarks(List<String> itineraryRemarks) {
		this.itineraryRemarks = itineraryRemarks;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
}

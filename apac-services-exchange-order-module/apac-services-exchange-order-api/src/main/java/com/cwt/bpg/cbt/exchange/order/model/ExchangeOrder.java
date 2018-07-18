package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import com.cwt.bpg.cbt.exchange.order.model.validator.NotEmptyIfAnotherFieldNull;
import com.cwt.bpg.cbt.exchange.order.model.validator.NotNullIfAnotherFieldHasValue;
import com.cwt.bpg.cbt.exchange.order.model.validator.NotNullIfAnotherFieldNull;
import org.mongodb.morphia.annotations.*;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
@NotNullIfAnotherFieldNull.List({
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "commission"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "gstAmount"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "merchantFee"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "additionalInfoDate"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "nettCost"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "total"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "header"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "sellingPrice"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "totalSellingPrice"),
		@NotNullIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "vendor"),

})
@NotEmptyIfAnotherFieldNull.List({
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "fopType"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "description"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "productCode"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "accountNumber"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "passengerName"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "agentId"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "pcc"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "agentName"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "recordLocator"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "eoAction"),
		@NotEmptyIfAnotherFieldNull(fieldName = "eoNumber",dependFieldName = "status"),

})
@NotNullIfAnotherFieldHasValue.List({
		@NotNullIfAnotherFieldHasValue(fieldName = "fopType",fieldValue="CC",dependFieldName = "creditCard"),
		@NotNullIfAnotherFieldHasValue(fieldName = "fopType",fieldValue="CX",dependFieldName = "creditCard"),
})
@Entity(value = "exchangeOrderTransactions", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("eoNumber"),@Field("recordLocator")}))
public class ExchangeOrder implements Serializable {

	private static final long serialVersionUID = 79442657760597469L;

	@Id
	private String eoNumber;

	@DecimalMin(value = "0")
	private BigDecimal commission;

	@DecimalMin(value = "0")
	private BigDecimal gstAmount;

	@DecimalMin(value = "0")
	private BigDecimal merchantFee;
	private String countryCode;

	@ApiModelProperty(allowableValues = "CX,CC,INV", required = true)
	private String fopType;

	@Valid
	private CreditCard creditCard;

	@ApiModelProperty(required = true)
	private String description;

	private String btaDescription;

	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z", required = true)
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant additionalInfoDate;

	@ApiModelProperty(required = true)
	private String productCode;

	@ApiModelProperty(required = true)
	private String accountNumber;

	@ApiModelProperty(required = true)
	private String passengerName;

	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant createDateTime;

    @ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
	private Instant updateDateTime;

	@ApiModelProperty(required = true)
	private String agentId;

	@ApiModelProperty(required = true)
	private String pcc;

	private String faxNumber;

	@ApiModelProperty(required = true)
	private String agentName;

	@ApiModelProperty(required = true)
	private String recordLocator;

	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal nettCost;

	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal total;

	@Valid
	@ApiModelProperty(required = true)
	private Header header;

	@ApiModelProperty(required = true)
	private String eoAction;

	@ApiModelProperty(required = true)
	private String status;

	private String raiseCheque;

	private BigDecimal tax1;

	private BigDecimal tax2;

	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal sellingPrice;

	@DecimalMin(value = "0")
	@ApiModelProperty(required = true)
	private BigDecimal totalSellingPrice;

	@ApiModelProperty(value = "CWT Absorb Tag for GST")
	private boolean gstAbsorb;

	@ApiModelProperty(value = "CWT Absorb Tag for Merchant Fee")
	private boolean merchantFeeAbsorb;

	private List<String> eoRemarks;

	private List<String> itineraryRemarks;

	@Valid
	private Vendor vendor;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

    public Instant getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Instant updateDateTime) {
        this.updateDateTime = updateDateTime;
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

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
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

	public boolean isGstAbsorb() {
		return gstAbsorb;
	}

	public void setGstAbsorb(boolean gstAbsorb) {
		this.gstAbsorb = gstAbsorb;
	}

	public boolean isMerchantFeeAbsorb() {
		return merchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(boolean merchantFeeAbsorb) {
		this.merchantFeeAbsorb = merchantFeeAbsorb;
	}
}

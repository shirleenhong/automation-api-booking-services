package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Id;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseExchangeOrder implements Serializable {

	private static final long serialVersionUID = -7547529728116136280L;

	@Id
	private String eoNumber;
	
	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant createDateTime;
	
	@ApiModelProperty(hidden = true, value = "Date in UTC", example = "2008-05-29T14:09:000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
	private Instant updateDateTime;
	
	private String countryCode;
	
	private String recordLocator;

	@ApiModelProperty(required = true, allowableValues = "Email, Print, Request Cheque")
	private EoAction eoAction;

	@ApiModelProperty(required = true, allowableValues = "New, Pending, Completed")
	private EoStatus status;
	
	private BigDecimal total;

	private Boolean gstAbsorb;

	private Boolean merchantFeeAbsorb;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String productCode;

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getEoNumber() {
		return eoNumber;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public EoStatus getStatus() {
		return status;
	}

	public void setStatus(EoStatus status) {
		this.status = status;
	}

	public EoAction getEoAction() {
		return eoAction;
	}

	public void setEoAction(EoAction eoAction) {
		this.eoAction = eoAction;
	}

	public Boolean isGstAbsorb() {
		return gstAbsorb;
	}

	public void setGstAbsorb(Boolean gstAbsorb) {
		this.gstAbsorb = gstAbsorb;
	}

	public Boolean isMerchantFeeAbsorb() {
		return merchantFeeAbsorb;
	}

	public void setMerchantFeeAbsorb(Boolean merchantFeeAbsorb) {
		this.merchantFeeAbsorb = merchantFeeAbsorb;
	}

	public abstract BaseServiceInfo getServiceInfo();

	public abstract BaseVendor getVendor();

}

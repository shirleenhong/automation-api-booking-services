package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.mongodb.morphia.annotations.Id;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseExchangeOrder {
	
	@Id
	private String eoNumber;
	
	private String test;
	
	private ServiceInfo serviceInfo;
	
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
	
	@ApiModelProperty(required = true)
	private EoStatus status;
	
	private Vendor vendor;

	public void setEoNumber(String eoNumber) {
		this.eoNumber = eoNumber;
	}
	
	public abstract String getProductCode();
	
	public BigDecimal total;
	
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

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Vendor getVendor() {
		return vendor;
	}
		
	public abstract String getRaiseCheque();
	
	public abstract String getLastUpdatedByUser();

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

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	
	
	
}

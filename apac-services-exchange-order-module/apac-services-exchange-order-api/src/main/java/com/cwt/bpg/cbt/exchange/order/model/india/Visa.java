package com.cwt.bpg.cbt.exchange.order.model.india;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;

public class Visa implements Serializable {

	private static final long serialVersionUID = 8189018419334701864L;

	private String document;

	private String country;

	private String docType;

	@ApiModelProperty(value = "Date in UTC", example = "2008-05-29T14:09:000Z")
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private Instant applicationDate;

	private String entries;

	private Integer validityLength;

	private String validityType;

	private String processingType;

	private VisaCustomFields customFields;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Instant getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Instant applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(String entries) {
		this.entries = entries;
	}

	public Integer getValidityLength() {
		return validityLength;
	}

	public void setValidityLength(Integer validityLength) {
		this.validityLength = validityLength;
	}

	public String getValidityType() {
		return validityType;
	}

	public void setValidityType(String validityType) {
		this.validityType = validityType;
	}

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}

	public VisaCustomFields getCustomFields() {
		return customFields;
	}

	public void setCustomFields(VisaCustomFields customFields) {
		this.customFields = customFields;
	}
}

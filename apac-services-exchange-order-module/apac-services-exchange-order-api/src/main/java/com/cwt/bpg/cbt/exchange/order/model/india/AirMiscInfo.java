package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity("airMiscInfo")
@Indexes(@Index(fields = @Field("id")))
public class AirMiscInfo extends ClientMiscInfo implements Serializable {

	private static final long serialVersionUID = 3727784764930884517L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;

	private String min;

	private String max;

    private String reportingListId;

    private String mandatory;

    private String clientId;
    
    private String clientAccountNumber;

    private String reportingFieldTypeId;

    private String reportingFieldType;
    
    
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
    public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getReportingListId() {
		return reportingListId;
	}

	public void setReportingListId(String reportingListId) {
		this.reportingListId = reportingListId;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientAccountNumber() {
		return clientAccountNumber;
	}

	public void setClientAccountNumber(String clientAccountNumber) {
		this.clientAccountNumber = clientAccountNumber;
	}

	public String getReportingFieldTypeId() {
		return reportingFieldTypeId;
	}

	public void setReportingFieldTypeId(String reportingFieldTypeId) {
		this.reportingFieldTypeId = reportingFieldTypeId;
	}

	public String getReportingFieldType() {
		return reportingFieldType;
	}

	public void setReportingFieldType(String reportingFieldType) {
		this.reportingFieldType = reportingFieldType;
	}
}

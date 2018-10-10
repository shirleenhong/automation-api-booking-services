package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("airMiscInfo")
@Indexes(@Index(fields = @Field("id")))
public class AirMiscInfo extends ClientMiscInfo implements Serializable {

	private static final long serialVersionUID = 3727784764930884517L;

    private String reportingListId;

    private String clientId;
    
    private String clientAccountNumber;

    private String reportingFieldTypeId;

    private String reportingFieldType;
    
	public String getReportingListId() {
		return reportingListId;
	}

	public void setReportingListId(String reportingListId) {
		this.reportingListId = reportingListId;
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

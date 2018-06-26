package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

@Entity(value = "sequenceNumbers", noClassnameStored = true)
public class SequenceNumber implements Serializable {

	private static final long serialVersionUID = 1702591795635681138L;

	@Id
	private String countryCode;

	@Version
	private Long version;
	
	private int value;
		
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}

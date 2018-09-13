package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "carVendors", noClassnameStored = true)
@Indexes(@Index(fields = @Field("code")))
public class CarVendor implements Serializable {
	
	private static final long serialVersionUID = -8504684108984152096L;

	@Id
	private String code;
	
	private String name;
	
	private String shortName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}

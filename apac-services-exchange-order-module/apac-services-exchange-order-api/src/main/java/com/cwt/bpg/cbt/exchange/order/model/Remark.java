package com.cwt.bpg.cbt.exchange.order.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.io.Serializable;

@Entity(value = "remarkList", noClassnameStored = true)
@Indexes(@Index(fields = @Field("_id")))
public class Remark implements Serializable {
	private static final long serialVersionUID = -756695804842165089L;

	@Id
	private String id;
	
	@NotEmpty
	private String productType;

	@NotEmpty
	private String remarkType;
	
	@NotEmpty
	private String countryCode;

	@NotEmpty
	private String text;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

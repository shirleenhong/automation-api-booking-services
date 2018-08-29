package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(value = "remarkList", noClassnameStored = true)
@Indexes(@Index(fields = @Field("_id")))
public class Remark implements Serializable {
	private static final long serialVersionUID = -756695804842165089L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;
	
	@NotEmpty
	private String productType;

	@NotEmpty
	private String remarkType;
	
	@NotEmpty
	private String countryCode;

	@NotEmpty
	private String text;


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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

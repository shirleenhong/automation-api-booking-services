package com.cwt.bpg.cbt.airline.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "agentIfo", noClassnameStored = true)
@Indexes(@Index(fields = @Field("uid")))
public class AgentInfo implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String uid;

	private String phone;

	private String countryCode;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}

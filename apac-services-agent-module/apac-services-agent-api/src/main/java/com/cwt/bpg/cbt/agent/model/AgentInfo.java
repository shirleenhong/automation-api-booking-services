package com.cwt.bpg.cbt.agent.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "agentInfo", noClassnameStored = true)
@Indexes(@Index(fields = @Field("uid")))
public class AgentInfo implements Serializable {

	private static final long serialVersionUID = 5943172847055937530L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	@ApiModelProperty(hidden = true)
	private ObjectId id;
	
	@NotEmpty
	@ApiModelProperty(required = true)
	private String uid;

	private String phone;

	private String countryCode;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
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

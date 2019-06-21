package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "insurance", noClassnameStored = true)
@Indexes(@Index(fields = @Field("id")))
public class InsurancePlan implements Serializable {

	private static final long serialVersionUID = 5189419687629453470L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	@ApiModelProperty(hidden = true)
	private ObjectId id;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String type;
	
	@NotNull
	@ApiModelProperty(required = true)
	private Float commission;
	
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

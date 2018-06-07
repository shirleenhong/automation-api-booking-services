package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import io.swagger.annotations.ApiModelProperty;

@Entity(value = "airlineRules", noClassnameStored = true)
@Indexes(@Index(fields = @Field("code")))
public class AirlineRule implements Serializable {

	private static final long serialVersionUID = -3604480842389246624L;

	@Id
	@NotEmpty
	@ApiModelProperty(required = true)
	private String code;
	private boolean includeYqCommission;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isIncludeYqCommission() {
		return includeYqCommission;
	}

	public void setIncludeYqCommission(boolean includeYqCommission) {
		this.includeYqCommission = includeYqCommission;
	}
}

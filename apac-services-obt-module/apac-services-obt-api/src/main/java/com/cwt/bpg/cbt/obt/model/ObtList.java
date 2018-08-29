package com.cwt.bpg.cbt.obt.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Entity(value = "obtList", noClassnameStored = true)
@Indexes(@Index(fields = @Field("countryCode")))
public class ObtList implements Serializable {

    private static final long serialVersionUID = -7039713787520750000L;

    @Id
    @NotEmpty
	@ApiModelProperty(required = true)
    private String countryCode;

    @Valid
	private List<Obt> obts;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<Obt> getObts() {
		return obts;
	}

	public void setObts(List<Obt> obts) {
		this.obts = obts;
	}
}

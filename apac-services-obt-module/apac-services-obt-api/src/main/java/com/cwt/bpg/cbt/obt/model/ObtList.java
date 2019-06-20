package com.cwt.bpg.cbt.obt.model;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
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
	private List<OnlineBookingTool> onlineBookingTools;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<OnlineBookingTool> getOnlineBookingTools() {
		return onlineBookingTools;
	}

	public void setOnlineBookingTools(List<OnlineBookingTool> onlineBookingTools) {
		this.onlineBookingTools = onlineBookingTools;
	}
}

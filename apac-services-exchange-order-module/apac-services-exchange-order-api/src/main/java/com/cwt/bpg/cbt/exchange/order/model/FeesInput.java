package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class FeesInput implements Serializable {

	private static final long serialVersionUID = -5237125856544162255L;

	private String countryCode;

	@NotEmpty
	private String profileName;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

}

package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.io.Serializable;

public class AgentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5496967155781607054L;
	
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

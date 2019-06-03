package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.io.Serializable;

public class LineDefinitionClientMapping implements Serializable {

	private static final long serialVersionUID = -5508357866779755478L;

	private Integer mappingId;

	private int configInstanceKeyId;

	private String dummyBar;

	private Integer clientId;

	private int lineDefMasterId;

	private String profilePcc;
	
	private String profileName;
	
	private int preferred;

	public Integer getMappingId() {
		return mappingId;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public int getConfigInstanceKeyId() {
		return configInstanceKeyId;
	}

	public void setConfigInstanceKeyId(int configInstanceKeyId) {
		this.configInstanceKeyId = configInstanceKeyId;
	}

	public String getDummyBar() {
		return dummyBar;
	}

	public void setDummyBar(String dummyBar) {
		this.dummyBar = dummyBar;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getLineDefMasterId() {
		return lineDefMasterId;
	}

	public void setLineDefMasterId(int lineDefMasterId) {
		this.lineDefMasterId = lineDefMasterId;
	}

	public String getProfilePcc() {
		return profilePcc;
	}

	public void setProfilePcc(String profilePcc) {
		this.profilePcc = profilePcc;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public int getPreferred() {
		return preferred;
	}

	public void setPreferred(int preferred) {
		this.preferred = preferred;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

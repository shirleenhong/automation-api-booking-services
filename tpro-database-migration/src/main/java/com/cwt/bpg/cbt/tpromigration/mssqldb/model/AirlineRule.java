package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

public class AirlineRule {

	private String code;
	private boolean includeYqComm;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isIncludeYqComm() {
		return includeYqComm;
	}

	public void setIncludeYqComm(boolean includeYqComm) {
		this.includeYqComm = includeYqComm;
	}
}

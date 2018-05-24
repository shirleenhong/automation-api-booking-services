package com.cwt.bpg.cbt.exchange.order.model;

public class InProduct extends Product {
	private String gdsCode;
	private String ot1;
	private String ot2;
	private String requireCdr;
	private Boolean requireTicket;
	private Boolean suppressMi;
	private Double feeCode;
	private Boolean appMF;
	private Boolean overridePc;
	private Boolean hotelFee;

	public String getGdsCode() {
		return gdsCode;
	}

	public void setGdsCode(String gdsCode) {
		this.gdsCode = gdsCode;
	}

	public String getOt1() {
		return ot1;
	}

	public void setOt1(String ot1) {
		this.ot1 = ot1;
	}

	public String getOt2() {
		return ot2;
	}

	public void setOt2(String ot2) {
		this.ot2 = ot2;
	}

	public String getRequireCdr() {
		return requireCdr;
	}

	public void setRequireCdr(String requireCdr) {
		this.requireCdr = requireCdr;
	}

	public Boolean getRequireTicket() {
		return requireTicket;
	}

	public void setRequireTicket(Boolean requireTicket) {
		this.requireTicket = requireTicket;
	}

	public Boolean getSuppressMi() {
		return suppressMi;
	}

	public void setSuppressMi(Boolean suppressMi) {
		this.suppressMi = suppressMi;
	}

	public Double getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(Double feeCode) {
		this.feeCode = feeCode;
	}

	public Boolean getAppMF() {
		return appMF;
	}

	public void setAppMF(Boolean appMF) {
		this.appMF = appMF;
	}

	public Boolean getOverridePc() {
		return overridePc;
	}

	public void setOverridePc(Boolean overridePc) {
		this.overridePc = overridePc;
	}

	public Boolean getHotelFee() {
		return hotelFee;
	}

	public void setHotelFee(Boolean hotelFee) {
		this.hotelFee = hotelFee;
	}
}

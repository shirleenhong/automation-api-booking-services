package com.cwt.bpg.cbt.exchange.order.model;

public class InProduct extends Product {
	
	private static final long serialVersionUID = 1L;
	private String gdsCode;
	private Double ot1;
	private Double ot2;
	private Boolean requireCdr;
	private Boolean requireTicket;
	private Boolean suppressMi;
	private Double feeCode;
	private Boolean appMf;
	private Boolean overridePc;
	private Boolean hotelFee;

	public String getGdsCode() {
		return gdsCode;
	}

	public void setGdsCode(String gdsCode) {
		this.gdsCode = gdsCode;
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

	public Boolean getAppMf() {
		return appMf;
	}

	public void setAppMf(Boolean appMf) {
		this.appMf = appMf;
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

	public Double getOt1() {
		return ot1;
	}

	public void setOt1(Double ot1) {
		this.ot1 = ot1;
	}

	public Double getOt2() {
		return ot2;
	}

	public void setOt2(Double ot2) {
		this.ot2 = ot2;
	}

	public Boolean getRequireCdr() {
		return requireCdr;
	}

	public void setRequireCdr(Boolean requireCdr) {
		this.requireCdr = requireCdr;
	}
	
}

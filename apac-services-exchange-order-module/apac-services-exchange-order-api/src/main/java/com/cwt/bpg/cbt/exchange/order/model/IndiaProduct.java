package com.cwt.bpg.cbt.exchange.order.model;

public class IndiaProduct extends BaseProduct {
	
	private static final long serialVersionUID = 1L;
	private String gdsCode;
	private Double ot1Percent;
	private Double ot2Percent;
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

	public Double getOt1Percent() {
		return ot1Percent;
	}

	public void setOt1Percent(Double ot1Percent) {
		this.ot1Percent = ot1Percent;
	}

	public Double getOt2Percent() {
		return ot2Percent;
	}

	public void setOt2Percent(Double ot2Percent) {
		this.ot2Percent = ot2Percent;
	}

	public Boolean getRequireCdr() {
		return requireCdr;
	}

	public void setRequireCdr(Boolean requireCdr) {
		this.requireCdr = requireCdr;
	}
	
}

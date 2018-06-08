package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

public class InProduct extends com.cwt.bpg.cbt.exchange.order.model.IndiaProduct {

	private static final long serialVersionUID = 6599854149265489189L;
	private String gstFormula;

	public String getGstFormula() {
		return gstFormula;
	}

	public void setGstFormula(String gstFormula) {
		this.gstFormula = gstFormula;
	}
}

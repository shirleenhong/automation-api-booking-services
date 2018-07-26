package com.cwt.bpg.cbt.exchange.order.model;

import java.util.List;

public class MiscChargeOrder {

	private MiscChargeOrderInfo mcoInfo;
	private String traveler;
	private List<String> remarks;

	public MiscChargeOrderInfo getMcoInfo() {
		return mcoInfo;
	}

	public void setMcoInfo(MiscChargeOrderInfo mcoInfo) {
		this.mcoInfo = mcoInfo;
	}

	public String getTraveler() {
		return traveler;
	}

	public void setTraveler(String traveler) {
		this.traveler = traveler;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

}

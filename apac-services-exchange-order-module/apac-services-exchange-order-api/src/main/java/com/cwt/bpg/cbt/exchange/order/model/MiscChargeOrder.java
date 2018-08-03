package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

public class MiscChargeOrder implements Serializable {

	private static final long serialVersionUID = -5288263305248747374L;

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

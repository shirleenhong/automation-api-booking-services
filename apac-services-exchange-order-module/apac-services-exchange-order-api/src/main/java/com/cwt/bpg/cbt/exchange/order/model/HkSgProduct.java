package com.cwt.bpg.cbt.exchange.order.model;

public class HkSgProduct extends Product {

	private Boolean enableCcFop;
	private Boolean fullComm;
	private Boolean mi;
	private Boolean tktNo;

	public Boolean getEnableCcFop() {
		return enableCcFop;
	}

	public void setEnableCcFop(Boolean enableCcFop) {
		this.enableCcFop = enableCcFop;
	}

	public Boolean getFullComm() {
		return fullComm;
	}

	public void setFullComm(Boolean fullComm) {
		this.fullComm = fullComm;
	}

	public Boolean getMi() {
		return mi;
	}

	public void setMi(Boolean mi) {
		this.mi = mi;
	}

	public Boolean getTktNo() {
		return tktNo;
	}

	public void setTktNo(Boolean tktNo) {
		this.tktNo = tktNo;
	}
}

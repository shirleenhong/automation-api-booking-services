package com.cwt.bpg.cbt.exchange.order.model;

public class HkSgProduct extends Product {

	private static final long serialVersionUID = 2104719481136370225L;
	
	private Boolean enableCcFop;
	private Boolean fullCommission;
	private Boolean mi;
	private Boolean ticketNumber;

	public Boolean getEnableCcFop() {
		return enableCcFop;
	}

	public void setEnableCcFop(Boolean enableCcFop) {
		this.enableCcFop = enableCcFop;
	}

	public Boolean getMi() {
		return mi;
	}

	public void setMi(Boolean mi) {
		this.mi = mi;
	}

	public Boolean getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Boolean ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Boolean getFullCommission() {
		return fullCommission;
	}

	public void setFullCommission(Boolean fullCommission) {
		this.fullCommission = fullCommission;
	}
}

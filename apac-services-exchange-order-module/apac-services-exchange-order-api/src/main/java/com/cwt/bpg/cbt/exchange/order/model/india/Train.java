package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class Train implements Serializable {

	private static final long serialVersionUID = 2737677039047402942L;

	private String number;

	private String name;

	private String cabinClass;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}

}

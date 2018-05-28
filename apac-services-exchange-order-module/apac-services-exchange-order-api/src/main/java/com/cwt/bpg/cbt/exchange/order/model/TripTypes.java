package com.cwt.bpg.cbt.exchange.order.model;

public enum TripTypes {
	
	INT(1),
	DOM(2),
	LCC(22);	

	private final int id;

	private TripTypes(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

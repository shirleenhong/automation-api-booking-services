package com.cwt.bpg.cbt.exchange.order.model;

public enum TripTypes {
	
	INTERNATIONAL("I"),
	DOMESTIC("D"),
	LCC("L");	

	private final String code;

	private TripTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static boolean isInternational(String tripType) {
		
		return INTERNATIONAL.getCode().equalsIgnoreCase(tripType);
	}
}

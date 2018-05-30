package com.cwt.bpg.cbt.exchange.order.model;

public enum TripTypes {
	
	INTERNATIONAL(1),
	DOMESTIC(2),
	LCC(22);	

	private final int id;

	private TripTypes(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static boolean isInternational(String tripType) {
		
		return tripType != null 
				? tripType.equalsIgnoreCase(INTERNATIONAL.toString()) 
				: false;
	}
}

package com.cwt.bpg.cbt.exchange.order.model;

import java.util.ArrayList;
import java.util.List;

public enum ClientTypes {

	DU("DU"), 
	DB("DB"), 
	MN("MN"),
	TF("TF"),
	MG("MG"),
	TP("TP");

	private final String code;
	
	private final static List<String> discountClients = new ArrayList<>();
	
	public static List<String> discountClients () {
		
		if(discountClients.isEmpty()) {
			discountClients.add(DU.getCode());
			discountClients.add(DB.getCode());
			discountClients.add(MN.getCode());
			discountClients.add(TF.getCode());
			discountClients.add(TP.getCode());				
		}
		
		return discountClients;
	}

	private ClientTypes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

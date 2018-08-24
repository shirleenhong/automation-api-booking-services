package com.cwt.bpg.cbt.exchange.order.model;

import java.util.ArrayList;
import java.util.List;

public enum ClientType {

	DU("DU"),
	DB("DB"),
	MN("MN"),
	TF("TF"),
	MG("MG"),
	TP("TP");

	private final String code;

	private static final List<String> discountClients = new ArrayList<>();

	ClientType(String code) {
		this.code = code;
	}

	public static List<String> clientsWithDiscount() {

		if (discountClients.isEmpty()) {
			discountClients.add(DU.getCode());
			discountClients.add(DB.getCode());
			discountClients.add(MN.getCode());
			discountClients.add(TF.getCode());
			discountClients.add(TP.getCode());
		}

		return discountClients;
	}

	public String getCode() {
		return code;
	}
}

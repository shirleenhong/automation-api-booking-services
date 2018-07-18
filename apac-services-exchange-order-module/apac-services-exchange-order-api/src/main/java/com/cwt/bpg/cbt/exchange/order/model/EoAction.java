package com.cwt.bpg.cbt.exchange.order.model;

public enum EoAction {
	PRINT("Print"),
	EMAIL("Email"),
	REQUEST_CHEQUE("Request Cheque");

	private final String value;

	private EoAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return value;
	}

	public static EoAction getEoAction(String value) {

		for (EoAction action : EoAction.values()) {
			if(action.getValue().equalsIgnoreCase(value)) {
				return action;
			}
		}
		throw new IllegalArgumentException("EO Action not supported");
	}

}

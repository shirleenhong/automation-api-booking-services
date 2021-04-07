package com.cwt.bpg.cbt.calculator.model;

public enum Country {

	HONG_KONG("HK", "1"),
	SINGAPORE("SG", "2"),
	AUSTRALIA("AU", "4"),
	NEW_ZEALAND("NZ", "5"),
	INDIA("IN", "3"),
	THAILAND("TH", "6");

	private final String code;
	private final String id;

	private Country(String code, String id) {
		this.code = code;
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public String getId() {
		return id;
	}

	public static Country getCountry(String countryCode) {

		for (Country country : Country.values()) {
			if(country.getCode().equalsIgnoreCase(countryCode)) {
				return country;
			}
		}
		throw new IllegalArgumentException("Country not supported");
	}

}

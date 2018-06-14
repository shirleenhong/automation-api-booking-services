package com.cwt.bpg.cbt.calculator.model;

public enum Country {

	HONG_KONG("HK", "852"),
	SINGAPORE("SG", "65"),
	AUSTRALIA("AU", "61"),
	NEW_ZEALAND("NZ", "64"),
	INDIA("IN", "91");

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

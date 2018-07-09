package com.cwt.bpg.cbt.calculator.model;

import org.junit.Test;

import static com.cwt.bpg.cbt.calculator.model.Country.getCountry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CountryTest {

	private Country country = Country.HONG_KONG;

	@Test
	public void shouldReturnCorrectCountryCode() {
		assertNotNull(country.getCode());
	}

	@Test
	public void shouldReturnCorrectCountryId() {
		assertNotNull(country.getId());
	}

	@Test
	public void shouldReturnCorrectCountry() {
		assertEquals(getCountry(country.getCode()), country);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotReturnACountry() {
		getCountry("MNL");
	}
}
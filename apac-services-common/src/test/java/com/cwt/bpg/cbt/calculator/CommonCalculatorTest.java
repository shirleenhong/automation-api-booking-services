package com.cwt.bpg.cbt.calculator;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class CommonCalculatorTest {

	private CommonCalculator calculator = new CommonCalculator();

	@Test
	public void shouldReturnSafeBigDecimal() {
		BigDecimal input = null;
		BigDecimal safeValue = calculator.safeValue(input);
		assertThat(safeValue, is(equalTo(BigDecimal.ZERO)));

		input = new BigDecimal(10.00);
		safeValue = calculator.safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}

	@Test
	public void shouldReturnSafeString() {
		String input = null;
		String safeValue = calculator.safeValue(input);
		assertThat(safeValue, isEmptyString());

		input = "abc";
		safeValue = calculator.safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}

	@Test
	public void safeValueShouldDoNothingWhenValueIsNotNull() {
		BigDecimal decimal = new BigDecimal(10.00);
		BigDecimal safeValue = calculator.safeValue(decimal);
		assertThat(safeValue, is(equalTo(safeValue)));
	}

	@Test
	public void roundShouldReturnNullWhenAmountIsNull() {
		BigDecimal roundedDecimal = calculator.round(null, 2);
		assertThat(roundedDecimal, nullValue(BigDecimal.class));
	}

	@Test
	public void roundShouldSetScaleTo2WhenCountryCodeIsSG() {
		BigDecimal decimal = new BigDecimal("10");
		BigDecimal roundedDecimal = calculator.round(decimal, 2);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("10.00"))));
	}

	@Test
	public void roundShouldSetScaleTo0WhenCountryCodeIsHK() {
		BigDecimal decimal = new BigDecimal("10.49");
		BigDecimal roundedDecimal = calculator.round(decimal, 0);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("10"))));
	}

	@Test
	public void roundShouldSetScaleTo0WhenCountryCodeIsIN() {
		BigDecimal decimal = new BigDecimal("10.51");
		BigDecimal roundedDecimal = calculator.round(decimal, 0);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("11"))));
	}

	@Test
	public void percentDecimalReturnsZeroWhenValueIsNull() {
		BigDecimal percentDecimal = calculator.percentDecimal(0D);
		assertThat(percentDecimal.doubleValue(), is(equalTo(0.00)));
	}

	@Test
	public void percentDecimalReturnsNonZeroWhenValueIsNotNull() {
		BigDecimal percentDecimal = calculator.percentDecimal(5D);
		assertThat(percentDecimal.doubleValue(), is(equalTo(0.05)));
	}

	@Test
	public void calculatePercentageShouldReturnZeroWhenInputIsNullOrPercentIsNull() {
		BigDecimal decimal = calculator.calculatePercentage(null, 2D);
		assertThat(decimal.doubleValue(), is(equalTo(0D)));

		decimal = calculator.calculatePercentage(new BigDecimal(20), null);
		assertThat(decimal.doubleValue(), is(equalTo(0D)));
	}

	@Test
	public void shouldCalculatePercentage() {
		BigDecimal decimal = calculator.calculatePercentage(new BigDecimal(20), 20D);
		assertThat(decimal.doubleValue(), is(equalTo(4D)));
	}

	@Test
	public void shouldRoundUp() {
		assertThat(calculator.round(new BigDecimal(3.5), 0).doubleValue(), is(equalTo(4D)));
		assertThat(calculator.round(new BigDecimal(3.51), 0).doubleValue(), is(equalTo(4D)));
	}

	@Test
	public void shouldRoundDown() {
		assertThat(calculator.round(new BigDecimal(3.4), 0).doubleValue(), is(equalTo(3D)));
		assertThat(calculator.round(new BigDecimal(3.49), 0).doubleValue(), is(equalTo(3D)));
	}

	@Test
	public void shouldReturnSafeInteger() {
		Integer input = null;
		Integer safeValue = calculator.safeValue(input);
		assertThat(safeValue, is(equalTo(Integer.valueOf(0))));

		input = 100;
		safeValue = calculator.safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}

}

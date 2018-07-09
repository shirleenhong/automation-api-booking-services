package com.cwt.bpg.cbt.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.safeValue;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.junit.Test;

public class CalculatorUtilsTest
{
	@Test(expected = InvocationTargetException.class)
	public void shouldNotBeInstantiated() throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		Constructor<CalculatorUtils> constructor = CalculatorUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void shouldReturnSafeBigDecimal() {
		BigDecimal input = null;
		BigDecimal safeValue = safeValue(input);
		assertThat(safeValue, is(equalTo(BigDecimal.ZERO)));

		input = new BigDecimal(10);
		safeValue = safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}

	@Test
	public void shouldReturnSafeString() {
		String input = null;
		String safeValue = safeValue(input);
		assertThat(safeValue, isEmptyString());

		input = "abc";
		safeValue = safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}

	@Test
	public void safeValueShouldDoNothingWhenValueIsNotNull() {
		BigDecimal decimal = new BigDecimal(10);
		BigDecimal safeValue = safeValue(decimal);
		assertThat(safeValue, is(equalTo(safeValue)));
	}

	@Test
	public void roundShouldReturnNullWhenAmountIsNull() {
		BigDecimal roundedDecimal = CalculatorUtils.round(null, 2);
		assertThat(roundedDecimal, nullValue(BigDecimal.class));
	}

	@Test
	public void roundShouldSetScaleTo2WhenCountryCodeIsSG() {
		BigDecimal decimal = new BigDecimal("10");
		BigDecimal roundedDecimal = CalculatorUtils.round(decimal, 2);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("10.00"))));
	}

	@Test
	public void roundShouldSetScaleTo0WhenCountryCodeIsHK() {
		BigDecimal decimal = new BigDecimal("10.49");
		BigDecimal roundedDecimal = CalculatorUtils.round(decimal, 0);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("10"))));
	}

	@Test
	public void roundShouldSetScaleTo0WhenCountryCodeIsIN() {
		BigDecimal decimal = new BigDecimal("10.51");
		BigDecimal roundedDecimal = CalculatorUtils.round(decimal, 0);
		assertThat(roundedDecimal, is(equalTo(new BigDecimal("11"))));
	}

	@Test
	public void percentDecimalReturnsZeroWhenValueIsNull() {
		BigDecimal percentDecimal = CalculatorUtils.percentDecimal(null);
		assertThat(percentDecimal.doubleValue(), is(equalTo(0d)));
	}

	@Test
	public void percentDecimalReturnsNonZeroWhenValueIsNotNull() {
		BigDecimal percentDecimal = CalculatorUtils.percentDecimal(5d);
		assertThat(percentDecimal.doubleValue(), is(equalTo(0.05)));
	}

	@Test
	public void calculatePercentageShouldReturnZeroWhenInputIsNullOrPercentIsNull() {
		BigDecimal decimal = CalculatorUtils.calculatePercentage(null, 2d);
		assertThat(decimal.doubleValue(), is(equalTo(0d)));

		decimal = CalculatorUtils.calculatePercentage(new BigDecimal(20), null);
		assertThat(decimal.doubleValue(), is(equalTo(0d)));
	}

	@Test
	public void shouldCalculatePercentage() {
		BigDecimal decimal = CalculatorUtils.calculatePercentage(new BigDecimal(20), 20d);
		assertThat(decimal.doubleValue(), is(equalTo(4d)));
	}

	@Test
	public void shouldRoundUp() {
		assertThat(CalculatorUtils.round(new BigDecimal(3.5), 0).doubleValue(), is(equalTo(4d)));
		assertThat(CalculatorUtils.round(new BigDecimal(3.51), 0).doubleValue(), is(equalTo(4d)));
	}

	@Test
	public void shouldRoundDown() {
		assertThat(CalculatorUtils.round(new BigDecimal(3.4), 0).doubleValue(), is(equalTo(3d)));
		assertThat(CalculatorUtils.round(new BigDecimal(3.49), 0).doubleValue(), is(equalTo(3d)));
	}

	@Test
	public void shouldReturnSafeInteger() {
		Integer input = null;
		Integer safeValue = safeValue(input);
		assertThat(safeValue, is(equalTo(Integer.valueOf(0))));

		input = 100;
		safeValue = safeValue(input);
		assertThat(safeValue, is(equalTo(input)));
	}
}

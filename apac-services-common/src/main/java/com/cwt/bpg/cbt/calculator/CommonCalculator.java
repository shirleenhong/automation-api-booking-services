package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CommonCalculator {

	private MathContext mc = new MathContext(2, RoundingMode.HALF_UP);

	private static final String COUNTRY_CODE_INDIA = "IN";
	private static final String COUNTRY_CODE_HONGKONG = "HK";

	public BigDecimal safeValue(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}

	public BigDecimal round(BigDecimal amount, String countryCode) {
		if (amount == null) {
			return null;
		}

		if (countryCode != null && (countryCode.equals(COUNTRY_CODE_INDIA)
				|| countryCode.equals(COUNTRY_CODE_HONGKONG))) {
			return round(amount, 0);
		}
		else {
			return round(amount, 2);
		}
	}

	public BigDecimal round(BigDecimal d, int scale) {
		return d.setScale(scale, RoundingMode.HALF_UP);
	}

	protected BigDecimal getPercentageAmount(BigDecimal baseAmount, BigDecimal amountInput, Double percent) {
		return amountInput != null ? amountInput : baseAmount.multiply(new BigDecimal(percent, MathContext.DECIMAL64)).divide(new BigDecimal(100));
	}

	protected BigDecimal calculatePercentage(BigDecimal input, Double percent) {
		return safeValue(input).multiply(percentDecimal(percent));
	}

	protected BigDecimal percentDecimal(Double value) {
		return BigDecimal.valueOf(safeValue(value) * 0.01);
	}

	private double safeValue(Double value) {
		return value == null ? 0D : value;
	}

	protected BigDecimal getValue(Double value) {
		return new BigDecimal(value, MathContext.DECIMAL64);
	}

	public MathContext getMc() {
		return mc;
	}
}

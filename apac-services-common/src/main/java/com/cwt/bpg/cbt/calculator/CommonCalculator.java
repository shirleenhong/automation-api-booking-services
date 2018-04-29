package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonCalculator {

	protected BigDecimal safeValue(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}

	private double safeValue(Double value) {
		return value == null ? 0D : value;
	}

	protected String safeValue(String value) {
		return value == null ? "" : value;
	}

	protected BigDecimal round(BigDecimal amount, int scale) {
		if (amount == null) {
			return null;
		}
		return amount.setScale(scale, RoundingMode.HALF_UP);
	}

	protected BigDecimal calculatePercentage(BigDecimal input, Double percent) {
		return safeValue(input).multiply(percentDecimal(percent));
	}

	protected BigDecimal percentDecimal(Double value) {
		return BigDecimal.valueOf(safeValue(value) * 0.01);
	}

}

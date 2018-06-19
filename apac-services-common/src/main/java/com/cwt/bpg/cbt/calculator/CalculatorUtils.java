package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CalculatorUtils {

	private CalculatorUtils() {
		throw new IllegalStateException("utility class");
	}

	public static String safeValue(String value) {
		return value == null ? "" : value;
	}

	public static BigDecimal safeValue(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}

	public static double safeValue(Double value) {
		return value == null ? 0D : value;
	}

	public static int safeValue(Integer value) {
		return value == null ? 0 : value;
	}

	public static BigDecimal round(BigDecimal amount, int scale) {
		return amount == null ? null : amount.setScale(scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal calculatePercentage(BigDecimal input, Double percent) {
		return safeValue(input).multiply(percentDecimal(percent));
	}

	public static BigDecimal percentDecimal(Double value) {
		return BigDecimal.valueOf(safeValue(value) * 0.01);
	}
}
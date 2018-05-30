package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonCalculator {

	protected static BigDecimal safeValue(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}

	protected static double safeValue(Double value) {
		return value == null ? 0D : value;
	}

	protected static String safeValue(String value) {
		return value == null ? "" : value;
	}

	protected static BigDecimal round(BigDecimal amount, int scale) {
		if (amount == null) {
			return null;
		}
		return amount.setScale(scale, RoundingMode.HALF_UP);
	}

	protected static BigDecimal calculatePercentage(BigDecimal input, Double percent) {
		return safeValue(input).multiply(percentDecimal(percent));
	}

	protected static BigDecimal percentDecimal(Double value) {
		return BigDecimal.valueOf(safeValue(value) * 0.01);
	}
	
	protected static int safeValue(Integer value) {
		if (value == null) {
			return 0;
		}
		return value;
	}
}

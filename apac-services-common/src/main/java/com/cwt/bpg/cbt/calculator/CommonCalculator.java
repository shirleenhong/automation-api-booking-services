package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CommonCalculator {

	public BigDecimal safeValue(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}
		
	public BigDecimal round(BigDecimal amount, int scale) {
		
		if (amount == null) {
			return null;
		}
		
		return amount.setScale(scale, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getPercentageAmount(
			BigDecimal baseAmount, BigDecimal amountInput, Double percentage) {
		
		return amountInput != null 
				? amountInput 
				: baseAmount.multiply(new BigDecimal(percentage, MathContext.DECIMAL64)).divide(new BigDecimal(100));
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
	
}

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
		
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
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
	
	/**
	 * Get percentage value given the percent value
	 * @param input
	 * @param percent
	 * @return
	 */
	public BigDecimal applyPercentage(BigDecimal input, Double percent) {
		return safeValue(input).multiply(getPercentage(percent));				
	}

	/**
	 * Get value in percent
	 * @param value
	 * @return
	 */
	public BigDecimal getPercentage(Double value) {
		return BigDecimal.valueOf(safeValue(value) * 0.01);
	}
	
	private double safeValue(Double value) {
		return value == null ? 0D : value ;
	}

	/**
	 * Get Big Decimal value from Double input
	 * @param value
	 * @return
	 */
	public BigDecimal getValue(Double value) {
		return new BigDecimal(value, MathContext.DECIMAL64);
	}
	
}

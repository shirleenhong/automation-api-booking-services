package com.cwt.bpg.cbt.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CommonCalculator {
	
	private static final int DEFAULT_SCALE = 2;

	private MathContext mc = new MathContext(4, RoundingMode.HALF_UP);

	public final String COUNTRY_CODE_INDIA = "IN";
	public final String COUNTRY_CODE_HONGKONG = "HK";

	public BigDecimal safeValue(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		return value;
	}

	public BigDecimal roundAmount(BigDecimal amount, String countryCode) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		// TODO: move precision to mongo config
		if (countryCode != null && (countryCode.equals(COUNTRY_CODE_INDIA)
				|| countryCode.equals(COUNTRY_CODE_HONGKONG))) {
			return round(amount, 0);
		} else {
			return round(amount, 2);
		}
	}
	
	public BigDecimal roundAmount(BigDecimal amount, int scale) {
		
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		
		return round(amount, scale);
		
	}

	public BigDecimal round(BigDecimal d, int scale) {
		return d.setScale(scale, RoundingMode.HALF_UP);
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
	public BigDecimal getValue(BigDecimal input, Double percent) {
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

	public MathContext getMc() {
		return mc;
	}

	public BigDecimal roundUp(BigDecimal value) {
		return value.setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
	}	

}

package com.cwt.bpg.cbt.exchange.order;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;

// TODO: test only, will remove this
public class SafeValueTest extends CommonCalculator {

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(20);
		BigDecimal b = null;
		
		BigDecimal c = safeValue(a).add(b);
		
		System.out.println(c);
		
	}

}

package com.cwt.bpg.cbt.exchange.order.calculator;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class HkAirCalculatorTest {
	private Calculator calculator = new HkAirCalculator();
	

	@Test
	public void shouldCalculateWithoutSpecialFormula() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		
	}

}

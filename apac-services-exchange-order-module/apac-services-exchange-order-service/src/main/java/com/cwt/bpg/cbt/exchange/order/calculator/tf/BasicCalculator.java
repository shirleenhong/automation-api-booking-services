package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component("tfBasicCalculator")
public class BasicCalculator extends FeeCalculator {
	
	@Override
	public BigDecimal getTotalFee(IndiaAirFeesInput input, 
			IndiaAirFeesBreakdown breakdown, 
			BigDecimal yqTax) {
		return input.getBaseFare();
	}
}

package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.safeValue;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component("tfFullFareCalculator")
public class FullFareCalculator extends FeeCalculator {
	
	@Override
	public BigDecimal getTotalFee(IndiaAirFeesInput input, 
			IndiaAirFeesBreakdown breakdown,
			BigDecimal yqTax) {
		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()));
	}
}

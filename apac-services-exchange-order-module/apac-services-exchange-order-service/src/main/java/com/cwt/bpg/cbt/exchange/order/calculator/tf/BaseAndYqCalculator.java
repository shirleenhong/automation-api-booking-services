package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfBaseAndYqCalculator")
public class BaseAndYqCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(InAirFeesInput input, InAirFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).add(safeValue(input.getYqTax()));
	}
}

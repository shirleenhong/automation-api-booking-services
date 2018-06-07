package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component("tfGrossFareCalculator")
public class GrossFareCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).add(safeValue(breakdown.getTotalTaxes()))
				.add(safeValue(breakdown.getTotalGst()));
	}
}

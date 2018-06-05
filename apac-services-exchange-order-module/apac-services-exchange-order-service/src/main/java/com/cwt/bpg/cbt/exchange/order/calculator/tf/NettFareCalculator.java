package com.cwt.bpg.cbt.exchange.order.calculator.tf;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfNettFareCalculator")
public class NettFareCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalFee(InAirFeesInput input, InAirFeesBreakdown breakdown) {
		return safeValue(input.getBaseFare()).subtract(safeValue(breakdown.getTotalIataCommission()))
				.subtract(safeValue(breakdown.getTotalOverheadCommission()));
	}
}

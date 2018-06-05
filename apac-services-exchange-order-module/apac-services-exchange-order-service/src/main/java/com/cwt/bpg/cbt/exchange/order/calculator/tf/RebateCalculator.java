package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfRebateCalculator")
public class RebateCalculator extends FeeCalculator {

	@Override
    public BigDecimal getMfOnTf(InAirFeesInput input,
    		InAirFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

    @Override
    public BigDecimal getTotalCharge(
			InAirFeesBreakdown breakdown) {
    	
        return safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));
    }
}

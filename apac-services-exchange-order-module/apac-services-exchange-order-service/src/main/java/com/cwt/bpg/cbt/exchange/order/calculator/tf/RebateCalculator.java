package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

@Component("tfRebateCalculator")
public class RebateCalculator extends FeeCalculator {

	@Override
    public BigDecimal getMfOnTf(InAirFeesInput input,
    		TransactionFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

    @Override
    public BigDecimal getTotalCharge(
			TransactionFeesBreakdown breakdown) {
    	
        return safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));
    }
}

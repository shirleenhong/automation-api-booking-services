package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;


@Component("noFeeCalculator")
public class NoFeeCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalDiscount(
			InAirFeesInput input,
			TransactionFeesBreakdown breakdown) {
        return null;
    }

	@Override
    public BigDecimal getTotalOverheadCommission( 
			InAirFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getTotalOrCom2( 
			InAirFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getMerchantFee(
    		InAirFeesInput input,
			TransactionFeesBreakdown breakdown
			) {
        return calculatePercentage(
        		safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getTotalTaxes()))
        		.add(safeValue(breakdown.getTotalGst())),
        			input.getMerchantFeePercent());
    }

	@Override
    public BigDecimal getMfOnTf( 
			InAirFeesInput input,
			TransactionFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

	@Override
    public BigDecimal getTotalFee(InAirFeesInput input, TransactionFeesBreakdown breakdown) {
        return null;
    }

	@Override
    public BigDecimal getTotalSellingFare(TransactionFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalGst()))
    			.add(safeValue(breakdown.getTotalMerchantFee()));
    }

	@Override
    public BigDecimal getTotalCharge(
			TransactionFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalTaxes()));
    }
}

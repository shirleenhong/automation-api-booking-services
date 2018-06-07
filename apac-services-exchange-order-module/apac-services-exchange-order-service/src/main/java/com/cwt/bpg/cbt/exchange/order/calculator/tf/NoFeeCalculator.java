package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;


@Component("noFeeCalculator")
public class NoFeeCalculator extends FeeCalculator {

	@Override
	public BigDecimal getTotalDiscount(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown) {
        return null;
    }

	@Override
    public BigDecimal getTotalOverheadCommission( 
			IndiaAirFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getTotalOrCom2( 
			IndiaAirFeesInput input) {
        return null;
    }

	@Override
    public BigDecimal getMerchantFee(
    		IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown
			) {
        return calculatePercentage(
        		safeValue(breakdown.getTotalSellFare())
        		.add(safeValue(breakdown.getTotalTaxes()))
        		.add(safeValue(breakdown.getTotalGst())),
        			input.getMerchantFeePercent());
    }

	@Override
    public BigDecimal getMfOnTf( 
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {
        return null;
    }

	@Override
    public Boolean getDdlFeeApply() {
        return null;
    }

	@Override
    public BigDecimal getTotalFee(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown) {
        return null;
    }

	@Override
    public BigDecimal getTotalSellingFare(IndiaAirFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalGst()))
    			.add(safeValue(breakdown.getTotalMerchantFee()));
    }

	@Override
    public BigDecimal getTotalCharge(
			IndiaAirFeesBreakdown breakdown) {

        return safeValue(breakdown.getTotalSellFare())
    			.add(safeValue(breakdown.getTotalTaxes()));
    }
}

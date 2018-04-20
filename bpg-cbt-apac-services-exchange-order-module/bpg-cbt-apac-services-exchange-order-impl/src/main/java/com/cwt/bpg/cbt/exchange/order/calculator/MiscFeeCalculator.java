package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculator extends CommonCalculator {
	
	public FeesBreakdown calMiscFee(OtherServiceFeesInput input, 
			Double merchantFeePct,
			BigDecimal nettCost) {		

		FeesBreakdown result = new FeesBreakdown();
		
		if(input == null) {
			return result;
		}
		
		BigDecimal gstAmount = BigDecimal.ZERO;
		BigDecimal nettCostGst =  BigDecimal.ZERO;
		BigDecimal merchantFeeAmount =  BigDecimal.ZERO;
		BigDecimal sellingPriceInDi =  BigDecimal.ZERO;
		BigDecimal commission =  BigDecimal.ZERO;
		
		if(!input.isGstAbsorb()) {
			
			gstAmount = getValue(input.getSellingPrice(), input.getGstPercent());			
			nettCostGst = getValue(nettCost, input.getGstPercent());
		}
		
		if(!input.isMerchantFeeAbsorb() 
			&& FOPTypes.CX.getCode().equals(input.getFopType())
			&& !input.isMerchantFeeWaive()) 
		{			
			merchantFeeAmount = roundAmount(
						getValue(input.getSellingPrice().multiply(getValue(1D).add(getPercentage(input.getGstPercent()))), 
									  merchantFeePct), 
								input.getCountryCode());
		}		
		
		sellingPriceInDi = input.getSellingPrice().add(gstAmount).add(safeValue(merchantFeeAmount))
								.divide(getValue(1D).add(getPercentage(input.getGstPercent())), getMc());

		if(sellingPriceInDi.compareTo(safeValue(nettCost)) > 0) {
			commission = sellingPriceInDi.subtract(safeValue(nettCost));
		}
		
		result.setNettCostGst(roundUp(nettCostGst));
		result.setCommission(roundUp(commission));
		result.setGstAmount(roundUp(gstAmount));
		result.setMerchantFee(roundUp(merchantFeeAmount));
		result.setSellingPriceInDi(roundUp(sellingPriceInDi));
		
		return result;
	}

}

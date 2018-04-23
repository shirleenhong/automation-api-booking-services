package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculator extends CommonCalculator {
	
	public FeesBreakdown calMiscFee(OtherServiceFeesInput input, 
			Double merchantFeePct) {		

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
			
			gstAmount = round(applyPercentage(input.getSellingPrice(), input.getGstPercent()), input.getCountryCode());			
			nettCostGst = round(applyPercentage(input.getNettCost(), input.getGstPercent()), input.getCountryCode());
		}
		
		if(!input.isMerchantFeeAbsorb() 
			&& FOPTypes.CX.getCode().equals(input.getFopType())
			&& !input.isMerchantFeeWaive()) 
		{			
			merchantFeeAmount = round(
						applyPercentage(input.getSellingPrice().multiply(getValue(1D).add(getPercentage(input.getGstPercent()))), 
									  merchantFeePct), 
								input.getCountryCode());
		}		
		
		sellingPriceInDi = input.getSellingPrice().add(safeValue(gstAmount)).add(safeValue(merchantFeeAmount))
								.divide(getValue(1D).add(getPercentage(input.getGstPercent())), 2, RoundingMode.HALF_UP);

		if(sellingPriceInDi.compareTo(safeValue(input.getNettCost())) > 0) {
			commission = round(sellingPriceInDi.subtract(safeValue(input.getNettCost())), input.getCountryCode());
		}
		
		result.setNettCostGst(round(nettCostGst, input.getCountryCode()));
		result.setCommission(round(commission, input.getCountryCode()));
		result.setGstAmount(round(gstAmount, input.getCountryCode()));
		result.setMerchantFee(round(merchantFeeAmount, input.getCountryCode()));
		result.setSellingPriceInDi(round(sellingPriceInDi, input.getCountryCode()));
		
		return result;
	}

}

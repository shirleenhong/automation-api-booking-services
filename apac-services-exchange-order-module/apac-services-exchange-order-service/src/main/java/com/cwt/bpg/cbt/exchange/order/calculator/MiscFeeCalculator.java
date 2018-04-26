package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculator extends CommonCalculator implements Calculator {
		
	@Autowired
	private ScaleConfig scaleConfig;
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, 
			Double merchantFeePct) {		
		
		MiscFeesInput input = (MiscFeesInput) genericInput;
		FeesBreakdown result = new FeesBreakdown();
			
		if(input == null) {
			return result;
		}
		
		BigDecimal gstAmount = BigDecimal.ZERO;
		BigDecimal nettCostGst =  BigDecimal.ZERO;
		BigDecimal merchantFeeAmount =  BigDecimal.ZERO;
		BigDecimal sellingPriceInDi =  BigDecimal.ZERO;
		BigDecimal commission =  BigDecimal.ZERO;
		
		int scale = scaleConfig.getScale(input.getCountryCode());
		
		if(!input.isGstAbsorb()) {
			
			gstAmount = round(applyPercentage(input.getSellingPrice(), input.getGstPercent()), scale);			
			nettCostGst = round(applyPercentage(input.getNettCost(), input.getGstPercent()), scale);
		}
		
		if(!input.isMerchantFeeAbsorb() 
			&& FOPTypes.CWT.getCode().equals(input.getFopType())
			&& !input.isMerchantFeeWaive()) 
		{			
			merchantFeeAmount = round(
						applyPercentage(input.getSellingPrice().multiply(getValue(1D).add(getPercentage(input.getGstPercent()))), 
									  merchantFeePct), scale);
		}		
		
		sellingPriceInDi = input.getSellingPrice().add(safeValue(gstAmount)).add(safeValue(merchantFeeAmount))
								.divide(getValue(1D).add(getPercentage(input.getGstPercent())), 2, RoundingMode.HALF_UP);

		if(sellingPriceInDi.compareTo(safeValue(input.getNettCost())) > 0) {
			commission = round(sellingPriceInDi.subtract(safeValue(input.getNettCost())), scale);
		}
		
		result.setNettCostGst(round(nettCostGst, scale));
		result.setCommission(round(commission, scale));
		result.setGstAmount(round(gstAmount, scale));
		result.setMerchantFee(round(merchantFeeAmount, scale));
		result.setSellingPriceInDi(round(sellingPriceInDi, scale));
		
		return result;
	}
}

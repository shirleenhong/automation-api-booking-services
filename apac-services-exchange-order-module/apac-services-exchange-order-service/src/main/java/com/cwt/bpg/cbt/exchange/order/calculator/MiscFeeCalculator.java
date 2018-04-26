package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculator extends CommonCalculator implements Calculator {
		
	@Autowired
	private ScaleConfig scaleConfig;
	
	@Override	
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFee) {
		MiscFeesInput input = (MiscFeesInput) genericInput;
		FeesBreakdown result = new FeesBreakdown();

		if (input == null) {
			return result;
		}

		BigDecimal gstAmount = null;
		BigDecimal nettCostGst = null;
		
		int scale = scaleConfig.getScale(input.getCountryCode());
		
		if (!input.isGstAbsorb()) {
			gstAmount = round(
					calculatePercentage(input.getSellingPrice(), input.getGstPercent()), scale);
			nettCostGst = round(
					calculatePercentage(input.getNettCost(), input.getGstPercent()),
					scale);
		}

		BigDecimal merchantFeeAmount = null;
		if (!input.isMerchantFeeAbsorb()
				&& FOPTypes.CWT.getCode().equals(input.getFopType())
				&& !input.isMerchantFeeWaive()) {
			merchantFeeAmount = round(
                    calculatePercentage(
                            input.getSellingPrice()
                                    .multiply(getValue(1D)
                                            .add(percentDecimal(input.getGstPercent()))),
                            merchantFee.getMerchantFeePct()),
                    		scale);
		}

        BigDecimal sellingPriceInDi = round(input.getSellingPrice()
                        .add(safeValue(gstAmount)).add(safeValue(merchantFeeAmount))
                        .divide(getValue(1D).add(percentDecimal(input.getGstPercent())), 2,
                                RoundingMode.HALF_UP),
                        			scale);

		BigDecimal commission = round(BigDecimal.ZERO, scale);
		
		if (sellingPriceInDi.compareTo(safeValue(input.getNettCost())) > 0) {
			commission = round(sellingPriceInDi.subtract(safeValue(input.getNettCost())),
					scale);
		}

		result.setNettCostGst(nettCostGst);
		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setSellingPriceInDi(sellingPriceInDi);
		result.setCommission(commission);

		return result;
	}
}

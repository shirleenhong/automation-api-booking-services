package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class VisaFeesCalculator extends CommonCalculator {

	@Autowired
	private ScaleConfig scaleConfig;

	public VisaFeesBreakdown calculate(VisaFeesInput input, MerchantFee merchantFee) {

		VisaFeesBreakdown result = new VisaFeesBreakdown();

		int scale = scaleConfig.getScale(input.getCountryCode());

		BigDecimal mfNettCost = BigDecimal.ZERO;
		if (input.isNettCostMerchantFeeChecked()) {
			mfNettCost = round(calculatePercentage(input.getNettCost(),
					merchantFee.getMerchantFeePct()), scale);
			result.setNettCostMerchantFee(mfNettCost);
		}

		BigDecimal mfCwtHandling = BigDecimal.ZERO;
		if (input.isCwtHandlingMerchantFeeChecked()) {
			mfCwtHandling = round(calculatePercentage(
					input.getCwtHandling().add(input.getVendorHandling()),
					merchantFee.getMerchantFeePct()), scale);
			result.setCwtHandlingMerchantFee(mfCwtHandling);
		}

		BigDecimal sellingPrice = input.getNettCost().add(input.getVendorHandling())
				.add(input.getCwtHandling()).add(mfNettCost).add(mfCwtHandling);

		BigDecimal commission = input.getCwtHandling().add(mfNettCost).add(mfCwtHandling);

		result.setCommission(commission);
		result.setSellingPrice(sellingPrice);
		result.setSellingPriceInDi(sellingPrice);
		return result;
	}
}

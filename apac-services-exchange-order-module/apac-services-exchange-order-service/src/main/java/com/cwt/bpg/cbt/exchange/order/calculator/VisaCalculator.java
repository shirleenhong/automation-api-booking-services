package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class VisaCalculator extends CommonCalculator implements Calculator {

	@Autowired
	private ScaleConfig scaleConfig;

	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput otherServiceFeesInput,
			MerchantFee merchantFee) {
		VisaFeesBreakdown result = new VisaFeesBreakdown();
		VisaInput input = (VisaInput) otherServiceFeesInput;
		BigDecimal mfNettCost = BigDecimal.ZERO;
		BigDecimal mfCwtHandling = BigDecimal.ZERO;

		int scale = scaleConfig.getScale(input.getCountryCode());
		if (input.isNettCostMerchantFeeChecked()) {
			mfNettCost = round(calculatePercentage(input.getNettCost(),
					merchantFee.getMerchantFeePct()), scale);
			result.setNettCostMerchantFee(mfNettCost);
		}

		if (input.isCwtHandlingMerchantFeeChecked()) {
			mfCwtHandling = round(calculatePercentage(input.getCwtHandling(),
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

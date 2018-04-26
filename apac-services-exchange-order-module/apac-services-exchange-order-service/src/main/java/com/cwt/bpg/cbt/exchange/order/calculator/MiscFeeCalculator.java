package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculator extends CommonCalculator implements Calculator {

	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFee) {
		MiscFeesInput input = (MiscFeesInput)genericInput;
		FeesBreakdown result = new FeesBreakdown();

		if (input == null) {
			return result;
		}

		BigDecimal gstAmount = null;
		BigDecimal nettCostGst = null;
		if (!input.isGstAbsorb()) {
			gstAmount = round(
					calculatePercentage(input.getSellingPrice(), input.getGstPercent()),
					input.getCountryCode());
			nettCostGst = round(
					calculatePercentage(input.getNettCost(), input.getGstPercent()),
					input.getCountryCode());
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
                    input.getCountryCode());
		}

        BigDecimal sellingPriceInDi = round(input.getSellingPrice()
                        .add(safeValue(gstAmount)).add(safeValue(merchantFeeAmount))
                        .divide(getValue(1D).add(percentDecimal(input.getGstPercent())), 2,
                                RoundingMode.HALF_UP),
                input.getCountryCode());

		BigDecimal commission = round(BigDecimal.ZERO, input.getCountryCode());
		if (sellingPriceInDi.compareTo(safeValue(input.getNettCost())) > 0) {
			commission = round(sellingPriceInDi.subtract(safeValue(input.getNettCost())),
					input.getCountryCode());
		}

		result.setNettCostGst(nettCostGst);
		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setSellingPriceInDi(sellingPriceInDi);
		result.setCommission(commission);

		return result;
	}
}

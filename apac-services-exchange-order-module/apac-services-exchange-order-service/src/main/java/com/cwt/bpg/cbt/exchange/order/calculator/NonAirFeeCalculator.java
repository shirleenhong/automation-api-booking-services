package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.FopType;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;

@Component("nonAirFeeCalculator")
public class NonAirFeeCalculator implements Calculator<NonAirFeesBreakdown, NonAirFeesInput> {

	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private RoundingConfig roundingConfig;

	@Override
	public NonAirFeesBreakdown calculate(NonAirFeesInput input, MerchantFee merchantFee, String countryCode) {
		NonAirFeesBreakdown result = new NonAirFeesBreakdown();

		if (input == null) {
			return result;
		}

		BigDecimal gstAmount = null;
		BigDecimal nettCostGst = null;

		int scale = scaleConfig.getScale(countryCode);

		if (!input.isGstAbsorb()) {
			gstAmount = round(calculatePercentage(input.getSellingPrice(), input.getGstPercent()), scale);
			nettCostGst = round(calculatePercentage(input.getNettCost(), input.getGstPercent()), scale, roundingConfig.getRoundingMode("nettCost", countryCode));
		}

		BigDecimal merchantFeeAmount = null;
		if (!input.isMerchantFeeAbsorb() && FopType.CWT.equals(input.getFopType())
				&& !input.isMerchantFeeWaive()) {

			merchantFeeAmount = round(
					calculatePercentage(
							input.getSellingPrice()
									.multiply(BigDecimal.ONE.add(percentDecimal(input.getGstPercent()))),
							merchantFee.getMerchantFeePercent()),
					scale, roundingConfig.getRoundingMode("merchantFee", countryCode));
		}

		BigDecimal sellingPriceInDi = round(input.getSellingPrice().add(safeValue(gstAmount))
				.add(safeValue(merchantFeeAmount))
				.divide(BigDecimal.ONE.add(percentDecimal(input.getGstPercent())), 2, RoundingMode.HALF_UP),
				scale);

		if (!input.isGstAbsorb()) {
			gstAmount = round(calculatePercentage(sellingPriceInDi, input.getGstPercent()), scale);
		}

        BigDecimal commission = round(BigDecimal.ZERO, scale);
        if (sellingPriceInDi.compareTo(safeValue(input.getNettCost())) > 0) {
            commission = round(sellingPriceInDi.subtract(safeValue(input.getNettCost())), scale, roundingConfig.getRoundingMode("commission", countryCode));
        }

        sellingPriceInDi = round(sellingPriceInDi.add(safeValue(gstAmount)), scale, roundingConfig.getRoundingMode("totalSellingFare", countryCode));

        result.setNettCostGst(nettCostGst);
		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setTotalSellingPrice(sellingPriceInDi);
		result.setCommission(commission);

		return result;
	}
}

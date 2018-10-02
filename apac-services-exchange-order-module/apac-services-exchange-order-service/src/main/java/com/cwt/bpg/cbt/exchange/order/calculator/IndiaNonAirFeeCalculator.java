package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesInput;

@Component
public class IndiaNonAirFeeCalculator {

	@Autowired
	private ScaleConfig scaleConfig;

	public IndiaNonAirFeesBreakdown calculate(IndiaNonAirFeesInput input,
                                              Client client,
                                              Double mfPercent) {

		IndiaNonAirFeesBreakdown result = new IndiaNonAirFeesBreakdown();

		if (input == null || client == null) {
			return result;
		}

		BigDecimal commission = safeValue(input.getCommission());
		BigDecimal discount = safeValue(input.getDiscount());

		int scale = scaleConfig.getScale(Country.INDIA.getCode());

		if (input.isCommissionByPercent()) {
			commission = round(calculatePercentage(input.getCostAmount(), input.getCommissionPercent()),
					scale);
		}

		if (input.isDiscountByPercent()) {
			discount = round(
					calculatePercentage(safeValue(input.getCostAmount()).add(safeValue(commission)),
							input.getDiscountPercent()),
					scale);
		}

		BigDecimal grossSell = round(safeValue(input.getCostAmount()).add(safeValue(commission))
				.subtract(safeValue(discount)), scale);

		BigDecimal tax = round(
				calculatePercentage(grossSell, safeValue(input.getProduct().getGstPercent())),
				scale);

		BigDecimal gstAmount = round(safeValue(tax)
				.add(round(calculatePercentage(grossSell, safeValue(input.getProduct().getOt1Percent())), scale))
				.add(round(calculatePercentage(grossSell, safeValue(input.getProduct().getOt2Percent())), scale)), scale);

		BigDecimal merchantFeeAmount = round(calculatePercentage(safeValue(grossSell).add(gstAmount), mfPercent),
				scale);

		BigDecimal totalSellAmount = round(
				safeValue(grossSell).add(gstAmount).add(safeValue(merchantFeeAmount)),
				scale);

        if(mfPercent == 0){
            result.setNoMerchantFee(true);
        }

        if(input.getProduct().getGstPercent() == 0){
            result.setClientExempt(true);
        }

		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setTotalSellingPrice(totalSellAmount);
		result.setGrossSellingPrice(grossSell);
		result.setCommission(commission);
		result.setDiscount(discount);
		
		return result;
	}
}

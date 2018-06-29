package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesInput;

@Component
public class VisaFeesCalculator implements Calculator<VisaFeesBreakdown, VisaFeesInput> {

    @Autowired
    private ScaleConfig scaleConfig;

    @Override
    public VisaFeesBreakdown calculate(VisaFeesInput input, MerchantFee merchantFee) {

        VisaFeesBreakdown result = new VisaFeesBreakdown();

        int scale = scaleConfig.getScale(input.getCountryCode());

        BigDecimal mfNettCost = BigDecimal.ZERO;
        Double merchantFeePercent = merchantFee.getMerchantFeePercent();
        BigDecimal nettCost = safeValue(input.getNettCost());

        if (input.isNettCostMerchantFeeChecked()) {
            mfNettCost = round(calculatePercentage(nettCost, merchantFeePercent), scale);
            result.setNettCostMerchantFee(mfNettCost);
        }

        BigDecimal mfCwtHandling = BigDecimal.ZERO;
        if (input.isCwtHandlingMerchantFeeChecked()) {
            mfCwtHandling = round(calculatePercentage(
                    input.getCwtHandling().add(input.getVendorHandling()), merchantFeePercent), scale);
            result.setCwtHandlingMerchantFee(mfCwtHandling);
        }

        BigDecimal sellingPrice = nettCost.add(input.getVendorHandling())
                .add(input.getCwtHandling()).add(mfNettCost).add(mfCwtHandling);

        BigDecimal commission = input.getCwtHandling().add(mfNettCost).add(mfCwtHandling);

        result.setCommission(commission);
        result.setSellingPrice(sellingPrice);
        result.setSellingPriceInDi(sellingPrice);
        return result;
    }
}

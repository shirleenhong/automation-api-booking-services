package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class VisaFeesCalculator extends CommonCalculator implements Calculator {

    @Autowired
    private ScaleConfig scaleConfig;

    @Override
    public FeesBreakdown calculate(FeesInput feesInput, MerchantFee merchantFee) {

        VisaFeesBreakdown result = new VisaFeesBreakdown();
        VisaFeesInput input = (VisaFeesInput) feesInput;

        int scale = scaleConfig.getScale(input.getCountryCode());

        BigDecimal mfNettCost = BigDecimal.ZERO;
        Double merchantFeePct = merchantFee != null ? merchantFee.getMerchantFeePct() : 0d;
        BigDecimal nettCost = safeValue(input.getNettCost());

        if (input.isNettCostMerchantFeeChecked()) {
            mfNettCost = round(calculatePercentage(nettCost,
                    merchantFeePct), scale);
            result.setNettCostMerchantFee(mfNettCost);
        }

        BigDecimal mfCwtHandling = BigDecimal.ZERO;
        if (input.isCwtHandlingMerchantFeeChecked()) {
            mfCwtHandling = round(calculatePercentage(
                    input.getCwtHandling().add(input.getVendorHandling()),
                    merchantFeePct), scale);
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

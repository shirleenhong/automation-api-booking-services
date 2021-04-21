package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;
import static com.cwt.bpg.cbt.exchange.order.calculator.MerchantFeeCalculatorUtils.getMerchantFeeForVendorCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;

@Component
public class ThNonAirFeeCalculator {

    @Autowired
    private ScaleConfig scaleConfig;

    @Autowired
    private RoundingConfig roundingConfig;

    public NonAirFeesBreakdown calculate(NonAirFeesInput input, MerchantFee merchantFee, String countryCode) {
        NonAirFeesBreakdown result = new NonAirFeesBreakdown();

        if (input == null) {
            return result;
        }

        int scale = scaleConfig.getScale(countryCode);

        BigDecimal nettCost = round(safeValue(input.getNettCost()),scale);
        BigDecimal sellingPrice = round(safeValue(input.getSellingPrice()), scale);
        BigDecimal tax = round(safeValue(input.getTax()), scale);
        BigDecimal commission = round(safeValue(input.getCommission()), scale);

        BigDecimal totalSellingPrice;
        BigDecimal nettPrice = sellingPrice.add(tax);
        BigDecimal gstAmount = calculatePercentage(nettPrice, input.getGstPercent());
        BigDecimal nettPriceWithGST = nettPrice.add(gstAmount);

        BigDecimal merchantFeeAmount = applyMerchantFee(merchantFee, input, scale,
                getRoundingMode("merchantFee", countryCode), nettPriceWithGST);

        totalSellingPrice = round(nettPriceWithGST.add(safeValue(merchantFeeAmount)), scale, getRoundingMode("totalSellingPrice", countryCode));

        result.setNettCost(nettCost);
        result.setSellingPrice(sellingPrice);
        result.setTax(tax);
        result.setCommission(commission);
        result.setGstAmount(gstAmount);
        result.setMerchantFee(merchantFeeAmount);
        result.setTotalSellingPrice(totalSellingPrice);

        return result;
    }

    private BigDecimal applyMerchantFee(MerchantFee merchantFee, NonAirFeesInput input, int scale, RoundingMode roundingMode,
            BigDecimal nettFareWithGST)
    {
        BigDecimal merchantFeeAmount = BigDecimal.ZERO;

        if (merchantFee != null) {
            Double merchantFeePercent = getMerchantFeeForVendorCode(merchantFee, input.getVendorCode());
            merchantFeeAmount = round(calculatePercentage(nettFareWithGST, merchantFeePercent), scale, roundingMode);
        }

        return roundUp(merchantFeeAmount);
    }

    private BigDecimal roundUp(BigDecimal merchantFeeAmount)
    {
        final BigDecimal five = new BigDecimal("5");

        return merchantFeeAmount.divide(five)
                .setScale(0, RoundingMode.CEILING)
                .multiply(five);
    }

    private RoundingMode getRoundingMode(String field, String countryCode) {
        return roundingConfig.getRoundingMode(field, countryCode);
    }
}

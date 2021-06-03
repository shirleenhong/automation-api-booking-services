package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;
import static com.cwt.bpg.cbt.exchange.order.calculator.MerchantFeeCalculatorUtils.getMerchantFeeForVendorCode;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class ThAirCalculator implements Calculator<AirFeesBreakdown, AirFeesInput>
{
    @Autowired
    private ScaleConfig scaleConfig;

    @Autowired
    private RoundingConfig roundingConfig;

    @Override
    public AirFeesBreakdown calculate(AirFeesInput input, MerchantFee merchantFee, String countryCode)
    {
        AirFeesBreakdown result = new AirFeesBreakdown();

        if (input == null)
        {
            return result;
        }

        int scale = scaleConfig.getScale(Country.THAILAND.getCode());

        BigDecimal totalSellingFare;
        BigDecimal nettCost;
        BigDecimal merchantFeeAmount;
        BigDecimal nettFare = safeValue(input.getNettFare());
        final BigDecimal sellingPrice = round(safeValue((input.getSellingPrice())), scale);
        final BigDecimal commission = safeValue(input.getCommission());
        final BigDecimal discount = safeValue(input.getDiscount());
        final BigDecimal tax1 = safeValue(input.getTax1());
        final BigDecimal tax2 = safeValue(input.getTax2());
        final Double gst = safeValue(input.getGst());
        final BigDecimal transactionFee = safeValue(input.getTransactionFee());
        final BigDecimal vat = round(calculatePercentage(transactionFee, gst), scale);

        result.setCommission(commission);
        result.setDiscount(discount);
        result.setSellingPrice(sellingPrice);
        result.setVat(vat);

        nettCost = nettFare;
        nettFare = round(nettFare.add(tax1).add(tax2).add(commission).subtract(discount), scale, getRoundingMode("nettFare", countryCode));
        result.setNettFare(nettFare);

        merchantFeeAmount = applyMerchantFee(merchantFee, input, scale, getRoundingMode("merchantFee", countryCode), nettFare);
        result.setMerchantFee(merchantFeeAmount);

        totalSellingFare = nettFare.add(safeValue(merchantFeeAmount));

        result.setTotalSellingFare(round(totalSellingFare, scale, getRoundingMode("totalSellingFare", countryCode)));
        result.setNettCost(round(nettCost, scale, getRoundingMode("nettCost", countryCode)));

        return result;
    }

    private BigDecimal applyMerchantFee(MerchantFee merchantFee, AirFeesInput input, int scale, RoundingMode roundingMode,
                                        BigDecimal nettFare)
    {
        BigDecimal merchantFeeAmount = BigDecimal.ZERO;

        if (merchantFee != null)
        {
            Double merchantFeePercent = getMerchantFeeForVendorCode(merchantFee, input.getVendorCode());
            merchantFeeAmount = round(calculatePercentage(nettFare, merchantFeePercent), scale, roundingMode);
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

    private RoundingMode getRoundingMode(String field, String countryCode)
    {
        return roundingConfig.getRoundingMode(field, countryCode);
    }

}

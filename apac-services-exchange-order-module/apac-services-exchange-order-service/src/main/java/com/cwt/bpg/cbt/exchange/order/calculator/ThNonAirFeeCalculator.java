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
public class ThNonAirFeeCalculator implements Calculator<NonAirFeesBreakdown, NonAirFeesInput>
{

    @Autowired
    private ScaleConfig scaleConfig;

    @Autowired
    private RoundingConfig roundingConfig;

    private int scale;

    @Override
    public NonAirFeesBreakdown calculate(NonAirFeesInput input, MerchantFee merchantFee, String countryCode)
    {
        NonAirFeesBreakdown result = new NonAirFeesBreakdown();

        if (input == null)
        {
            return result;
        }

        scale = scaleConfig.getScale(countryCode);

        final BigDecimal nettCost = round(safeValue(input.getNettCost()), scale);
        final BigDecimal sellingPrice = round(safeValue(input.getSellingPrice()), scale);
        final BigDecimal tax = round(safeValue(input.getTax()), scale);
        final BigDecimal commission = round(safeValue(input.getCommission()), scale);

        final BigDecimal nettPrice = sellingPrice.add(tax);
        final BigDecimal gstAmount = calculatePercentage(nettPrice, input.getGstPercent()).setScale(2, RoundingMode.HALF_UP);
        final BigDecimal nettPriceWithGST = nettPrice.add(gstAmount);

        final BigDecimal merchantFeeAmount = applyMerchantFee(merchantFee, input, nettPrice);

        final BigDecimal totalSellingPrice = (nettPriceWithGST.add(safeValue(merchantFeeAmount)).setScale(2, RoundingMode.HALF_UP));

        result.setNettCost(nettCost);
        result.setSellingPrice(sellingPrice);
        result.setTax(tax);
        result.setCommission(commission);
        result.setGstAmount(gstAmount);
        result.setMerchantFee(merchantFeeAmount);
        result.setTotalSellingPrice(totalSellingPrice);

        return result;
    }

    private BigDecimal applyMerchantFee(MerchantFee merchantFee, NonAirFeesInput input, BigDecimal nettPrice)
    {
        BigDecimal merchantFeeAmount = BigDecimal.ZERO;

        if (merchantFee != null)
        {
            final Double merchantFeePercent = getMerchantFeeForVendorCode(merchantFee, input.getVendorCode());
            merchantFeeAmount = calculatePercentage(nettPrice, merchantFeePercent);
        }

        return roundUpNearestFive(merchantFeeAmount);
    }

}

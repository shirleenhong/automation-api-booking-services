package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class HkAirCalculator implements Calculator<AirFeesBreakdown, AirFeesInput>
{

    @Autowired
    private ScaleConfig scaleConfig;

    @Autowired
    private RoundingConfig roundingConfig;

    private final List<String> clientsWithAdditionalSellPrice = Arrays.asList(ClientType.MG.getCode(),
            ClientType.DB.getCode(),
            ClientType.TF.getCode(),
            ClientType.MN.getCode());

    private final List<String> clientsWithPercentageDiscount = Arrays.asList(ClientType.DU.getCode(),
            ClientType.DB.getCode());

    private final List<String> clientsWithCommissionDiscount = Arrays
            .asList(ClientType.MN.getCode(), ClientType.TF.getCode(), ClientType.TP.getCode());

    private final List<String> clientsWithNoDiscount = Arrays.asList(ClientType.MN.getCode(),
            ClientType.TF.getCode());

    @Override
    public AirFeesBreakdown calculate(AirFeesInput input, MerchantFee merchantFee, String countryCode)
    {

        AirFeesBreakdown result = new AirFeesBreakdown();

        if (input == null)
        {
            return result;
        }

        int scale = scaleConfig.getScale(Country.HONG_KONG.getCode());

        BigDecimal totalSellingFare;
        BigDecimal nettCost;
        BigDecimal sellingPrice;
        BigDecimal merchantFeeAmount = safeValue(input.getMerchantFee());
        BigDecimal commission = safeValue(input.getCommission());
        BigDecimal discount = safeValue(input.getDiscount());
        BigDecimal nettFare = safeValue(input.getNettFare());
        BigDecimal tax1 = safeValue(input.getTax1());
        BigDecimal tax2 = safeValue(input.getTax2());

        if (!input.isApplyFormula())
        {
            totalSellingFare = nettFare.add(commission)
                    .subtract(discount)
                    .add(tax1)
                    .add(tax2)
                    .add(merchantFeeAmount);
            nettCost = nettFare;
        }
        else
        {
            if (input.isCommissionByPercent())
            {
                if (!ClientType.TP.getCode().equals(input.getClientType()))
                {
                    commission = getCommission(input, scale, getRoundingMode("commission", countryCode), nettFare);
                }

                sellingPrice = nettFare.divide(
                        BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPercent())),
                        MathContext.DECIMAL128);

                if (!clientsWithAdditionalSellPrice.contains(input.getClientType()))
                {
                    sellingPrice = sellingPrice.add(BigDecimal.TEN);
                }
            }
            else
            {
                commission = round(commission, scale, getRoundingMode("commission", countryCode));
                sellingPrice = nettFare.add(commission);
            }

            sellingPrice = round(sellingPrice, scale);
            result.setCommission(commission);
            result.setSellingPrice(sellingPrice);

            discount = round(applyDiscount(input, commission, discount, nettFare), scale);
            result.setDiscount(discount);

            nettCost = nettFare;
            nettFare = round(sellingPrice.add(tax1).add(tax2).subtract(discount), scale, getRoundingMode("nettFare", countryCode));
            result.setNettFare(nettFare);

            merchantFeeAmount = applyMerchantFee(merchantFee, input, scale, getRoundingMode("merchantFee", countryCode), nettFare, tax1, tax2);
            result.setMerchantFee(merchantFeeAmount);

            totalSellingFare = nettFare.add(safeValue(merchantFeeAmount));
        }

        result.setTotalSellingFare(round(totalSellingFare, scale, getRoundingMode("totalSellingFare", countryCode)));
        result.setNettCost(round(nettCost, scale, getRoundingMode("nettCost", countryCode)));

        return result;
    }

    private BigDecimal getCommission(AirFeesInput input, int scale, RoundingMode roundingMode, BigDecimal nettFare)
    {

        BigDecimal commission = nettFare
                .divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPercent())),
                        MathContext.DECIMAL128)
                .subtract(nettFare);

        if (commission.compareTo(BigDecimal.ZERO) > 0
                && ClientType.DU.getCode().equals(input.getClientType()))
        {
            commission = commission.add(BigDecimal.TEN);
        }
        return round(commission, scale, roundingMode);
    }

    private BigDecimal applyMerchantFee(MerchantFee merchantFee, AirFeesInput input, int scale, RoundingMode roundingMode,
            BigDecimal nettFare, BigDecimal tax1, BigDecimal tax2)
    {

        BigDecimal merchantFeeAmount = null;

        if (!input.isCwtAbsorb() && FopType.CWT.equals(input.getFopType())
                && !input.isMerchantFeeWaive())
        {

            BigDecimal mFTotal;
            BigDecimal transactionFee = safeValue(input.getTransactionFee());

            if (input.isUatp())
            {
                if (ClientType.TF.getCode().equals(input.getClientType()))
                {
                    mFTotal = transactionFee;
                }
                else
                {
                    mFTotal = nettFare.subtract(input.getNettFare()).subtract(tax1).subtract(tax2);
                }
            }
            else
            {
                mFTotal = nettFare;

                if (ClientType.TF.getCode().equals(input.getClientType()) && merchantFee.isIncludeTransactionFee())
                {
                    mFTotal = mFTotal.add(transactionFee);
                }
            }
            merchantFeeAmount = BigDecimal.ZERO.max(round(calculatePercentage(mFTotal, merchantFee.getMerchantFeePercent()), scale, roundingMode));
        }

        return merchantFeeAmount;
    }

    private BigDecimal applyDiscount(AirFeesInput input, BigDecimal commission, BigDecimal discount,
            BigDecimal nettFare)
    {

        BigDecimal result = discount;

        if (clientsWithNoDiscount.contains(input.getClientType()))
        {
            result = BigDecimal.ZERO;
        }
        else if (input.isDiscountByPercent())
        {
            if (clientsWithPercentageDiscount.contains(input.getClientType()))
            {
                result = calculatePercentage(commission.add(nettFare), input.getDiscountPercent());
            }
            else if (clientsWithCommissionDiscount.contains(input.getClientType()))
            {
                result = commission;
            }
        }

        return result;
    }

    private RoundingMode getRoundingMode(String field, String countryCode)
    {
        return roundingConfig.getRoundingMode(field, countryCode);
    }
}

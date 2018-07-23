package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CalculatorUtils;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

@Component
public class ExchangeOrderAmountScaler
{

    @Autowired
    private ScaleConfig scaleConfig;

    void scale(ExchangeOrder exchangeOrder) {

        int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());

        exchangeOrder.setCommission(CalculatorUtils.scale(exchangeOrder.getCommission(), scale));
        exchangeOrder.setMerchantFee(CalculatorUtils.scale(exchangeOrder.getMerchantFee(), scale));
        exchangeOrder.setNettCost(CalculatorUtils.scale(exchangeOrder.getNettCost(), scale));
        exchangeOrder.setGstAmount(CalculatorUtils.scale(exchangeOrder.getGstAmount(), scale));
        exchangeOrder.setTax1(CalculatorUtils.scale(exchangeOrder.getTax1(), scale));
        exchangeOrder.setTax2(CalculatorUtils.scale(exchangeOrder.getTax2(), scale));
        exchangeOrder.setTotal(CalculatorUtils.scale(exchangeOrder.getTotal(), scale));
        exchangeOrder.setSellingPrice(CalculatorUtils.scale(exchangeOrder.getSellingPrice(), scale));
        exchangeOrder.setTotalSellingPrice(CalculatorUtils.scale(exchangeOrder.getTotalSellingPrice(), scale));
    }
}

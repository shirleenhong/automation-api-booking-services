package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CalculatorUtils;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

@Component
public class ExchangeOrderAmountScaler
{

    @Autowired
    private ScaleConfig scaleConfig;

    void scale(BaseExchangeOrder exchangeOrder) {

        int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());

        exchangeOrder.getServiceInfo().setCommission(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getCommission(), scale));
        exchangeOrder.getServiceInfo().setMerchantFee(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getMerchantFee(), scale));
        exchangeOrder.getServiceInfo().setNettCost(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getNettCost(), scale));
        exchangeOrder.getServiceInfo().setGst(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getGst(), scale));
        exchangeOrder.getServiceInfo().setTax1(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTax1(), scale));
        exchangeOrder.getServiceInfo().setTax2(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTax2(), scale));
        exchangeOrder.setTotal(CalculatorUtils.scale(exchangeOrder.getTotal(), scale));
        exchangeOrder.getServiceInfo().setSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getSellingPrice(), scale));
        exchangeOrder.getServiceInfo().setTotalSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTotalSellingPrice(), scale));
    }
}

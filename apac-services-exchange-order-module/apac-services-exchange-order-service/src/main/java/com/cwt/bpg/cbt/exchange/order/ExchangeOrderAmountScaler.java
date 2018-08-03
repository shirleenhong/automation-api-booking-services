package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CalculatorUtils;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;

@Component
public class ExchangeOrderAmountScaler
{

    @Autowired
    private ScaleConfig scaleConfig;

    public void scale(BaseExchangeOrder exchangeOrder) {
        if (Country.INDIA.getCode().equalsIgnoreCase(exchangeOrder.getCountryCode())) {
            scale((IndiaExchangeOrder) exchangeOrder);
        }
        else {
            scale((ExchangeOrder) exchangeOrder);
        }
    }

    private void scale(ExchangeOrder exchangeOrder) {

        int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());

        if(exchangeOrder.getServiceInfo()!=null) {

            exchangeOrder.getServiceInfo().setCommission(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getCommission(), scale));
            exchangeOrder.getServiceInfo().setMerchantFeeAmount(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getMerchantFeeAmount(), scale));
            exchangeOrder.getServiceInfo().setNettCost(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getNettCost(), scale));
            exchangeOrder.getServiceInfo().setGstAmount(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getGstAmount(), scale));
            exchangeOrder.getServiceInfo().setTax1(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTax1(), scale));
            exchangeOrder.getServiceInfo().setTax2(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTax2(), scale));
            exchangeOrder.setTotal(CalculatorUtils.scale(exchangeOrder.getTotal(), scale));
            exchangeOrder.getServiceInfo().setSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getSellingPrice(), scale));
            exchangeOrder.getServiceInfo().setTotalSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTotalSellingPrice(), scale));
        }
    }

    private void scale(IndiaExchangeOrder exchangeOrder) {

        int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());

        if(exchangeOrder.getServiceInfo()!=null){

            exchangeOrder.getServiceInfo().setCommission(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getCommission(), scale));
            exchangeOrder.getServiceInfo().setMerchantFeeAmount(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getMerchantFeeAmount(), scale));
            exchangeOrder.getServiceInfo().setNettCost(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getNettCost(), scale));
            exchangeOrder.getServiceInfo().setGstAmount(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getGstAmount(), scale));
            exchangeOrder.setTotal(CalculatorUtils.scale(exchangeOrder.getTotal(), scale));
            exchangeOrder.getServiceInfo().setSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getSellingPrice(), scale));
            exchangeOrder.getServiceInfo().setTotalSellingPrice(CalculatorUtils.scale(exchangeOrder.getServiceInfo().getTotalSellingPrice(), scale));
        }


    }
}

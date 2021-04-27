package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OtherServiceNonAirCalculatorFactory {

    @Autowired
    @Qualifier("thNonAirFeeCalculator")
    private Calculator<NonAirFeesBreakdown, NonAirFeesInput> thNonAirFeeCalculator;

    @Autowired
    @Qualifier("defaultNonAirFeeCalculator")
    private Calculator<NonAirFeesBreakdown, NonAirFeesInput> defaultNonAirFeeCalculator;

    private Map<String, Calculator<NonAirFeesBreakdown, NonAirFeesInput>> calculatorMap = new HashMap<>();

    @PostConstruct
    public void init(){
        calculatorMap.put(Country.THAILAND.getCode(), thNonAirFeeCalculator);
        calculatorMap.put(Country.SINGAPORE.getCode(), defaultNonAirFeeCalculator);
        calculatorMap.put(Country.HONG_KONG.getCode(), defaultNonAirFeeCalculator);
    }

    public Calculator<NonAirFeesBreakdown, NonAirFeesInput> getCalculator(String countryCode){

        if (calculatorMap.containsKey(countryCode)){
            return calculatorMap.get(countryCode);
        }
        else{
            throw new IllegalArgumentException("Country not Supported");
        }
    }
}

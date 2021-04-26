package com.cwt.bpg.cbt.exchange.order.calculator.factory;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OtherServiceNonAirCalculatorFactory {


    @Autowired
    @Qualifier("thNonAirCalculator")
    private Calculator<NonAirFeesBreakdown, NonAirFeesInput> thNonAirCalculator;

    @Autowired
    @Qualifier("NonAirCalculator")
    private Calculator<NonAirFeesBreakdown, NonAirFeesInput> NonAirCalculator;

    private Map<String, Calculator<NonAirFeesBreakdown, NonAirFeesInput>> calculatorMap = new HashMap<>();

    @PostConstruct
    public void Init(){
        calculatorMap.put(Country.THAILAND.getCode(), thNonAirCalculator);
        calculatorMap.put(Country.SINGAPORE.getCode(), NonAirCalculator);
        calculatorMap.put(Country.HONG_KONG.getCode(), NonAirCalculator);
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

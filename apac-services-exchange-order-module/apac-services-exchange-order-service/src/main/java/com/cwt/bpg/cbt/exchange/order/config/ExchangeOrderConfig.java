package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.exchange.order.calculator.HkBspAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;

@Configuration("com.cwt.bpg.cbt.exchange.order.config")
public class ExchangeOrderConfig {

	@Bean(name="miscFeeCalculator")
    public MiscFeeCalculator miscFeeCalculator()
    {
        return new MiscFeeCalculator();
    }
	
	@Bean(name="hkBspAirCalculator")
    public HkBspAirCalculator hkBspAirCalculator()
    {
        return new HkBspAirCalculator();
    }
	
	@Bean(name="otherServiceCalculatorFactory")
    public OtherServiceCalculatorFactory otherServiceCalculatorFactory()
    {
        return new OtherServiceCalculatorFactory();
    }
}


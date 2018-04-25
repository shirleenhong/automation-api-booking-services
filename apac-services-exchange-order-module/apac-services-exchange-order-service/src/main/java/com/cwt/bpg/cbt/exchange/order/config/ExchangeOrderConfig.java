package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.HkAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;

@Configuration("com.cwt.bpg.cbt.exchange.order.config")
public class ExchangeOrderConfig {

	@Bean
    public Calculator miscFeeCalculator()
    {
        return new MiscFeeCalculator();
    }
	
	@Bean
    public Calculator hkAirCalculator()
    {
        return new HkAirCalculator();
    }
}


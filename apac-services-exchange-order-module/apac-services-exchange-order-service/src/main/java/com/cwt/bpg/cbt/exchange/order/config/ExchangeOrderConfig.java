package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.exchange.order.calculator.BspAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;

@Configuration("com.cwt.bpg.cbt.exchange.order.config")
public class ExchangeOrderConfig {

	@Bean
    public MiscFeeCalculator miscFeeCalculator()
    {
        return new MiscFeeCalculator();
    }
	
	@Bean
    public BspAirCalculator bspAirCalculator()
    {
        return new BspAirCalculator();
    }
}


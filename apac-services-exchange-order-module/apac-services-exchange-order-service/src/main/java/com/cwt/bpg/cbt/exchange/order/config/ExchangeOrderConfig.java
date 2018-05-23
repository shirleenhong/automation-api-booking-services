package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.HkAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.InMiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.SgAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;

@Configuration("com.cwt.bpg.cbt.exchange.order.config")
public class ExchangeOrderConfig {

	@Bean(name = "miscFeeCalculator")
	public Calculator miscFeeCalculator() {
		return new MiscFeeCalculator();
	}
	
	@Bean(name = "inMiscFeeCalculator")
	public InMiscFeeCalculator inMiscFeeCalculator() {
		return new InMiscFeeCalculator();
	}

	@Bean(name = "hkAirCalculator")
	public Calculator hkAirCalculator() {
		return new HkAirCalculator();
	}

	@Bean(name = "sgAirCalculator")
	public Calculator sgAirCalculator() {
		return new SgAirCalculator();
	}

	@Bean(name = "nettCostCalculator")
	public NettCostCalculator nettCostCalculator() {
		return new NettCostCalculator();
	}

    @Bean(name = "visaFeesCalculator")
    public VisaFeesCalculator visaFeesCalculator() {
        return new VisaFeesCalculator();
    }

	@Bean(name = "otherServiceCalculatorFactory")
	public OtherServiceCalculatorFactory otherServiceCalculatorFactory() {
		return new OtherServiceCalculatorFactory();
	}
}

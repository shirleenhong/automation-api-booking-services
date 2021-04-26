package com.cwt.bpg.cbt.exchange.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceNonAirCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;

@Configuration("com.cwt.bpg.cbt.exchange.order.config")
public class ExchangeOrderConfig {

	@Bean(name = "otherServiceCalculatorFactory")
	public OtherServiceCalculatorFactory otherServiceCalculatorFactory() {
		return new OtherServiceCalculatorFactory();
	}

	@Bean(name = "otherServiceNonAirCalculatorFactory")
	public OtherServiceNonAirCalculatorFactory otherServiceNonAirCalculatorFactory() {
		return new OtherServiceNonAirCalculatorFactory();
	}
	
	@Bean(name = "transactionFeeCalculatorFactory")
	public TransactionFeeCalculatorFactory transactionFeeCalculatorFactory() {
		return new TransactionFeeCalculatorFactory();
	}
}

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
import com.cwt.bpg.cbt.exchange.order.calculator.tf.*;

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

	@Bean(name = "tfCalculator")
	public TransactionFeeCalculator tfCalculator() {
		return new TransactionFeeCalculator();
	}

	@Bean(name = "tfFullFareCalculator")
	public TransactionFeeCalculator tfFullFareCalculator() {
		return new FullFareCalculator();
	}

	@Bean(name = "tfNettFareCalculator")
	public TransactionFeeCalculator tfNettFareCalculator() {
		return new NettFareCalculator();
	}

	@Bean(name = "tfGrossFareCalculator")
	public TransactionFeeCalculator tfGrossFareCalculator() {
		return new GrossFareCalculator();
	}

	@Bean(name = "tfFareCalculator")
	public TransactionFeeCalculator tfFareCalculator() {
		return new FareCalculator();
	}

	@Bean(name = "tfBasicCalculator")
	public TransactionFeeCalculator tfBasicCalculator() {
		return new BasicCalculator();
	}

	@Bean(name = "noFeeCalculator")
	public TransactionFeeCalculator noFeeCalculator() {
		return new NoFeeCalculator();
	}

	@Bean(name = "tfPlusVatCalculator")
	public TransactionFeeCalculator tfPlusVatCalculator() {
		return new WithVatCalculator();
	}

	@Bean(name = "tfRebateCalculator")
	public TransactionFeeCalculator tfRebateCalculator() {
		return new RebateCalculator();
	}

	@Bean(name = "noFeeWithDiscountCalculator")
	public TransactionFeeCalculator noFeeWithDiscountCalculator() {
		return new NoFeeWithDiscountCalculator();
	}

	@Bean(name = "tfBaseAndYqCalculator")
	public TransactionFeeCalculator tfBaseAndYqCalculator() {
		return new BaseAndYqCalculator();
	}
}

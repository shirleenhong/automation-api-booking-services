package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.exchange.order.calculator.tf.TransactionFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.PricingNames;

public class TransactionFeeCalculatorFactory {

	@Autowired
	@Qualifier("tfCalculator")
	private TransactionFeeCalculator tfCalculator;
	
	@Autowired
	@Qualifier("tfFullFareCalculator")
	private TransactionFeeCalculator tfFullFareCalculator;
	
	@Autowired
	@Qualifier("tfNettFareCalculator")
	private TransactionFeeCalculator tfNettFareCalculator;
	
	@Autowired
	@Qualifier("tfGrossFareCalculator")
	private TransactionFeeCalculator tfGrossFareCalculator;
	
	@Autowired
	@Qualifier("tfFareCalculator")
	private TransactionFeeCalculator tfFareCalculator;
	
	@Autowired
	@Qualifier("tfBasicCalculator")
	private TransactionFeeCalculator tfBasicCalculator;
	
	@Autowired
	@Qualifier("noFeeCalculator")
	private TransactionFeeCalculator noFeeCalculator;
	
	@Autowired
	@Qualifier("tfPlusVatCalculator")
	private TransactionFeeCalculator tfPlusVatCalculator;
	
	@Autowired
	@Qualifier("tfRebateCalculator")
	private TransactionFeeCalculator tfRebateCalculator;
	
	@Autowired
	@Qualifier("noFeeWithDiscountCalculator")
	private TransactionFeeCalculator noFeeWithDiscountCalculator;
	
	@Autowired
	@Qualifier("tfBaseAndYqCalculator")
	private TransactionFeeCalculator tfBaseAndYqCalculator;

	private Map<Integer, TransactionFeeCalculator> serviceMap = new HashMap<>();

	@PostConstruct
	public void init() {

		this.serviceMap.put(PricingNames.TRANSACTION_FEE.getId(), this.tfCalculator);
		this.serviceMap.put(PricingNames.TF_ON_FULL_FARE.getId(), this.tfFullFareCalculator);
		this.serviceMap.put(PricingNames.TF_ON_NETT_FARE.getId(), this.tfNettFareCalculator);
		this.serviceMap.put(PricingNames.TF_ON_GROSS_FARE.getId(), this.tfGrossFareCalculator);
		this.serviceMap.put(PricingNames.TF_ON_FARE.getId(), this.tfFareCalculator);
		this.serviceMap.put(PricingNames.TF_ON_BASIC.getId(), this.tfBasicCalculator);
		this.serviceMap.put(PricingNames.NO_FEE.getId(), this.noFeeCalculator);
		this.serviceMap.put(PricingNames.TF_PLUS_VAT.getId(), this.tfPlusVatCalculator);
		this.serviceMap.put(PricingNames.TF_REBATE.getId(), this.tfRebateCalculator);
		this.serviceMap.put(PricingNames.NO_FEE_WITH_DISCOUNT.getId(), this.noFeeWithDiscountCalculator);
		this.serviceMap.put(PricingNames.TF_ON_BASE_AND_YQ.getId(), this.tfBaseAndYqCalculator);
	}

	public TransactionFeeCalculator getCalculator(int id) {

		if (this.serviceMap.containsKey(id)) {
			return this.serviceMap.get(id);
		}
		else {
			return this.tfCalculator;
		}
	}

}

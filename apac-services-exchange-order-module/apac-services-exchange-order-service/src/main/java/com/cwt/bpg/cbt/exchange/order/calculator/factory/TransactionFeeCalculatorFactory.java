package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.exchange.order.calculator.tf.FeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.PricingNames;

public class TransactionFeeCalculatorFactory {

	@Autowired
	@Qualifier("tfCalculator")
	private FeeCalculator tfCalculator;
	
	@Autowired
	@Qualifier("tfFullFareCalculator")
	private FeeCalculator tfFullFareCalculator;
	
	@Autowired
	@Qualifier("tfNettFareCalculator")
	private FeeCalculator tfNettFareCalculator;
	
	@Autowired
	@Qualifier("tfGrossFareCalculator")
	private FeeCalculator tfGrossFareCalculator;
	
	@Autowired
	@Qualifier("tfFareCalculator")
	private FeeCalculator tfFareCalculator;
	
	@Autowired
	@Qualifier("tfBasicCalculator")
	private FeeCalculator tfBasicCalculator;
	
	@Autowired
	@Qualifier("noFeeCalculator")
	private FeeCalculator noFeeCalculator;
	
	@Autowired
	@Qualifier("tfPlusVatCalculator")
	private FeeCalculator tfPlusVatCalculator;
	
	@Autowired
	@Qualifier("tfRebateCalculator")
	private FeeCalculator tfRebateCalculator;
	
	@Autowired
	@Qualifier("noFeeWithDiscountCalculator")
	private FeeCalculator noFeeWithDiscountCalculator;
	
	@Autowired
	@Qualifier("tfBaseAndYqCalculator")
	private FeeCalculator tfBaseAndYqCalculator;

	private Map<Integer, FeeCalculator> serviceMap = new HashMap<>();

	@PostConstruct
	public void init() {

		serviceMap.put(PricingNames.TRANSACTION_FEE.getId(), tfCalculator);
		serviceMap.put(PricingNames.TF_ON_FULL_FARE.getId(), tfFullFareCalculator);
		serviceMap.put(PricingNames.TF_ON_NETT_FARE.getId(), tfNettFareCalculator);
		serviceMap.put(PricingNames.TF_ON_GROSS_FARE.getId(), tfGrossFareCalculator);
		serviceMap.put(PricingNames.TF_ON_FARE.getId(), tfFareCalculator);
		serviceMap.put(PricingNames.TF_ON_BASIC.getId(), tfBasicCalculator);
		serviceMap.put(PricingNames.NO_FEE.getId(), noFeeCalculator);
		serviceMap.put(PricingNames.TF_PLUS_VAT.getId(), tfPlusVatCalculator);
		serviceMap.put(PricingNames.TF_REBATE.getId(), tfRebateCalculator);
		serviceMap.put(PricingNames.NO_FEE_WITH_DISCOUNT.getId(), noFeeWithDiscountCalculator);
		serviceMap.put(PricingNames.TF_ON_BASE_AND_YQ.getId(), tfBaseAndYqCalculator);
	}

	public FeeCalculator getCalculator(int id) {

		if (serviceMap.containsKey(id)) {
			return serviceMap.get(id);
		}
		else {
			return tfCalculator;
		}
	}

}

package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.InMiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.FeesInput;
import com.cwt.bpg.cbt.exchange.order.model.InMiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;

@Service
public class OtherServiceFeesService {

	@Autowired
	@Qualifier(value = "miscFeeCalculator")
	private Calculator miscFeeCalculator;
	
	@Autowired
	@Qualifier(value = "inMiscFeeCalculator")
	private InMiscFeeCalculator inMiscFeeCalculator;

	@Autowired
	@Qualifier(value = "nettCostCalculator")
	private NettCostCalculator nettCostCalculator;

	@Autowired
	@Qualifier(value = "visaFeesCalculator")
	private VisaFeesCalculator visaFeesCalculator;

	@Autowired
	private OtherServiceCalculatorFactory osFactory;

	@Autowired
	private ExchangeOrderService exchangeOrderService;

	FeesBreakdown calculateMiscFee(FeesInput input) {
		
		return this.miscFeeCalculator.calculate(input, getMerchantFeePct(input));
	}

	FeesBreakdown calculateAirFee(FeesInput input) {
		return this.osFactory.getCalculator(input.getCountryCode()).calculate(input,
				getMerchantFeePct(input));
	}

	FeesBreakdown calculateVisaFees(FeesInput input) {
		return this.visaFeesCalculator.calculate(input, getMerchantFeePct(input));
	}

	AirFeesBreakdown calculateNettCost(NettCostInput input) {
		return nettCostCalculator.calculateFee(input.getSellingPrice(),
				input.getCommissionPct());
	}

	private MerchantFee getMerchantFeePct(FeesInput input) {

		return exchangeOrderService.getMerchantFee(input.getCountryCode(),
				input.getClientType(), input.getProfileName());
	}

	private Client getClient(InMiscFeesInput input) {
		
		Client client = exchangeOrderService.getClient(input.getProfileName());
		
		if(client != null && client.isStandardMfProduct()) {
			return exchangeOrderService.getDefaultClient();
		}
		
		return client;
	}

	public MiscFeesBreakdown calculateNonAirFee(InMiscFeesInput input) {
		if(Country.INDIA.getCode().equals(input.getCountryCode())) {
			return this.inMiscFeeCalculator.calculate(input, getClient(input));
		}
		
		return new MiscFeesBreakdown();
	}
}

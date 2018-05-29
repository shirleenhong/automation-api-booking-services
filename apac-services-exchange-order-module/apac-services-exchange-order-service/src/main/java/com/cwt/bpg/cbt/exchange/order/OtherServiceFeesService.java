package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.InMiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.FeesInput;
import com.cwt.bpg.cbt.exchange.order.model.InMiscFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

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
	private TransactionFeeCalculatorFactory tfFactory;

	@Autowired
	private ExchangeOrderService exchangeOrderService;
	
	@Autowired
	private ClientService clientService;

	public FeesBreakdown calculateMiscFee(FeesInput input) {
		
		return this.miscFeeCalculator.calculate(input, getMerchantFeePct(input));
	}

	public FeesBreakdown calculateAirFee(FeesInput input) {
		return this.osFactory.getCalculator(input.getCountryCode()).calculate(input,
				getMerchantFeePct(input));
	}

	
	public FeesBreakdown calculateAirFee(TransactionFeesInput input) {		
		return this.tfFactory.getCalculator(
						getPricingId(input.getProfileName()))
				.calculate(getClient(input.getProfileName()), input);
	
	}

	private int getPricingId(String profileName) {
		Client client = getClient(profileName);
		return client != null ? client.getPricingId() : 0;
	}

	public FeesBreakdown calculateVisaFees(FeesInput input) {
		return this.visaFeesCalculator.calculate(input, getMerchantFeePct(input));
	}

	public AirFeesBreakdown calculateNettCost(NettCostInput input) {
		return nettCostCalculator.calculateFee(input.getSellingPrice(),
				input.getCommissionPct());
	}

	private MerchantFee getMerchantFeePct(FeesInput input) {

		return exchangeOrderService.getMerchantFee(input.getCountryCode(),
				input.getClientType(), input.getProfileName());
	}

	private Client getClient(String profileName) {
		
		Client client = clientService.getClient(profileName);
		
		if(client != null && client.isStandardMfProduct()) {
			return clientService.getDefaultClient();
		}
		
		return client;
	}

	public MiscFeesBreakdown calculateNonAirFee(InMiscFeesInput input) {
		if(Country.INDIA.getCode().equals(input.getCountryCode())) {
			return this.inMiscFeeCalculator.calculate(input, getClient(input.getProfileName()));
		}
		
		return new MiscFeesBreakdown();
	}
}

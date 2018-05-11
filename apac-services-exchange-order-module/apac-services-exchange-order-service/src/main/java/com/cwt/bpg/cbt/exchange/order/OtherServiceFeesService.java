package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Service
public class OtherServiceFeesService {

	@Autowired
	@Qualifier(value = "miscFeeCalculator")
	private Calculator miscFeeCalculator;

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
}

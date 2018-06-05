package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.calculator.InNonAirFeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Service
public class OtherServiceFeesService {

	@Autowired
	@Qualifier(value = "hkSgNonAirFeeCalculator")
	private Calculator<NonAirFeesBreakdown, HkSgNonAirFeesInput> hkSgNonAirFeeCalculator;

	@Autowired
	@Qualifier(value = "inNonAirFeeCalculator")
	private InNonAirFeeCalculator inNonAirFeeCalculator;

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

	@Autowired
	private AirportService airportService;

	@Autowired
	private AirlineRuleService airlineRuleService;

	public NonAirFeesBreakdown calculateMiscFee(HkSgNonAirFeesInput input) {

		return this.hkSgNonAirFeeCalculator.calculate(input, getMerchantFeePct(input));
	}

	public AirFeesBreakdown calculateAirFee(AirFeesInput input) {
		String countryCode = input.getCountryCode();
		if ("IN".equalsIgnoreCase(countryCode)) {
			final Client client = getClient(input.getProfileName());
			final int pricingId = getPricingId(input.getProfileName());
			final InAirFeesInput inAirFeesInput = (InAirFeesInput) input;
			final AirlineRule airlineRule = airlineRuleService.getAirlineRule(inAirFeesInput.getPlatCarrier());
			Airport airport = getAirport(inAirFeesInput.getCityCode());

			return this.tfFactory.getCalculator(pricingId)
					.calculate(inAirFeesInput, airlineRule, client, airport);
		}
		else {
			return this.osFactory.getCalculator(countryCode).calculate(input,
					getMerchantFeePct(input));
		}
	}

	private Airport getAirport(String cityCode) {
		return airportService.getAirport(cityCode);
	}

	private int getPricingId(String profileName) {
		Client client = getClient(profileName);
		return client != null ? client.getPricingId() : 0;
	}

	public VisaFeesBreakdown calculateVisaFees(VisaFeesInput input) {
		return this.visaFeesCalculator.calculate(input, getMerchantFeePct(input));
	}

	public AirFeesBreakdown calculateNettCost(NettCostInput input) {
		return nettCostCalculator.calculateFee(input.getSellingPrice(), input.getCommissionPct());
	}

	private MerchantFee getMerchantFeePct(FeesInput input) {

		return exchangeOrderService
				.getMerchantFee(input.getCountryCode(), input.getClientType(), input.getProfileName());
	}

	private Client getClient(String profileName) {

		Client client = clientService.getClient(profileName);

		if (client != null && client.isStandardMfProduct()) {
			return clientService.getDefaultClient();
		}

		return client;
	}

	public NonAirFeesBreakdown calculateNonAirFee(NonAirFeesInput input) {
		if (Country.INDIA.getCode().equals(input.getCountryCode())) {
			return this.inNonAirFeeCalculator.calculate((InNonAirFeesInput)input, getClient(input.getProfileName()));
		}
		else {
			return this.hkSgNonAirFeeCalculator.calculate((HkSgNonAirFeesInput)input, getMerchantFeePct(input));
		}
	}
}

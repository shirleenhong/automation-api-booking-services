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
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

@Service
public class OtherServiceFeesService {

	private static final String AIR_PRODUCT_CODE = "35";

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
	
	@Autowired
	private ProductService productService;

	public NonAirFeesBreakdown calculateMiscFee(HkSgNonAirFeesInput input) {

		return this.hkSgNonAirFeeCalculator.calculate(input, getMerchantFeePct(input));
	}

	public AirFeesBreakdown calculateAirFee(AirFeesInput input) {
		String countryCode = input.getCountryCode();
		if ("IN".equalsIgnoreCase(countryCode)) {
			final Client client = clientService.getClient(input.getProfileName());
			final int pricingId = getPricingId(input.getProfileName());
			final InAirFeesInput inAirFeesInput = (InAirFeesInput) input;
			final AirlineRule airlineRule = airlineRuleService
					.getAirlineRule(inAirFeesInput.getPlatCarrier());
			final Airport airport = getAirport(inAirFeesInput.getCityCode());
			final Product airproduct = getProduct(
							Country.INDIA.getCode(), 
							AIR_PRODUCT_CODE);

			return this.tfFactory.getCalculator(pricingId)
					.calculate(inAirFeesInput, airlineRule, client, airport, airproduct);
		}
		else {
			return this.osFactory.getCalculator(countryCode).calculate(input, getMerchantFeePct(input));
		}
	}

	private Product getProduct(String countryCode, String productCode) {
		return productService.getProductByCode(countryCode, productCode);
	}

	private Airport getAirport(String cityCode) {
		return airportService.getAirport(cityCode);
	}

	private int getPricingId(String profileName) {
		Client client = clientService.getClient(profileName);
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

	public NonAirFeesBreakdown calculateNonAirFee(NonAirFeesInput input) {
		
		if (Country.INDIA.getCode().equals(input.getCountryCode())) {
			
			final Client client = clientService.getClient(input.getProfileName());
			final Client defaultClient = clientService.getDefaultClient(); 
			
			return this.inNonAirFeeCalculator.calculate((InNonAirFeesInput) input,
					client, defaultClient);
		}
		else {
			return this.hkSgNonAirFeeCalculator.calculate((HkSgNonAirFeesInput) input,
					getMerchantFeePct(input));
		}
	}
}

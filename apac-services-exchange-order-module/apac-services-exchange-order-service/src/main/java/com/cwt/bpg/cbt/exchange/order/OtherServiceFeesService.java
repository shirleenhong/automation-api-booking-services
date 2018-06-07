package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.IndiaNonAirFeeCalculator;
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
	private Calculator<NonAirFeesBreakdown, NonAirFeesInput> hkSgNonAirFeeCalculator;

	@Autowired
	@Qualifier(value = "inNonAirFeeCalculator")
	private IndiaNonAirFeeCalculator inNonAirFeeCalculator;

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

	public NonAirFeesBreakdown calculateNonAirFee(NonAirFeesInput input) {
        MerchantFee merchantFee = exchangeOrderService
                .getMerchantFee(input.getCountryCode(), input.getClientType(), input.getProfileName());
		return this.hkSgNonAirFeeCalculator.calculate(input, merchantFee);
	}

	public NonAirFeesBreakdown calculateInNonAirFee(InNonAirFeesInput input) {

		final Client client = clientService.getClient(input.getProfileName());
		final Client defaultClient = clientService.getDefaultClient();

		return this.inNonAirFeeCalculator.calculate(input, client, defaultClient);
	}

	public AirFeesBreakdown calculateAirFees(AirFeesInput input) {
        MerchantFee merchantFee = exchangeOrderService
                .getMerchantFee(input.getCountryCode(), input.getClientType(), input.getProfileName());
	    return this.osFactory.getCalculator(input.getCountryCode()).calculate(input, merchantFee);
	}

    public IndiaAirFeesBreakdown calculateIndiaAirFees(IndiaAirFeesInput input)
    {
        final Client client = clientService.getClient(input.getProfileName());
        final int pricingId = getPricingId(input.getProfileName());
        final AirlineRule airlineRule = airlineRuleService.getAirlineRule(input.getPlatCarrier());
        final Airport airport = getAirport(input.getCityCode());
        final Product airProduct = getProduct(Country.INDIA.getCode(), AIR_PRODUCT_CODE);

        return this.tfFactory.getCalculator(pricingId)
                .calculate(input, airlineRule, client, airport, airProduct);
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
        MerchantFee merchantFee = exchangeOrderService
                .getMerchantFee(input.getCountryCode(), input.getClientType(), input.getProfileName());
        return this.visaFeesCalculator.calculate(input, merchantFee);
	}

	public AirFeesBreakdown calculateNettCost(NettCostInput input) {
		return nettCostCalculator.calculateFee(input.getSellingPrice(), input.getCommissionPct());
	}

}

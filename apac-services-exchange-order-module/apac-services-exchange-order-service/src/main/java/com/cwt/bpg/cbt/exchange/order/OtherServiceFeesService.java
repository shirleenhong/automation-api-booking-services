package com.cwt.bpg.cbt.exchange.order;

import java.util.Optional;

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
	@Qualifier(value = "nonAirFeeCalculator")
	private Calculator<NonAirFeesBreakdown, NonAirFeesInput> nonAirFeeCalculator;

	@Autowired
	@Qualifier(value = "indiaNonAirFeeCalculator")
	private IndiaNonAirFeeCalculator indiaNonAirFeeCalculator;

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
	private MerchantFeeService merchantFeeService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private AirportService airportService;

	@Autowired
	private AirlineRuleService airlineRuleService;

	@Autowired
	private ProductService productService;

	NonAirFeesBreakdown calculateNonAirFees(NonAirFeesInput input, String countryCode) {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(countryCode, input.getClientAccountNumber());
		return this.nonAirFeeCalculator.calculate(input, merchantFee, countryCode);
	}

	IndiaNonAirFeesBreakdown calculateIndiaNonAirFees(IndiaNonAirFeesInput input) {

		final Client client = clientService.getClient(input.getClientAccountNumber());
		final Client defaultClient = clientService.getDefaultClient();

		return this.indiaNonAirFeeCalculator.calculate(input, client, defaultClient);
	}

	AirFeesBreakdown calculateAirFees(AirFeesInput input, String countryCode) {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(countryCode, input.getClientAccountNumber());
	    return this.osFactory.getCalculator(countryCode).calculate(input, merchantFee, countryCode);
	}

    IndiaAirFeesBreakdown calculateIndiaAirFees(IndiaAirFeesInput input){

		Optional<Client> isClientExist = Optional.ofNullable(clientService.getClient(input.getClientAccountNumber()));

		Client client = isClientExist
				.orElseThrow(() -> new IllegalArgumentException(
						"Client [ " + input.getClientAccountNumber() + " ] not found."));

        final int pricingId = client.getPricingId();
        final AirlineRule airlineRule = airlineRuleService.getAirlineRule(input.getPlatCarrier());

		Optional<Airport> isAirportExist = Optional.ofNullable(getAirport(input.getCityCode()));

		Airport airport = isAirportExist
				.orElseThrow(() -> new IllegalArgumentException(
						"Airport City Code [ " + input.getCityCode() + " ] not found."));

        final IndiaProduct airProduct = (IndiaProduct) getProduct(Country.INDIA.getCode(), AIR_PRODUCT_CODE);

        return this.tfFactory.getCalculator(pricingId)
                .calculate(input, airlineRule, client, airport, airProduct);
    }

	private BaseProduct getProduct(String countryCode, String productCode) {
		return productService.getProductByCode(countryCode, productCode);
	}

	private Airport getAirport(String cityCode) {
		return airportService.getAirport(cityCode);
	}

	VisaFeesBreakdown calculateVisaFees(VisaFeesInput input) {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(input.getCountryCode(), input.getClientAccountNumber());
        return this.visaFeesCalculator.calculate(input, merchantFee, input.getCountryCode());
	}

	AirFeesBreakdown calculateNettCost(NettCostInput input) {
		return nettCostCalculator.calculateFee(input.getSellingPrice(), input.getCommissionPct());
	}

}

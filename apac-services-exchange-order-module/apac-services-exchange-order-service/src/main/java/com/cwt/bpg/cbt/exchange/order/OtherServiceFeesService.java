package com.cwt.bpg.cbt.exchange.order;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.IndiaNonAirFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsOutput;
import com.cwt.bpg.cbt.exchange.order.model.india.MerchantFeePercentInput;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

@Service
public class OtherServiceFeesService {

    private static final String AIR_PRODUCT_CODE = "35";

    static final int BILL_TO_COMPANY = 3;


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
        final Double merchantFeePercent = getMerchantFeePercent(convertToMFPercentInput(input), client, defaultClient);

        return this.indiaNonAirFeeCalculator.calculate(input, client, merchantFeePercent);
    }

    AirFeesBreakdown calculateAirFees(AirFeesInput input, String countryCode) {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(countryCode, input.getClientAccountNumber());
        return this.osFactory.getCalculator(countryCode).calculate(input, merchantFee, countryCode);
    }

    IndiaAirFeesBreakdown calculateIndiaAirFees(IndiaAirFeesInput input) {

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

    AirFeesDefaultsOutput getAirFeesDefaults(AirFeesDefaultsInput input) {
        return null;
    }

    Double getMerchantFeePercent(MerchantFeePercentInput input) {
        final Client client = clientService.getClient(input.getClientAccountNumber());
        final Client defaultClient = clientService.getDefaultClient();

        return getMerchantFeePercent(input, client, defaultClient);
    }

    private Double getMerchantFeePercent(MerchantFeePercentInput input, Client client, Client defaultClient) {

        Double mfPercent;

        if (input.getFopMode() == BILL_TO_COMPANY
                || isProductSubjectToMF(client, input.getProductCode())
                || isProductSubjectToMF(client.isStandardMfProduct() ? defaultClient : client,
                input.getProductCode())) {
            mfPercent = 0D;
        }
        else {
            mfPercent = client.getMerchantFee();

            Bank bank = getBank(client, input.getFopNumber(), client.isApplyMfBank());
            if (bank != null && !StringUtils.isEmpty(bank.getCcNumberPrefix())) {
                mfPercent = bank.getPercentage();
            }
            else if (!StringUtils.isEmpty(input.getFopType())) {
                CreditCardVendor vendor = getCreditCard(client, input.getCcType(), client.isApplyMfCc());
                mfPercent = vendor != null ? vendor.getPercentage() : 0D;
            }
        }

        return mfPercent;
    }

    private boolean isProductSubjectToMF(Client client, String productCode) {
        ProductMerchantFee productMerchantFee = null;
        if (client.getMfProducts() != null) {
            Optional<ProductMerchantFee> result = client.getMfProducts().stream()
                    .filter(item -> item.getProductCode().equals(productCode)).findFirst();

            productMerchantFee = result.orElse(null);
        }
        return productMerchantFee != null && productMerchantFee.isSubjectToMf();
    }

    private CreditCardVendor getCreditCard(Client client, String acctType, boolean isStandard) {
        Optional<CreditCardVendor> vendor = Optional.empty();
        if (client.getMfCcs() != null) {
            vendor = client.getMfCcs().stream()
                    .filter(item -> item.getVendorName().equals(acctType) && item.isStandard() == isStandard).findFirst();
        }
        return vendor.orElse(null);
    }

    private Bank getBank(Client client, String fopNumber, boolean isStandard) {
        Optional<Bank> bank = Optional.empty();
        if (client.getMfBanks() != null) {
            bank = client.getMfBanks().stream()
                    .filter(item -> fopNumber.startsWith(item.getCcNumberPrefix()) && item.isStandard() == isStandard).findFirst();
        }
        return bank.orElse(null);
    }

    private MerchantFeePercentInput convertToMFPercentInput(IndiaNonAirFeesInput indiaNonAirFeesInput) {
        MerchantFeePercentInput merchantFeePercentInput = new MerchantFeePercentInput();
        merchantFeePercentInput.setCcType(indiaNonAirFeesInput.getCcType());
        merchantFeePercentInput.setFopNumber(indiaNonAirFeesInput.getFopNumber());
        merchantFeePercentInput.setFopType(indiaNonAirFeesInput.getFopType());
        merchantFeePercentInput.setProductCode(indiaNonAirFeesInput.getProduct().getProductCode());
        return merchantFeePercentInput;
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

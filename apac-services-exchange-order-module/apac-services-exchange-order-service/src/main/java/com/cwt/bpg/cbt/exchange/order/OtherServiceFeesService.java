package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceNonAirCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.IndiaNonAirFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;
import com.cwt.bpg.cbt.exchange.order.model.Bank;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.ClientFee;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;
import com.cwt.bpg.cbt.exchange.order.model.CreditCardVendor;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaNonAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.ProductMerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.VisaFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsOutput;
import com.cwt.bpg.cbt.exchange.order.model.india.MerchantFeePercentInput;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

@Service
public class OtherServiceFeesService
{

    private static final String AIR_PRODUCT_CODE = "35";

    static final int BILL_TO_COMPANY = 3;

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
    private OtherServiceNonAirCalculatorFactory osNonAirFactory;

    @Autowired
    private TransactionFeeCalculatorFactory tfFactory;

    @Autowired
    private MerchantFeeService merchantFeeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AirlineRuleService airlineRuleService;

    @Autowired
    private ProductService productService;

    public NonAirFeesBreakdown calculateNonAirFees(NonAirFeesInput input, String countryCode)
    {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(countryCode, input.getClientAccountNumber());
        return this.osNonAirFactory.getCalculator(countryCode).calculate(input, merchantFee, countryCode);
    }

    public IndiaNonAirFeesBreakdown calculateIndiaNonAirFees(IndiaNonAirFeesInput input)
    {
        Optional<Client> isClientExist = Optional.ofNullable(clientService.getClient(input));

        Client client = isClientExist
                .orElseThrow(() -> new IllegalArgumentException(
                        "Client [ client id: " + input.getClientId() + " or client account number: " + input.getClientAccountNumber() + " ] not found."));

        final Client defaultClient = clientService.getDefaultClient();
        final Double merchantFeePercent = getMerchantFeePercent(convertToMFPercentInput(input), client, defaultClient);

        return this.indiaNonAirFeeCalculator.calculate(input, client, merchantFeePercent);
    }

    public AirFeesBreakdown calculateAirFees(AirFeesInput input, String countryCode)
    {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(countryCode, input.getClientAccountNumber());
        return this.osFactory.getCalculator(countryCode).calculate(input, merchantFee, countryCode);
    }

    public IndiaAirFeesBreakdown calculateIndiaAirFees(IndiaAirFeesInput input)
    {
        Optional<Client> isClientExist = Optional.ofNullable(clientService.getClient(input));

        Client client = isClientExist
                .orElseThrow(() -> new IllegalArgumentException(
                        "Client [ client id: " + input.getClientId() + " or client account number: " + input.getClientAccountNumber() + " ] not found."));

        final int pricingId = client.getPricingId();
        final AirlineRule airlineRule = airlineRuleService.getAirlineRule(input.getPlatCarrier());

        final IndiaProduct airProduct = (IndiaProduct) getProduct(Country.INDIA.getCode(), AIR_PRODUCT_CODE);

        return this.tfFactory.getCalculator(pricingId)
                .calculate(input, airlineRule, client, airProduct);
    }

    public AirFeesDefaultsOutput getAirFeesDefaults(AirFeesDefaultsInput input)
    {
        AirFeesDefaultsOutput output = new AirFeesDefaultsOutput();
        List<ClientFee> clientFees = new ArrayList<>();

        List<ClientPricing> clientPricings = clientService
                .getClientPricings(input);

        if (!ObjectUtils.isEmpty(clientPricings))
        {
            for (ClientPricing pricing : clientPricings)
            {
                ClientFee clientFee = new ClientFee();
                clientFee.setFeeName(pricing.getFeeName());
                clientFee.setTripType(pricing.getTripType());
                clientFee.setValue(pricing.getValue());
                clientFees.add(clientFee);
            }
        }
        output.setClientFees(clientFees);

        if (!isAnyFieldNull(input.getFopType(), input.getFopMode(), input.getFopNumber(),
                input.getProductCode(), input.getCcType()))
        {

            Double merchantFeePercent = getMerchantFeePercent(
                    formMerchantFeeInput(input));
            output.setMerchantFeePercent(merchantFeePercent);
        }

        return output;
    }

    private boolean isAnyFieldNull(Object... fields)
    {
        for (Object field : fields)
        {
            if (field == null)
            {
                return true;
            }
        }
        return false;
    }

    private MerchantFeePercentInput formMerchantFeeInput(AirFeesDefaultsInput input)
    {
        MerchantFeePercentInput merchantFeeInput = new MerchantFeePercentInput();

        merchantFeeInput.setCcType(input.getCcType());
        merchantFeeInput.setClientAccountNumber(input.getClientAccountNumber());
        merchantFeeInput.setFopMode(input.getFopMode());
        merchantFeeInput.setFopNumber(input.getFopNumber());
        merchantFeeInput.setFopType(input.getFopType());
        merchantFeeInput.setProductCode(input.getProductCode());

        return merchantFeeInput;
    }

    public Double getMerchantFeePercent(MerchantFeePercentInput input)
    {
        final Client client = clientService.getClient(input);
        final Client defaultClient = clientService.getDefaultClient();

        return getMerchantFeePercent(input, client, defaultClient);
    }

    private Double getMerchantFeePercent(MerchantFeePercentInput input, Client client, Client defaultClient)
    {
        Double mfPercent;

        if (input.getFopMode() == BILL_TO_COMPANY
                || !isProductSubjectToMF(client.isStandardMfProduct() ? defaultClient : client,
                input.getProductCode()))
        {
            mfPercent = 0D;
        }
        else
        {
            mfPercent = client.getMerchantFee();

            Bank bank = getBank(getClient(client, defaultClient, client.isApplyMfBank()),
                    input.getFopNumber(),
                    client.isApplyMfBank());

            if (bank != null && !StringUtils.isEmpty(bank.getCcNumberPrefix()))
            {
                mfPercent = bank.getPercentage();
            }
            else if (!StringUtils.isEmpty(input.getFopType()))
            {
                CreditCardVendor vendor = getCreditCard(
                        getClient(client, defaultClient, client.isApplyMfCc()),
                        input.getCcType(),
                        client.isApplyMfCc());

                mfPercent = vendor != null ? vendor.getPercentage() : 0D;
            }
        }

        return mfPercent;
    }

    private Client getClient(Client client, Client defaultClient, boolean isStandard)
    {
        return isStandard ? defaultClient : client;
    }

    private boolean isProductSubjectToMF(Client client, String productCode)
    {
        ProductMerchantFee productMerchantFee = null;
        if (client.getMfProducts() != null)
        {
            Optional<ProductMerchantFee> result = client.getMfProducts()
                    .stream()
                    .filter(item -> item.getProductCode().equals(productCode))
                    .findFirst();

            productMerchantFee = result.orElse(null);
        }
        return productMerchantFee != null && productMerchantFee.isSubjectToMf();
    }

    private CreditCardVendor getCreditCard(Client client, String acctType, boolean isStandard)
    {
        Optional<CreditCardVendor> vendor = Optional.empty();
        if (client.getMfCcs() != null)
        {
            vendor = client.getMfCcs()
                    .stream()
                    .filter(item -> item.getVendorName().equals(acctType)
                            && item.isStandard() == isStandard)
                    .findFirst();
        }
        return vendor.orElse(null);
    }

    private Bank getBank(Client client, String fopNumber, boolean isStandard)
    {
        Optional<Bank> bank = Optional.empty();
        if (client.getMfBanks() != null)
        {
            bank = client.getMfBanks()
                    .stream()
                    .filter(item -> fopNumber.startsWith(item.getCcNumberPrefix())
                            && item.isStandard() == isStandard)
                    .findFirst();
        }
        return bank.orElse(null);
    }

    private MerchantFeePercentInput convertToMFPercentInput(IndiaNonAirFeesInput indiaNonAirFeesInput)
    {
        MerchantFeePercentInput merchantFeePercentInput = new MerchantFeePercentInput();
        merchantFeePercentInput.setCcType(indiaNonAirFeesInput.getCcType());
        merchantFeePercentInput.setFopNumber(indiaNonAirFeesInput.getFopNumber());
        merchantFeePercentInput.setFopType(indiaNonAirFeesInput.getFopType());
        merchantFeePercentInput.setProductCode(indiaNonAirFeesInput.getProduct().getProductCode());
        return merchantFeePercentInput;
    }

    private BaseProduct getProduct(String countryCode, String productCode)
    {
        return productService.getProductByCode(countryCode, productCode);
    }

    public VisaFeesBreakdown calculateVisaFees(VisaFeesInput input)
    {
        MerchantFee merchantFee = merchantFeeService.getMerchantFee(input.getCountryCode(), input.getClientAccountNumber());
        return this.visaFeesCalculator.calculate(input, merchantFee, input.getCountryCode());
    }

    public AirFeesBreakdown calculateNettCost(NettCostInput input)
    {
        return nettCostCalculator.calculateFee(input.getSellingPrice(), input.getCommissionPct());
    }
}

package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.IndiaNonAirFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.FeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsOutput;
import com.cwt.bpg.cbt.exchange.order.model.india.MerchantFeePercentInput;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

public class OtherServiceFeesServiceTest {

    @Mock
    private OtherServiceCalculatorFactory factory;

    @Mock
    private TransactionFeeCalculatorFactory tfFactory;

    @Mock
    private Calculator<NonAirFeesBreakdown, NonAirFeesInput> nonAirFeeCalculator;

    @Mock
    private Calculator<AirFeesBreakdown, AirFeesInput> hkCalculator;

    @Mock
    private FeeCalculator tfCalculator;

    @Mock
    private NettCostCalculator nettCostCalculator;

    @Mock
    private MerchantFeeService merchantFeeService;

    @Mock
    private ClientService clientService;

    @Mock
    private AirlineRuleService airlineRuleService;

    @Mock
    private AirportService airportService;

    @Mock
    private VisaFeesCalculator visaFeesCalculator;

    @Mock
    private IndiaNonAirFeeCalculator indiaNonAirFeeCalculator;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OtherServiceFeesService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(merchantFeeService.getMerchantFee(anyString(), anyString())).thenReturn(new MerchantFee());
    }

    @Test
    public void shouldReturnFeesBreakdown() {

        when(nonAirFeeCalculator.calculate(any(NonAirFeesInput.class), any(MerchantFee.class), anyString()))
                .thenReturn(new NonAirFeesBreakdown());

        NonAirFeesInput input = new NonAirFeesInput();
        input.setClientAccountNumber("12345");

        assertNotNull(service.calculateNonAirFees(input, "ZZ"));
    }

    @Test
    public void shouldReturnAirFeesBreakdown() {

        when(factory.getCalculator(anyString()))
                .thenReturn(hkCalculator);

        when(hkCalculator.calculate(any(AirFeesInput.class), any(MerchantFee.class), anyString()))
                .thenReturn(new AirFeesBreakdown());

        AirFeesInput input = new AirFeesInput();
        input.setClientAccountNumber("12345");

        assertNotNull(service.calculateAirFees(input, "HK"));
    }

    @Test
    public void shouldReturnNettCost() {

        when(nettCostCalculator.calculateFee(any(BigDecimal.class), any(Double.class)))
                .thenReturn(new AirFeesBreakdown());

        NettCostInput input = new NettCostInput();
        input.setCommissionPct(0d);
        input.setSellingPrice(BigDecimal.TEN);

        assertNotNull(service.calculateNettCost(input));
    }

    @Test
    public void shouldReturnIndiaAirFeesBreakdown() {

        when(tfFactory.getCalculator(anyInt())).thenReturn(tfCalculator);

        when(tfCalculator.
                calculate(
                        any(IndiaAirFeesInput.class),
                        any(AirlineRule.class),
                        any(Client.class),
                        any(BaseProduct.class)))
                .thenReturn(new IndiaAirFeesBreakdown());

        when(airlineRuleService.getAirlineRule(anyString())).thenReturn(new AirlineRule());
        when(airportService.getAirport(anyString())).thenReturn(new Airport());
        when(productService.getProductByCode(anyString(), anyString())).thenReturn(new IndiaProduct());

        Client client = new Client();
        client.setPricingId(20);
        when(clientService.getClient(any(IndiaAirFeesInput.class))).thenReturn(client);

        IndiaAirFeesInput input = new IndiaAirFeesInput();
        input.setClientAccountNumber("123456");
        input.setClientId(0);
        input.setCityCode("GGN");
        input.setPlatCarrier("AA");

        assertNotNull(service.calculateIndiaAirFees(input));
    }

    @Test
    public void shouldReturnVisaFees() {
        when(visaFeesCalculator
                .calculate(
                        any(VisaFeesInput.class),
                        any(MerchantFee.class),
                        anyString()))
                .thenReturn(new VisaFeesBreakdown());

        VisaFeesInput input = new VisaFeesInput();
        input.setCountryCode(Country.HONG_KONG.getCode());
        input.setClientAccountNumber("12345");

        assertNotNull(service.calculateVisaFees(input));

    }

    @Test
    public void shouldReturnNonAirFeeIndia() {
        when(indiaNonAirFeeCalculator
                .calculate(
                        any(IndiaNonAirFeesInput.class),
                        any(Client.class),
                        anyDouble()))
                .thenReturn(new IndiaNonAirFeesBreakdown());

        Client client = new Client();
        client.setClientId(0);
        client.setPricingId(20);
        client.setStandardMfProduct(false);
        when(clientService.getClient(any(IndiaNonAirFeesInput.class))).thenReturn(client);

        IndiaNonAirFeesInput input = new IndiaNonAirFeesInput();
        input.setProduct(new IndiaNonAirProductInput());
        input.setClientAccountNumber("12345");
        input.setCcType("VI");
        input.setFopNumber("4111111111111111");
        input.setFopType(FopType.CREDIT_CARD);

        assertNotNull(service.calculateIndiaNonAirFees(input));
    }

    @Test
    public void shouldReturnNonAirFeeNonIndia() {
        when(nonAirFeeCalculator
                .calculate(
                        any(NonAirFeesInput.class),
                        any(MerchantFee.class),
                        anyString()))
                .thenReturn(new NonAirFeesBreakdown());

        NonAirFeesInput input = new NonAirFeesInput();
        input.setClientAccountNumber("12345");

        assertNotNull(service.calculateNonAirFees(input, "HK"));
    }

    @Test
    public void shouldReturnZeroMerchantFeePercentWhenFopModeIsBTC() {
        when(clientService.getClient(anyString())).thenReturn(any(Client.class));

        MerchantFeePercentInput input = new MerchantFeePercentInput();
        input.setFopMode(OtherServiceFeesService.BILL_TO_COMPANY);

        Double merchantFeePercent = service.getMerchantFeePercent(input);

        assertThat(merchantFeePercent, equalTo(0d));
    }

    @Test
    public void shouldReturnZeroMerchantFeePercentWhenClientMFProductIsSubjectToMF() {
        final String productCode = "PROD1";

        Client client = new Client();
        client.setClientId(0);
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));

        when(clientService.getClient(any(MerchantFeePercentInput.class))).thenReturn(client);

        MerchantFeePercentInput input = new MerchantFeePercentInput();
        input.setFopMode(2);
        input.setProductCode(productCode);
        input.setClientAccountNumber("12345");

        Double merchantFeePercent = service.getMerchantFeePercent(input);

        assertNull(merchantFeePercent);
    }

    @Test
    public void shouldReturnAirFeesDefaults_noClientPricings() {
        Client client = new Client();
        client.setClientId(0);
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        String productCode = "PROD1";
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));

        List<ClientPricing> clientPricings = new ArrayList<>();
        AirFeesDefaultsInput input = createAirFeesDefaults();
        when(clientService.getClientPricings(input)).thenReturn(clientPricings);
        when(clientService.getClient(any(AirFeesDefaultsInput.class))).thenReturn(client);
        when(clientService.getClient(any(MerchantFeePercentInput.class))).thenReturn(client);
        when(clientService.getDefaultClient()).thenReturn(client);

        AirFeesDefaultsOutput output = service.getAirFeesDefaults(input);

        assertNotNull(output);
        assertThat(output.getMerchantFeePercent(), equalTo(0d));

    }

    @Test
    public void shouldReturnAirFeesDefaults() {
        List<ClientPricing> clientPricings = new ArrayList<>();

        Client client = new Client();
        client.setClientId(0);
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        String productCode = "PROD1";
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));

        ClientPricing clientPricing = new ClientPricing();
        clientPricing.setCmpid(1);
        clientPricings.add(clientPricing);

        AirFeesDefaultsInput input = createAirFeesDefaults();
        when(clientService.getClientPricings(input)).thenReturn(clientPricings);
        when(clientService.getClient(input)).thenReturn(client);
        when(clientService.getClient(any(MerchantFeePercentInput.class))).thenReturn(client);

        AirFeesDefaultsOutput output = service.getAirFeesDefaults(input);

        assertNotNull(output);
        assertThat(output.getMerchantFeePercent(), equalTo(0d));

    }

    @Test
    public void shouldReturnAirFeesDefaultsNullFields() {
        List<ClientPricing> clientPricings = new ArrayList<>();

        Client client = new Client();
        client.setClientId(0);
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        String productCode = "PROD1";
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));

        ClientPricing clientPricing = new ClientPricing();
        clientPricing.setCmpid(1);
        clientPricings.add(clientPricing);

        AirFeesDefaultsInput input = createAirFeesDefaultsWithNull();
        when(clientService.getClientPricings(input)).thenReturn(clientPricings);
        when(clientService.getClient(0)).thenReturn(client);

        AirFeesDefaultsOutput output = service.getAirFeesDefaults(input);

        assertNotNull(output);
        assertThat(output.getMerchantFeePercent(), nullValue());

    }


    private AirFeesDefaultsInput createAirFeesDefaults() {

        AirFeesDefaultsInput input = new AirFeesDefaultsInput();
        input.setCcType("VISA");
        input.setClientAccountNumber("2078200002");
        input.setFopMode(2);
        input.setFopNumber("1234");
        input.setFopType(FopType.CWT);
        input.setProductCode("02");

        return input;
    }

    private AirFeesDefaultsInput createAirFeesDefaultsWithNull() {

        AirFeesDefaultsInput input = new AirFeesDefaultsInput();
        input.setClientAccountNumber("2078200002");
        input.setFopMode(2);
        input.setFopNumber("1234");
        input.setFopType(FopType.CWT);

        return input;
    }
}

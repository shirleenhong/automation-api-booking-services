package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

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
	private MerchantFeeService orderService;

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
	}

	@Test
	public void shouldReturnFeesBreakdown() {

		when(nonAirFeeCalculator.calculate(anyObject(), anyObject(), anyString()))
			.thenReturn(new NonAirFeesBreakdown());
		assertNotNull(service.calculateNonAirFees(new NonAirFeesInput(), null));
	}

	@Test
	public void shouldReturnAirFeesBreakdown() {

		when(factory.getCalculator(anyString()))
			.thenReturn(hkCalculator);

		when(hkCalculator.calculate(anyObject(), anyObject(), anyString()))
			.thenReturn(new AirFeesBreakdown());

		assertNotNull(service.calculateAirFees(new AirFeesInput(), "HK"));
	}

	@Test
	public void shouldReturnNettCost() {

		when(nettCostCalculator.calculateFee(anyObject(), anyObject()))
			.thenReturn(new AirFeesBreakdown());
		assertNotNull(service.calculateNettCost(new NettCostInput()));
	}

	@Test
	public void shouldReturnIndiaAirFeesBreakdown() {

		when(tfFactory.getCalculator(anyInt()))
			.thenReturn(tfCalculator);

		when(tfCalculator.calculate(anyObject(), anyObject(), anyObject(),
				anyObject(), anyObject()))
			.thenReturn(new IndiaAirFeesBreakdown());

		when(airlineRuleService.getAirlineRule(anyString())).thenReturn(new AirlineRule());
		when(airportService.getAirport(anyString())).thenReturn(new Airport());

		Client client = new Client();
		client.setPricingId(20);
		when(clientService.getClient(anyString()))
				.thenReturn(client);

		IndiaAirFeesInput input = new IndiaAirFeesInput();

		assertNotNull(service.calculateIndiaAirFees(input));
	}

	@Test
	public void shouldReturnVisaFees() {
		when(visaFeesCalculator.calculate(anyObject(), anyObject(), anyString())).thenReturn(new VisaFeesBreakdown());

		VisaFeesInput input = new VisaFeesInput();
		input.setCountryCode(Country.HONG_KONG.getCode());

		assertNotNull(service.calculateVisaFees(input));

	}

	@Test
	public void shouldReturnNonAirFeeIndia() {
		when(indiaNonAirFeeCalculator.calculate(anyObject(), anyObject(), anyDouble())).thenReturn(new IndiaNonAirFeesBreakdown());

		Client client = new Client();
		client.setPricingId(20);
		client.setStandardMfProduct(false);
		when(clientService.getClient(anyString())).thenReturn(client);

		IndiaNonAirFeesInput input = new IndiaNonAirFeesInput();
		input.setProduct(new IndiaNonAirProductInput());

		assertNotNull(service.calculateIndiaNonAirFees(input));
	}

	@Test
	public void shouldReturnNonAirFeeHkSg() {
		when(nonAirFeeCalculator.calculate(anyObject(), anyObject(), anyString())).thenReturn(new NonAirFeesBreakdown());
		
		Client client = new Client();
		client.setPricingId(20);
		client.setStandardMfProduct(false);
		when(clientService.getClient(anyString())).thenReturn(client);

		NonAirFeesInput input = new NonAirFeesInput();
		
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
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));

        when(clientService.getClient(anyString())).thenReturn(client);

        MerchantFeePercentInput input = new MerchantFeePercentInput();
        input.setFopMode(2);
        input.setProductCode(productCode);

        Double merchantFeePercent = service.getMerchantFeePercent(input);

        assertThat(merchantFeePercent, equalTo(0d));
    }
    
    @Test
    public void shouldReturnAirFeesDefaults_noClientPricings() {
    	AirFeesDefaultsOutput output = new AirFeesDefaultsOutput();
    	List<ClientPricing> clientPricings = new ArrayList<>();
    	
    	Client client = new Client();
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        String productCode = "PROD1";
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));
        
		AirFeesDefaultsInput input = createAirFeesDefaults();
		when(clientService.getClientPricings(input.getClientAccountNumber(),
				input.getTripType())).thenReturn(clientPricings);
		when(clientService.getClient(anyString())).thenReturn(client);
		 
		output = service.getAirFeesDefaults(input);
		
		assertNotNull(output);
    	assertThat(output.getMerchantFeePercent(), equalTo(0d));
    	
    }
    
    @Test
    public void shouldReturnAirFeesDefaults() {
    	AirFeesDefaultsOutput output = new AirFeesDefaultsOutput();
    	List<ClientPricing> clientPricings = new ArrayList<>();
    	
    	Client client = new Client();
        ProductMerchantFee mfProduct = new ProductMerchantFee();
        String productCode = "PROD1";
        mfProduct.setProductCode(productCode);
        mfProduct.setSubjectToMf(true);
        client.setMfProducts(Collections.singletonList(mfProduct));
        
        ClientPricing clientPricing = new ClientPricing();
        clientPricing.setCmpid(1);
        clientPricings.add(clientPricing);
        
		AirFeesDefaultsInput input = createAirFeesDefaults();
		when(clientService.getClientPricings(input.getClientAccountNumber(),
				input.getTripType())).thenReturn(clientPricings);
		when(clientService.getClient(anyString())).thenReturn(client);
		 
		output = service.getAirFeesDefaults(input);
		
		assertNotNull(output);
    	assertThat(output.getMerchantFeePercent(), equalTo(0d));
    	
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

}

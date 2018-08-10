package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

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
		when(indiaNonAirFeeCalculator.calculate(anyObject(), anyObject(), anyObject())).thenReturn(new IndiaNonAirFeesBreakdown());

		Client client = new Client();
		client.setPricingId(20);
		client.setStandardMfProduct(false);
		when(clientService.getClient(anyString())).thenReturn(client);

		IndiaNonAirFeesInput input = new IndiaNonAirFeesInput();

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
}

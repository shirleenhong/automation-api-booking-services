package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import com.cwt.bpg.cbt.exchange.order.calculator.InNonAirFeeCalculator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
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
	private Calculator<NonAirFeesBreakdown, HkSgNonAirFeesInput> hkSgNonAirFeeCalculator;

	@Mock
	private Calculator<AirFeesBreakdown, HkSgAirFeesInput> hkCalculator;

	@Mock
	private FeeCalculator tfCalculator;

	@Mock
	private NettCostCalculator nettCostCalculator;

	@Mock
	private ExchangeOrderService orderService;

	@Mock
	private ClientService clientService;

	@Mock
	private AirlineRuleService airlineRuleService;

	@Mock
	private AirportService airportService;

	@Mock
	private VisaFeesCalculator visaFeesCalculator;

	@Mock
	private InNonAirFeeCalculator inNonAirFeeCalculator;

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

		when(hkSgNonAirFeeCalculator.calculate(anyObject(), anyObject()))
			.thenReturn(new NonAirFeesBreakdown());
		assertNotNull(service.calculateMiscFee(new HkSgNonAirFeesInput()));
	}

	@Test
	public void shouldReturnAirFeesBreakdown() {

		when(factory.getCalculator(anyString()))
			.thenReturn(hkCalculator);

		when(hkCalculator.calculate(anyObject(), anyObject()))
			.thenReturn(new HkSgAirFeesBreakdown());

		assertNotNull(service.calculateAirFee(new HkSgAirFeesInput()));
	}

	@Test
	public void shouldReturnNettCost() {

		when(nettCostCalculator.calculateFee(anyObject(), anyObject()))
			.thenReturn(new HkSgAirFeesBreakdown());
		assertNotNull(service.calculateNettCost(new NettCostInput()));
	}

	@Test
	public void shouldReturnIndiaAirFeesBreakdown() {

		when(tfFactory.getCalculator(anyInt()))
			.thenReturn(tfCalculator);

		when(tfCalculator.calculate(anyObject(), anyObject(), anyObject(),
				anyObject(), anyObject()))
			.thenReturn(new InAirFeesBreakdown());

		when(airlineRuleService.getAirlineRule(anyString())).thenReturn(new AirlineRule());
		when(airportService.getAirport(anyString())).thenReturn(new Airport());

		Client client = new Client();
		client.setPricingId(20);
		when(clientService.getClient(anyString()))
				.thenReturn(client);

		InAirFeesInput input = new InAirFeesInput();
		input.setCountryCode(Country.INDIA.getCode());

		assertNotNull(service.calculateAirFee(input));
	}

	@Test
	public void shouldReturnVisaFees() {
		when(visaFeesCalculator.calculate(anyObject(), anyObject())).thenReturn(new VisaFeesBreakdown());

		VisaFeesInput input = new VisaFeesInput();
		input.setCountryCode(Country.HONG_KONG.getCode());

		assertNotNull(service.calculateVisaFees(input));

	}

	@Test
	public void shouldReturnNonAirFeeIndia() {
		when(inNonAirFeeCalculator.calculate(anyObject(), anyObject(), anyObject())).thenReturn(new NonAirFeesBreakdown());

		Client client = new Client();
		client.setPricingId(20);
		client.setStandardMfProduct(false);
		when(clientService.getClient(anyString())).thenReturn(client);

		InNonAirFeesInput input = new InNonAirFeesInput();
		input.setCountryCode(Country.INDIA.getCode());

		assertNotNull(service.calculateNonAirFee(input));
	}

	@Test
	public void shouldReturnNonAirFeeHkSg() {
		when(hkSgNonAirFeeCalculator.calculate(anyObject(), anyObject())).thenReturn(new NonAirFeesBreakdown());
		
		Client client = new Client();
		client.setPricingId(20);
		client.setStandardMfProduct(false);
		when(clientService.getClient(anyString())).thenReturn(client);

		HkSgNonAirFeesInput input = new HkSgNonAirFeesInput();
		input.setCountryCode(Country.HONG_KONG.getCode());
		
		assertNotNull(service.calculateNonAirFee(input));
	}
}

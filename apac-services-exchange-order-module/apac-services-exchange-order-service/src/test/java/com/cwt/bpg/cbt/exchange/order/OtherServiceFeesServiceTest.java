package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.FeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class OtherServiceFeesServiceTest {

	@Mock
	private OtherServiceCalculatorFactory factory;
	
	@Mock
	private TransactionFeeCalculatorFactory tfFactory;
	
	@Mock
	private Calculator<MiscFeesBreakdown, MiscFeesInput> miscFeeCalculator;
	
	@Mock
	private Calculator<AirFeesBreakdown, AirFeesInput> hkCalculator;
	
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
	
	@InjectMocks
	private OtherServiceFeesService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnFeesBreakdown() {
		
		when(miscFeeCalculator.calculate(anyObject(), anyObject()))
			.thenReturn(new MiscFeesBreakdown());
		assertNotNull(service.calculateMiscFee(new MiscFeesInput()));
	}
	
	@Test
	public void shouldReturnAirFeesBreakdown() {
		
		when(factory.getCalculator(anyString()))
			.thenReturn(hkCalculator);
		
		when(hkCalculator.calculate(anyObject(), anyObject()))
			.thenReturn(new AirFeesBreakdown());
		
		assertNotNull(service.calculateAirFee(new AirFeesInput()));
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
				anyObject()))
			.thenReturn(new FeesBreakdown());
		
		when(airlineRuleService.getAirlineRule(anyString())).thenReturn(new AirlineRule());
		when(airportService.getAirport(anyString())).thenReturn(new Airport());
		
		Client client = new Client();
		client.setPricingId(20);
		when(clientService.getClient(anyString()))
				.thenReturn(client);
		
		TransactionFeesInput input = new TransactionFeesInput();
		input.setCountryCode(Country.INDIA.getCode());
		
		assertNotNull(service.calculateAirFee(input));
	}
}

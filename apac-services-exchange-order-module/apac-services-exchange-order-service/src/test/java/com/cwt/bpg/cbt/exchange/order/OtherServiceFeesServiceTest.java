package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.calculator.Calculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NettCostInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesInput;

public class OtherServiceFeesServiceTest {

	@Mock
	private OtherServiceCalculatorFactory factory;
	
	@Mock
	private Calculator miscFeeCalculator;
	
	@Mock
	private Calculator hkCalculator;
	
	@Mock
	private NettCostCalculator nettCostCalculator;
	
	@Mock
	private ExchangeOrderService orderService;
	
	@InjectMocks
	private OtherServiceFeesService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnFeesBreakdown() {
		
		Mockito.when(miscFeeCalculator.calculate(Mockito.anyObject(), Mockito.anyObject()))
			.thenReturn(new FeesBreakdown());
		assertNotNull(service.calculateMiscFee(new FeesInput()));
	}
	
	@Test
	public void shouldReturnAirFeesBreakdown() {
		
		Mockito.when(factory.getCalculator(Mockito.anyString()))
			.thenReturn(hkCalculator);
		
		Mockito.when(hkCalculator.calculate(Mockito.anyObject(), Mockito.anyObject()))
			.thenReturn(new FeesBreakdown());
		
		assertNotNull(service.calculateAirFee(new FeesInput()));
	}

	@Test
	public void shouldReturnNettCost() {
		
		Mockito.when(nettCostCalculator.calculateFee(Mockito.anyObject(), Mockito.anyObject()))
			.thenReturn(new AirFeesBreakdown());
		assertNotNull(service.calculateNettCost(new NettCostInput()));
	}
}

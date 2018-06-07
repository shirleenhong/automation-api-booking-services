package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class GrossFareCalculatorTest {

	private GrossFareCalculator grossFareCalc = new GrossFareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalTaxes;
	private BigDecimal totalGst;
	private BigDecimal expectedValue;
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(5);
		totalTaxes = new BigDecimal(4);
		totalGst = new BigDecimal(1);
		expectedValue = new BigDecimal(10);
		input = new IndiaAirFeesInput();
		breakdown = new IndiaAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);
		
		assertNotNull(grossFareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeSum() {
		input.setBaseFare(baseFare);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);
		
		BigDecimal actualTotalFee = grossFareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedValue, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalTaxes(null);
		breakdown.setTotalGst(null);
		
		assertNotNull(grossFareCalc.getTotalFee(input, breakdown));
	}
	
}

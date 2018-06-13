package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;

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
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(500);
		totalTaxes = new BigDecimal(4);
		totalGst = new BigDecimal(2);
		input = new IndiaAirFeesInput();
		breakdown = new IndiaAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);
		
		assertEquals(new BigDecimal(506), grossFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO));

	}

	@Test
	public void shouldGetTotalFeeSum() {
		input.setBaseFare(baseFare);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);
		
		BigDecimal actualTotalFee = grossFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO);
		assertEquals(new BigDecimal(506), actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalTaxes(null);
		breakdown.setTotalGst(null);
		
		assertEquals(BigDecimal.ZERO, grossFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO));
	}
	
}

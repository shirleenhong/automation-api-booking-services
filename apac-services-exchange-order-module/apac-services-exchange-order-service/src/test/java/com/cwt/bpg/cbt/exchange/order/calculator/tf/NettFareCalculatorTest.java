package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

public class NettFareCalculatorTest {

	private NettFareCalculator netFareCalc = new NettFareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalIataCommission;
	private BigDecimal expectedPositiveValue;
	private BigDecimal expectedNegativeValue;
	private BigDecimal returnableOr;
	private InAirFeesInput input;
	private InAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(5);
		totalIataCommission = new BigDecimal(4);
		expectedPositiveValue = new BigDecimal(1);
		returnableOr = new BigDecimal(0);
		expectedNegativeValue = new BigDecimal(-1);
		input = new InAirFeesInput();
		breakdown = new InAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalIataCommission(totalIataCommission);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		assertNotNull(netFareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(baseFare);
		breakdown.setTotalIataCommission(totalIataCommission);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedPositiveValue, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalIataCommission(null);
		breakdown.setTotalOverheadCommission(null);
		
		assertNotNull(netFareCalc.getTotalFee(input, breakdown));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(totalIataCommission);
		breakdown.setTotalIataCommission(baseFare);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedNegativeValue, actualTotalFee);
	}
}

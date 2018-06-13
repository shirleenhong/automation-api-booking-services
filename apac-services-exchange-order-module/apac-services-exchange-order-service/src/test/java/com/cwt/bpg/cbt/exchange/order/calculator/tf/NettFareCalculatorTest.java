package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class NettFareCalculatorTest {

	private NettFareCalculator netFareCalc = new NettFareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalAirlineCommission;
	private BigDecimal returnableOr;
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(500);
		totalAirlineCommission = new BigDecimal(4);
		returnableOr = new BigDecimal(0);
		input = new IndiaAirFeesInput();
		breakdown = new IndiaAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		assertEquals(new BigDecimal(496), netFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO);
		assertEquals(new BigDecimal(496), actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalAirlineCommission(null);
		breakdown.setTotalOverheadCommission(null);
		
		assertEquals(new BigDecimal(0), (netFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO)));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(totalAirlineCommission);
		breakdown.setTotalAirlineCommission(baseFare);
		breakdown.setTotalOverheadCommission(returnableOr);
		
		BigDecimal actualTotalFee = netFareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO);
		assertEquals(new BigDecimal(-496), actualTotalFee);
	}
}

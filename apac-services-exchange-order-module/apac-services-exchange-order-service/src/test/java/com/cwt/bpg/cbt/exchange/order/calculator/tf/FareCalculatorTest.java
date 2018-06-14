package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class FareCalculatorTest {

	private FareCalculator fareCalc = new FareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalAirlineCommission;
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(500);
		totalAirlineCommission = new BigDecimal(40);
		input = new IndiaAirFeesInput();
		breakdown = new IndiaAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		
		assertEquals(new BigDecimal(460), fareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO);
		assertEquals(new BigDecimal(460), actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalAirlineCommission(null);
		
		assertEquals(new BigDecimal(0), fareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(totalAirlineCommission);
		breakdown.setTotalAirlineCommission(baseFare);

		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown, BigDecimal.ZERO);
		assertEquals(new BigDecimal(-460), actualTotalFee);
	}
}

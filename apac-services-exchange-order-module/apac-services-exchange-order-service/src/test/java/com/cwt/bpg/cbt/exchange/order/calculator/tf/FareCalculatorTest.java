package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class FareCalculatorTest {

	private FareCalculator fareCalc = new FareCalculator();
	
	private BigDecimal baseFare;
	private BigDecimal totalAirlineCommission;
	private BigDecimal expectedPositiveValue;
	private BigDecimal expectedNegativeValue;
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		baseFare = new BigDecimal(5);
		totalAirlineCommission = new BigDecimal(4);
		expectedPositiveValue = new BigDecimal(1);
		expectedNegativeValue = new BigDecimal(-1);
		input = new IndiaAirFeesInput();
		breakdown = new IndiaAirFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeDifference() {
		input.setBaseFare(baseFare);
		breakdown.setTotalAirlineCommission(totalAirlineCommission);
		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedPositiveValue, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalAirlineCommission(null);
		
		assertNotNull(fareCalc.getTotalFee(input, breakdown));
	}
	
	@Test
	public void shouldGetTotalFeeNegativeDiff() {
		input.setBaseFare(totalAirlineCommission);
		breakdown.setTotalAirlineCommission(baseFare);

		
		BigDecimal actualTotalFee = fareCalc.getTotalFee(input, breakdown);
		assertEquals(expectedNegativeValue, actualTotalFee);
	}
}

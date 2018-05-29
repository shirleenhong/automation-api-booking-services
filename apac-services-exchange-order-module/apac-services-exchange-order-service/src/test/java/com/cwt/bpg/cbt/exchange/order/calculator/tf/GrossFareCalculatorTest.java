package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class GrossFareCalculatorTest {

	private GrossFareCalculator grossFareCalc = new GrossFareCalculator();
	
	private BigDecimal valFive;
	private BigDecimal valFour;
	private BigDecimal valOne;
	private BigDecimal valTen;
	private TransactionFeesInput input;
	private TransactionFeesBreakdown breakdown;
	
	@Before
	public void setup() {
		valFive = new BigDecimal(5);
		valFour = new BigDecimal(4);
		valOne = new BigDecimal(1);
		valTen = new BigDecimal(10);
		input = new TransactionFeesInput();
		breakdown = new TransactionFeesBreakdown();
	}

	@Test
	public void shouldGetTotalFeeValidParams() {
		input.setBaseFare(valFive);
		breakdown.setTotalTaxes(valFour);
		breakdown.setTotalGst(valOne);
		
		assertNotNull(grossFareCalc.getTotalFee(input, breakdown));

	}

	@Test
	public void shouldGetTotalFeeSum() {
		input.setBaseFare(valFive);
		breakdown.setTotalTaxes(valFour);
		breakdown.setTotalGst(valOne);
		
		BigDecimal actualTotalFee = grossFareCalc.getTotalFee(input, breakdown);
		assertEquals(valTen, actualTotalFee);
	}
	
	@Test
	public void shouldGetTotalFeeNullValues() {
		input.setBaseFare(null);
		breakdown.setTotalTaxes(null);
		breakdown.setTotalGst(null);
		
		assertNotNull(grossFareCalc.getTotalFee(input, breakdown));
	}
	
}

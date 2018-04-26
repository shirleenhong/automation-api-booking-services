package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class SgAirCalculatorTest {
	
	private Calculator calculator = new SgAirCalculator();
	
	@Test
	public void shouldCalculate() {
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(null, null);
		assertNotNull(airFeesBreakdown);
	}
	
	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeNotCT() {
		AirFeesInput input = new AirFeesInput();
		input.setSellingPrice(bigDecimal("500"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		input.setNettFare(bigDecimal("300"));
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		assertNotNull(afb);
		assertEquals(afb.getTotalSellingFare(),bigDecimal("417"));
	}
	
	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeCT() {
		
		AirFeesInput input = new AirFeesInput();
		input.setProductType("CT");
		input.setNettFare(bigDecimal("300"));
		input.setDiscountByPercent(false);
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		assertNotNull(afb);
		
	}
	
	@Test
	public void shouldCalculateWithApplyFormula() {
		
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(true);
		input.setCommissionPct(Double.parseDouble("20"));
		input.setDiscountByPercent(true);
		input.setDiscountPct(Double.parseDouble("15"));

		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		assertNotNull(afb);
		assertEquals(BigDecimal.ZERO, afb.getDiscount());
		assertEquals(bigDecimal("60.00"), afb.getCommission());
		assertEquals(bigDecimal("240.00"), afb.getNettCostInEO());
		assertEquals(BigDecimal.ZERO, afb.getMerchantFee());
		assertEquals(bigDecimal("237"), afb.getTotalSellingFare());
		
	}
	
	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMF() {
		
		AirFeesInput input = new AirFeesInput();
		MerchantFee merchantFee = new MerchantFee();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(false);
		input.setCommission(bigDecimal("15"));
		input.setDiscountByPercent(true);
		input.setDiscountPct(Double.parseDouble("15"));
		input.setProductType("CT");
		input.setClientType("TF");
		input.setFopType("CX");
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(true);
		merchantFee.setMerchantFeePct(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		
		assertNotNull(afb);
		assertEquals(bigDecimal("45.00"), afb.getDiscount());
		assertEquals(bigDecimal("15"), afb.getCommission());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("91.75"), afb.getMerchantFee());
		assertEquals(bigDecimal("383.75"), afb.getTotalSellingFare());
		
	}
	
	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCX() {
		
		AirFeesInput input = new AirFeesInput();
		MerchantFee merchantFee = new MerchantFee();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(false);
		input.setCommission(bigDecimal("15"));
		input.setDiscountByPercent(true);
		input.setDiscountPct(Double.parseDouble("15"));
		input.setProductType("CT");
		input.setClientType("DU");
		input.setFopType("CX");
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePct(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		
		assertNotNull(afb);
		assertEquals(bigDecimal("45.00"), afb.getDiscount());
		assertEquals(bigDecimal("15"), afb.getCommission());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("73.00"), afb.getMerchantFee());
		assertEquals(bigDecimal("365.00"), afb.getTotalSellingFare());
		
	}
	
	private BigDecimal bigDecimal(String amount) {
		if(amount == null || amount.equals("")) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(amount);
	}

}


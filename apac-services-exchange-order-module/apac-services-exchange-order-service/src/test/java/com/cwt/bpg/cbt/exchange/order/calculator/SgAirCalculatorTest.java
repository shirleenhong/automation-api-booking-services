package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class SgAirCalculatorTest {
	
	private Calculator calculator = new SgAirCalculator();
	
	@Mock
	private ScaleConfig scaleConfig;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
				
		Mockito.when(scaleConfig.getScale(Mockito.eq("SG"))).thenReturn(2);
		Mockito.when(scaleConfig.getScale(Mockito.eq("HK"))).thenReturn(0);
		
		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
	}
	
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
		input.setCountryCode("HK");
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		
		assertNull(afb.getCommission());
		assertNull(afb.getDiscount());
		assertEquals(bigDecimal("0"), afb.getMerchantFee());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("417"), afb.getTotalSellingFare());
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
		
		assertNull(afb.getCommission());
		assertNull(afb.getDiscount());
		assertEquals(bigDecimal("0"), afb.getMerchantFee());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("217"), afb.getTotalSellingFare());
	}
	
	@Test
	public void shouldCalculateNotDiscountByPercent() {
		
		AirFeesInput input = new AirFeesInput();
		input.setProductType("CX");
		input.setApplyFormula(true);
		input.setDiscountByPercent(false);
		input.setNettFare(bigDecimal("300"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		
		assertEquals(bigDecimal("15"), afb.getCommission());
		assertEquals(bigDecimal("150"), afb.getDiscount());
		assertEquals(bigDecimal("0"), afb.getMerchantFee());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("-113"), afb.getTotalSellingFare());
		
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
		input.setCountryCode("SG");
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		
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
		input.setCountryCode("SG");
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);

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
		input.setCountryCode("SG");
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		
		assertEquals(bigDecimal("45.00"), afb.getDiscount());
		assertEquals(bigDecimal("15"), afb.getCommission());
		assertEquals(bigDecimal("285"), afb.getNettCostInEO());
		assertEquals(bigDecimal("73.00"), afb.getMerchantFee());
		assertEquals(bigDecimal("365.00"), afb.getTotalSellingFare());
		
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMerFee() {
		
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
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePct(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		input.setCountryCode("SG");
		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		
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

	@Test
	public void shouldCalculateNoMerchantFee() {
		
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCwtAbsorb(false);
		input.setFopType("CX");
		input.setMerchantFeeWaive(false);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(true);
		input.setCommissionPct(Double.parseDouble("20"));
		input.setDiscountByPercent(true);
		input.setDiscountPct(Double.parseDouble("15"));
		input.setCountryCode("SG");

		
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculateFee(input, null);
		
		assertEquals(bigDecimal("0.00"),afb.getMerchantFee());
		assertEquals(BigDecimal.ZERO, afb.getDiscount());
		assertEquals(bigDecimal("60.00"), afb.getCommission());
		assertEquals(bigDecimal("240.00"), afb.getNettCostInEO());
		assertEquals(bigDecimal("237.00"), afb.getTotalSellingFare());		
	}
}


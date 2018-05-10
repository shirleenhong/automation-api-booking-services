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

public class HkAirCalculatorTest {
	
	private Calculator calculator = new HkAirCalculator();
	
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
	public void shouldHandleNullInput() {
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(null, null);
		assertNotNull(airFeesBreakdown);
		assertNull(airFeesBreakdown.getNettCost());
		assertNull(airFeesBreakdown.getTotalSellingFare());
		assertNull(airFeesBreakdown.getSellingPrice());

	}

	@Test
	public void shouldHandleNullFieldsFromInput() {
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(new AirFeesInput(), null);
		assertNotNull(airFeesBreakdown);
		assertNotNull(airFeesBreakdown.getNettCost());
		assertNotNull(airFeesBreakdown.getTotalSellingFare());
		assertNull(airFeesBreakdown.getSellingPrice());
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeDU() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPct(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2510D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, airFeesBreakdown.getDiscount());
		assertEquals(12510D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(12510D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeTP() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPct(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("TP");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(0D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, airFeesBreakdown.getDiscount());
		assertEquals(12510D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(12510D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeDB() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPct(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DB");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2500D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12500D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, airFeesBreakdown.getDiscount());
		assertEquals(12500D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(12500D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionNotByPercentageClientTypeAny() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setCommissionPct(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(0D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, airFeesBreakdown.getDiscount());
		assertEquals(10000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(10000D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeDU() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPct(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(2400D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(9600D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(9600D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeMN() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPct(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("MN");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
				
		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(12000D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeMG() {
		MerchantFee merchantFee = new MerchantFee();
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPct(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("MG");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(airFeesBreakdown.getMerchantFee());
		assertEquals(12000D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
		
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTF() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(2800D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(16800D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTFWithTransactionFeeIncluded() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		merchantFee.setIncludeTransactionFee(true);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(3800D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(17800D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(1000D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(15000D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
		
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeNonTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("MN");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(400D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(14400D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeNonTFAndUATPAndTaxesGreaterThanNettFare() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(1000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("MN");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(3000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(5000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(400D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(5400D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(1000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldAssignZeroToMerchantFeeForNegative() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(-20000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("MN");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(-20000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(-10000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(-8000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(0D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(-8000D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

}

package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesInput;
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(null, null);
		assertNotNull(HkSgAirFeesBreakdown);
		assertNull(HkSgAirFeesBreakdown.getNettCost());
		assertNull(HkSgAirFeesBreakdown.getTotalSellingFare());
		assertNull(HkSgAirFeesBreakdown.getSellingPrice());

	}

	@Test
	public void shouldHandleNullFieldsFromInput() {
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(new HkSgAirFeesInput(), null);
		assertNotNull(HkSgAirFeesBreakdown);
		assertNotNull(HkSgAirFeesBreakdown.getNettCost());
		assertNotNull(HkSgAirFeesBreakdown.getTotalSellingFare());
		assertNull(HkSgAirFeesBreakdown.getSellingPrice());
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeDU() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPercent(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2510D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, HkSgAirFeesBreakdown.getDiscount());
		assertEquals(12510D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(12510D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeTP() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPercent(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("TP");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(0D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, HkSgAirFeesBreakdown.getDiscount());
		assertEquals(12510D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(12510D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionByPercentageClientTypeDB() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(true);
		input.setCommissionPercent(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DB");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2500D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12500D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, HkSgAirFeesBreakdown.getDiscount());
		assertEquals(12500D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(12500D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithComissionNotByPercentageClientTypeAny() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setCommissionPercent(20D);
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(0D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(BigDecimal.ZERO, HkSgAirFeesBreakdown.getDiscount());
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(10000D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeDU() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPercent(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("DU");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(2400D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(9600D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(9600D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeMN() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPercent(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("MN");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
				
		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(12000D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateWithDiscountByPercentageClientTypeMG() {
		MerchantFee merchantFee = new MerchantFee();
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(true);
		input.setDiscountPercent(20D);
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setClientType("MG");
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertNull(HkSgAirFeesBreakdown.getMerchantFee());
		assertEquals(12000D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
		
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTF() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(2800D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(16800D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTFWithTransactionFeeIncluded() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		merchantFee.setIncludeTransactionFee(true);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);
		
		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(3800D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(17800D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(1000D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(15000D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
		
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeNonTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(14000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(400D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(14400D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeNonTFAndUATPAndTaxesGreaterThanNettFare() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(2000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(3000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(5000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(400D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(5400D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(1000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

	@Test
	public void shouldAssignZeroToMerchantFeeForNegative() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(20D);
		HkSgAirFeesInput input = new HkSgAirFeesInput();
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
		HkSgAirFeesBreakdown HkSgAirFeesBreakdown = (HkSgAirFeesBreakdown) calculator.calculate(input, merchantFee);

		assertEquals(-20000D, HkSgAirFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(-10000D, HkSgAirFeesBreakdown.getSellingPrice().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getDiscount().doubleValue(), 0D);
		assertEquals(-8000D, HkSgAirFeesBreakdown.getNettFare().doubleValue(), 0D);
		assertEquals(0D, HkSgAirFeesBreakdown.getMerchantFee().doubleValue(), 0D);
		assertEquals(-8000D, HkSgAirFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
		assertEquals(10000D, HkSgAirFeesBreakdown.getNettCost().doubleValue(), 0D);
	}

}

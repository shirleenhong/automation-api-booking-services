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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(null, null);
		assertNotNull(airFeesBreakdown);
		assertNull(airFeesBreakdown.getNettCostInEO());
		assertNull(airFeesBreakdown.getTotalSellingFare());
		assertNull(airFeesBreakdown.getSellingPrice());

	}

	@Test
	public void shouldHandleNullFieldsFromInput() {
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(new AirFeesInput(), null);
		assertNotNull(airFeesBreakdown);
		assertNotNull(airFeesBreakdown.getNettCostInEO());
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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(2510D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);

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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(0D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12510D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);

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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(2500D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(12500D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);

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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(0D, airFeesBreakdown.getCommission().doubleValue(), 0D);
		assertEquals(10000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(10400D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
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
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(0D, airFeesBreakdown.getDiscount().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTF() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(true);
		input.setMerchantFeeWaive(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(2800D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
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
		input.setCwtAbsorb(true);
		input.setMerchantFeeWaive(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(3800D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeNonTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(true);
		input.setMerchantFeeWaive(true);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("TF");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(1000D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
	}

	@Test
	public void shouldCalculateMerchantFeeClientTypeTFAndUATP() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(20D);
		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCommissionByPercent(false);
		input.setDiscountByPercent(false);
		input.setCwtAbsorb(true);
		input.setMerchantFeeWaive(true);
		input.setUatp(true);
		input.setFopType("CX");
		input.setCommission(new BigDecimal(2000));
		input.setNettFare(new BigDecimal(10000));
		input.setTransactionFee(new BigDecimal(5000));
		input.setTax1(new BigDecimal(1000));
		input.setTax2(new BigDecimal(1000));
		input.setClientType("MN");
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator.calculateFee(input, merchantFee);
		assertEquals(400D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
	}

}

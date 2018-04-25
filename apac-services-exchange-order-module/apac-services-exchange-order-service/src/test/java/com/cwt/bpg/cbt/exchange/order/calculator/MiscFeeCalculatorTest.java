package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.MiscFeesInput;

public class MiscFeeCalculatorTest {

	private MiscFeeCalculator calculator = new MiscFeeCalculator();
	private MerchantFee merchantFee;
	
	@Before
	public void setup() {
		merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(6D);
	}

	@Test
	public void shouldCalculateFees2DScale() {

		MiscFeesInput input = new MiscFeesInput();

		input.setFopType("CX");
		input.setCountryCode("SG");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);
		input.setNettCost(new BigDecimal(1528.27));

		FeesBreakdown result = calculator.calculateFee(input, merchantFee);

		assertNull(result.getCommission());
		assertEquals(round(new BigDecimal(60.03), 2), result.getGstAmount());
		assertEquals(round(new BigDecimal(75.63), 2), result.getMerchantFee());
		assertEquals(round(new BigDecimal(76.4135), 2), result.getNettCostGst());
		assertEquals(round(new BigDecimal(1272.53), 2), result.getSellingPriceInDi());
	}

	private BigDecimal round(BigDecimal value, int scale) {
		return value.setScale(scale, RoundingMode.HALF_UP);
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(0, RoundingMode.HALF_UP);
	}

	@Test
	public void shouldCalculateFees0DScale() {

		MiscFeesInput input = new MiscFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1500.50));
		input.setGstPercent(5D);
		input.setNettCost(new BigDecimal(1228.27));
		
		FeesBreakdown result = calculator.calculateFee(input, merchantFee);

		assertEquals(round(new BigDecimal(362.68)), result.getCommission());
		assertEquals(round(new BigDecimal(75.025)), result.getGstAmount());
		assertEquals(round(new BigDecimal(94.5315)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(61.4135)), result.getNettCostGst());
		assertEquals(round(new BigDecimal(1590.95)), result.getSellingPriceInDi());
	}

	@Test
	public void shouldCalculateFeesFlagsTrue() {
		MiscFeesInput input = new MiscFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);
		input.setGstAbsorb(true);
		input.setMerchantFeeAbsorb(true);
		input.setNettCost(new BigDecimal(1528.27));
		
		FeesBreakdown result = calculator.calculateFee(input, merchantFee);

		assertNull(result.getCommission());
		assertNull(result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertNull(result.getNettCostGst());
		assertEquals(round(new BigDecimal(1143.33)), result.getSellingPriceInDi());
	}

	@Test
	public void shouldNotFailOnNullInput() {
		FeesBreakdown result = calculator.calculateFee(null, null);

		assertEquals(BigDecimal.ZERO, result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		assertEquals(BigDecimal.ZERO, result.getNettCostGst());
		assertEquals(BigDecimal.ZERO, result.getSellingPriceInDi());
	}

	@Test
	public void shouldNotFailOnEmptyInput() {
		
		FeesBreakdown result = calculator.calculateFee(new MiscFeesInput(), null);

		assertNull(result.getCommission());
		assertNull(result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertNull(result.getNettCostGst());
		assertNull(result.getSellingPriceInDi());
	}
}

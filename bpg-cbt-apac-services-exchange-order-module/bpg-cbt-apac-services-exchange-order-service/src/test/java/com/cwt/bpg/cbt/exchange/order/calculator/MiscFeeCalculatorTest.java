package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class MiscFeeCalculatorTest {

	MiscFeeCalculator calculator = new MiscFeeCalculator();

	@Test
	public void shouldCalculateFees2DScale() {

		OtherServiceFeesInput input = new OtherServiceFeesInput();

		input.setFopType("CX");
		input.setCountryCode("SG");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);

		FeesBreakdown result = calculator.calMiscFee(input, 6D, new BigDecimal(1528.27));

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

		OtherServiceFeesInput input = new OtherServiceFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1500.50));
		input.setGstPercent(5D);

		FeesBreakdown result = calculator.calMiscFee(input, 6D, new BigDecimal(1228.27));

		assertEquals(round(new BigDecimal(362.68)), result.getCommission());
		assertEquals(round(new BigDecimal(75.025)), result.getGstAmount());
		assertEquals(round(new BigDecimal(94.5315)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(61.4135)), result.getNettCostGst());
		assertEquals(round(new BigDecimal(1590.95)), result.getSellingPriceInDi());
	}

	@Test
	public void shouldCalculateFeesFlagsTrue() {

		OtherServiceFeesInput input = new OtherServiceFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);
		input.setGstAbsorb(true);
		input.setMerchantFeeAbsorb(true);

		FeesBreakdown result = calculator.calMiscFee(input, 6D, new BigDecimal(1528.27));

		assertNull(result.getCommission());
		assertNull(result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertNull(result.getNettCostGst());
		assertEquals(round(new BigDecimal(1143.33)), result.getSellingPriceInDi());
	}

	@Test
	public void shouldNotFailOnNullInput() {
		FeesBreakdown result = calculator.calMiscFee(null, null, null);

		assertEquals(BigDecimal.ZERO, result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		assertEquals(BigDecimal.ZERO, result.getNettCostGst());
		assertEquals(BigDecimal.ZERO, result.getSellingPriceInDi());
	}

	@Test
	public void shouldNotFailOnEmptyInput() {
		FeesBreakdown result = calculator.calMiscFee(new OtherServiceFeesInput(), null, null);

		assertNull(result.getCommission());
		assertNull(result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertNull(result.getNettCostGst());
		assertNull(result.getSellingPriceInDi());
	}
}

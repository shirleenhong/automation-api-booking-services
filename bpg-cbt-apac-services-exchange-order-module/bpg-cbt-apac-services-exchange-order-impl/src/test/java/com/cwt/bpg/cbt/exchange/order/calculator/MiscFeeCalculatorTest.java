package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Ignore
public class MiscFeeCalculatorTest {

	MiscFeeCalculator calculator = new MiscFeeCalculator();
	
	@Test
	public void shouldCalculateFees2DScale() {
		
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
		input.setFopType("CX");
		input.setCountryCode("SG");
		input.setSellingPrice(new BigDecimal(1200.50));		
		input.setGstPercent(5D);
		
		FeesBreakdown result = calculator
				.calMiscFee(input, 
						6D, 
						new BigDecimal(1528.27));
		
		assertEquals(BigDecimal.ZERO, result.getCommission());
		//assertEquals(new BigDecimal(60.025), result.getGstAmount());
		assertEquals(new BigDecimal(75.63), result.getMerchantFee());
		assertEquals(new BigDecimal(76.4135), result.getNettCostGst());
		assertEquals(new BigDecimal(1336.155), result.getSellingPriceInDi());
	}

	@Test
	public void shouldCalculateFees0DScale() {
		
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1200.50));		
		input.setGstPercent(5D);
		
		FeesBreakdown result = calculator
				.calMiscFee(input, 
						6D, 
						new BigDecimal(1528.27));
		
		assertEquals(BigDecimal.ZERO, result.getCommission());
		assertEquals(new BigDecimal(60.025), result.getGstAmount());
		assertEquals(new BigDecimal(75.63), result.getMerchantFee());
		assertEquals(new BigDecimal(76.4135), result.getNettCostGst());
		assertEquals(new BigDecimal(1336.155), result.getSellingPriceInDi());
	}
	
	@Test
	public void shouldCalculateFeesFlagsTrue() {
		
		OtherServiceFeesInput input = new OtherServiceFeesInput();
		
		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1200.50));		
		input.setGstPercent(5D);
		input.setGstAbsorb(true);
		
		FeesBreakdown result = calculator
				.calMiscFee(input, 
						6D, 
						new BigDecimal(1528.27));
		
		assertEquals(new BigDecimal(1143.33), result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		assertEquals(BigDecimal.ZERO, result.getNettCostGst());
		assertEquals(new BigDecimal(1143.33), result.getSellingPriceInDi());
	}
	
	
	@Test
	public void shouldNotFailOnNullInput() {
		FeesBreakdown result = calculator
				.calMiscFee(null, null, null);
		
		assertEquals(BigDecimal.ZERO, result.getCommission());
		assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		assertEquals(BigDecimal.ZERO, result.getNettCostGst());
		assertEquals(BigDecimal.ZERO, result.getSellingPriceInDi());
	}
	
	@Test
	public void shouldNotFailOnEmptyInput() {
		FeesBreakdown result = calculator
				.calMiscFee(new OtherServiceFeesInput(), null, null);
		
		assertEquals(BigDecimal.ZERO, result.getCommission());
		//assertEquals(BigDecimal.ZERO, result.getGstAmount());
		assertEquals(BigDecimal.ZERO, result.getMerchantFee());
		//assertEquals(BigDecimal.ZERO, result.getNettCostGst());
		assertEquals(BigDecimal.ZERO, result.getSellingPriceInDi());
	}
}

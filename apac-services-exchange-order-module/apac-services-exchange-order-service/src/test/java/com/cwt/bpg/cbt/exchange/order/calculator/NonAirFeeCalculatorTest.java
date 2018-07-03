package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;

public class NonAirFeeCalculatorTest {
	
	@InjectMocks
	private NonAirFeeCalculator calculator = new NonAirFeeCalculator();
	
	@Mock
	private ScaleConfig scaleConfig;
	
	private MerchantFee merchantFee;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
				
		Mockito.when(scaleConfig.getScale(Mockito.eq("SG"))).thenReturn(2);
		Mockito.when(scaleConfig.getScale(Mockito.eq("HK"))).thenReturn(0);
		
		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
		
		merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePercent(6D);
	}

	@Test
	public void shouldCalculateFees2DScale() {

		NonAirFeesInput input = new NonAirFeesInput();

		input.setFopType("CX");
		input.setCountryCode("SG");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);
		input.setNettCost(new BigDecimal(1528.27));

		NonAirFeesBreakdown result = calculator.calculate(input, merchantFee);

		assertEquals(round(BigDecimal.ZERO, 2), result.getCommission());
		assertEquals(round(new BigDecimal(60.03), 2), result.getGstAmount());
		assertEquals(round(new BigDecimal(75.63), 2), result.getMerchantFee());
		assertEquals(round(new BigDecimal(76.4135), 2), result.getNettCostGst());
		assertEquals(round(new BigDecimal(1272.53), 2), result.getTotalSellingPrice());
		assertNull(result.getGrossSellingPrice());
	}
	
	@Test
	public void shouldCalculateFees0DScale() {

		NonAirFeesInput input = new NonAirFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1500.50));
		input.setGstPercent(5D);
		input.setNettCost(new BigDecimal(1228.27));
		
		NonAirFeesBreakdown result = calculator.calculate(input, merchantFee);

		assertEquals(round(new BigDecimal(362.68)), result.getCommission());
		assertEquals(round(new BigDecimal(75.025)), result.getGstAmount());
		assertEquals(round(new BigDecimal(94.5315)), result.getMerchantFee());
		assertEquals(round(new BigDecimal(61.4135)), result.getNettCostGst());
		assertEquals(round(new BigDecimal(1590.95)), result.getTotalSellingPrice());
		assertNull(result.getGrossSellingPrice());
	}

	@Test
	public void shouldCalculateFeesFlagsTrue() {
		NonAirFeesInput input = new NonAirFeesInput();

		input.setFopType("CX");
		input.setCountryCode("HK");
		input.setSellingPrice(new BigDecimal(1200.50));
		input.setGstPercent(5D);
		input.setGstAbsorb(true);
		input.setMerchantFeeAbsorb(true);
		input.setNettCost(new BigDecimal(1528.27));
		
		NonAirFeesBreakdown result = calculator.calculate(input, merchantFee);

		assertEquals(round(BigDecimal.ZERO), result.getCommission());
		assertNull(result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertNull(result.getNettCostGst());
		assertEquals(round(new BigDecimal(1143.33)), result.getTotalSellingPrice());
		assertNull(result.getGrossSellingPrice());
	}

	@Test
	public void shouldNotFailOnNullInput() {
		NonAirFeesBreakdown result = calculator.calculate(null, null);

		assertThat(result.getCommission(), is(nullValue(BigDecimal.class)));
		assertThat(result.getGstAmount(), is(nullValue(BigDecimal.class)));
		assertThat(result.getMerchantFee(), is(nullValue(BigDecimal.class)));
		assertThat(result.getNettCostGst(), is(nullValue(BigDecimal.class)));
		assertThat(result.getTotalSellingPrice(), is(nullValue(BigDecimal.class)));
		assertThat(result.getGrossSellingPrice(), is(nullValue(BigDecimal.class)));
	}

	@Test
	public void shouldNotFailOnEmptyInput() {
		NonAirFeesInput input = new NonAirFeesInput();
		input.setCountryCode("SG");
		NonAirFeesBreakdown result = calculator.calculate(input, null);

		assertEquals(round(BigDecimal.ZERO, 2), result.getCommission());
		assertEquals(round(BigDecimal.ZERO, 2), result.getGstAmount());
		assertNull(result.getMerchantFee());
		assertEquals(round(BigDecimal.ZERO, 2), result.getNettCostGst());
		assertEquals(round(BigDecimal.ZERO, 2), result.getTotalSellingPrice());
		assertNull(result.getGrossSellingPrice());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionWhenFopTypeIsCXAndMerchantFeeIsNull() {
		NonAirFeesInput input = new NonAirFeesInput();
		input.setCountryCode("SG");
		input.setFopType("CX");
        input.setSellingPrice(new BigDecimal(1200.50));
        input.setGstPercent(5D);
        input.setNettCost(new BigDecimal(1528.27));

		calculator.calculate(input, null);

	}


	@SuppressWarnings("SameParameterValue")
    private BigDecimal round(BigDecimal value, int scale) {
		return value.setScale(scale, RoundingMode.HALF_UP);
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale(0, RoundingMode.HALF_UP);
	}
}

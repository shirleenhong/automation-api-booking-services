package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;
import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;

public class BaseAndYqCalculatorTest {

	private BaseAndYqCalculator calculator = new BaseAndYqCalculator();
	IndiaAirFeesInput input = new IndiaAirFeesInput();
	IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();

	@Mock
	private ScaleConfig scaleConfig;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(scaleConfig.getScale(Mockito.eq("IN"))).thenReturn(2);
		
		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
		input.setCountryCode(Country.INDIA.getCode());
		input.setBaseFare(new BigDecimal(600));
		input.setYqTax(new BigDecimal(10));
		
		input.setAirlineCommission(new BigDecimal(9));
		input.setOverheadCommissionEnabled(true);
		input.setCityCode("AAE");
		input.setCommissionEnabled(true);
		input.setDiscountPercent(2D);
		input.setDiscountEnabled(true);
		input.setFeeOverride(true);
		input.setGstEnabled(true);
		input.setMarkupEnabled(true);
		input.setMerchantFeePercent(2D);
	}

	@Test
	public void getTotalFeeShouldReturnSumOfBaseFareAndYqTax() {

		BigDecimal expectedResult = input.getBaseFare().add(input.getYqTax());
		BigDecimal actualResult = calculator.getTotalFee(input, breakdown, input.getYqTax());

		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void shouldOverrideYqTax() {

		AirlineRule rule = new AirlineRule();
		rule.setIncludeYqCommission(false);
		Client client = new Client();
		client.setClientPricings(new ArrayList<>());

		IndiaAirFeesBreakdown result = calculator.calculate(input, rule, client, new Airport(), new IndiaProduct());
		
		assertNull(result.getCommission());
		assertEquals(new BigDecimal(600).setScale(2), result.getBaseAmount());
		assertNull(result.getGstPercent());
		assertEquals(new BigDecimal(0).setScale(2), result.getMerchantFeeOnTf());
		assertNull(result.getMerchantFeePercent());
		assertNull(result.getOt1Percent());
		assertNull(result.getOt2Percent());
		assertNull(result.getOverheadPercent());
		assertNull(result.getSubMerchantFeePercent());
		assertEquals(new BigDecimal(600).setScale(2), result.getTotalCharge());
		assertEquals(new BigDecimal(12.18).setScale(2, RoundingMode.HALF_UP), result.getTotalDiscount());
		assertEquals(new BigDecimal(0).setScale(2), result.getTotalGst());
		assertNull(result.getTotalMarkup());
		assertNull(result.getTotalOverheadCommission());
		assertEquals(new BigDecimal(600).setScale(2), result.getTotalSellFare());
		assertEquals(new BigDecimal(599.58).setScale(2, RoundingMode.HALF_UP), result.getTotalSellingFare());
		assertEquals(new BigDecimal(0).setScale(2), result.getTotalTaxes());
	}
}

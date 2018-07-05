package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

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
		IndiaAirProductInput product = new IndiaAirProductInput();
		product.setGstPercent(2d);
		product.setOt1Percent(1.5d);
		product.setOt2Percent(1.5d);
		input.setProduct(product);

		IndiaAirFeesBreakdown result = calculator.calculate(input, rule, client, new Airport(), new IndiaProduct());
		
		assertThat(result.getCommission(), is(nullValue()));
		assertThat(result.getBaseAmount().doubleValue(), is(equalTo(600d)));
		assertThat(result.getGstPercent(), is(nullValue()));
		assertThat(result.getMerchantFeeOnTf().doubleValue(), is(equalTo(0d)));
		assertThat(result.getMerchantFeePercent(), is(nullValue()));
		assertThat(result.getOt1Percent(), is(nullValue()));
		assertThat(result.getOt2Percent(), is(nullValue()));
		assertThat(result.getOverheadPercent(), is(nullValue()));
		assertThat(result.getSubMerchantFeePercent(), is(nullValue()));
		assertThat(result.getTotalCharge().doubleValue(), is(equalTo(642.60d)));
		assertThat(result.getTotalDiscount().doubleValue(), is(equalTo(0d)));
		assertThat(result.getTotalGst().doubleValue(), is(equalTo(30d)));
		assertThat(result.getTotalMarkup(), is(nullValue()));
		assertThat(result.getTotalOverheadCommission().doubleValue(), is(equalTo(0d)));
		assertThat(result.getTotalSellFare().doubleValue(), is(equalTo(600d)));
		assertThat(result.getTotalSellingFare().doubleValue(), is(equalTo(642.60d)));
		assertThat(result.getTotalTaxes().doubleValue(), is(equalTo(0d)));
	}
}


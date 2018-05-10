package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class VisaFeesCalculatorTest {

	@InjectMocks
	private VisaFeesCalculator calculator = new VisaFeesCalculator();

	@Mock
	private ScaleConfig scaleConfig;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(scaleConfig.getScale(Mockito.anyString())).thenReturn(0);
	}

	@Test
	public void shouldCalculateFeeWhenBothNettCostMFAndCwtHandlingMFAreUnchecked() {
		VisaFeesInput input = new VisaFeesInput();
		input.setNettCostMerchantFeeChecked(false);
		input.setCwtHandlingMerchantFeeChecked(false);
		input.setNettCost(new BigDecimal(150));
		input.setCwtHandling(new BigDecimal(100));
		input.setVendorHandling(new BigDecimal(50));

		VisaFeesBreakdown result = calculator.calculate(input, createMerchantFee());

		assertThat(result.getNettCostMerchantFee(), nullValue());
		assertThat(result.getCwtHandlingMerchantFee(), nullValue());
		assertThat(result.getCommission().doubleValue(), is(equalTo(100D)));
		assertThat(result.getSellingPrice().doubleValue(), is(equalTo(300D)));
		assertThat(result.getSellingPriceInDi().doubleValue(), is((equalTo(300D))));
	}

	@Test
	public void shouldCalculateFeeWhenNettCostMFIsChecked() {
		VisaFeesInput input = new VisaFeesInput();
		input.setNettCostMerchantFeeChecked(true);
		input.setCwtHandlingMerchantFeeChecked(false);
		input.setNettCost(new BigDecimal(150));
		input.setCwtHandling(new BigDecimal(100));
		input.setVendorHandling(new BigDecimal(50));

		VisaFeesBreakdown result = calculator.calculate(input, createMerchantFee());

		assertThat(result.getNettCostMerchantFee().doubleValue(), is(equalTo(3D)));
		assertThat(result.getCwtHandlingMerchantFee(), nullValue());
		assertThat(result.getCommission().doubleValue(), is(equalTo(103D)));
		assertThat(result.getSellingPrice().doubleValue(), is(equalTo(303D)));
		assertThat(result.getSellingPriceInDi().doubleValue(), is((equalTo(303D))));
	}

	@Test
	public void shouldCalculateFeeWhenCwtHandlingMFIsChecked() {
		VisaFeesInput input = new VisaFeesInput();
		input.setNettCostMerchantFeeChecked(false);
		input.setCwtHandlingMerchantFeeChecked(true);
		input.setNettCost(new BigDecimal(150));
		input.setCwtHandling(new BigDecimal(100));
		input.setVendorHandling(new BigDecimal(50));

		VisaFeesBreakdown result = calculator.calculate(input, createMerchantFee());

		assertThat(result.getNettCostMerchantFee(), nullValue());
		assertThat(result.getCwtHandlingMerchantFee().doubleValue(), is(equalTo(2D)));
		assertThat(result.getCommission().doubleValue(), is(equalTo(102D)));
		assertThat(result.getSellingPrice().doubleValue(), is(equalTo(302D)));
		assertThat(result.getSellingPriceInDi().doubleValue(), is((equalTo(302D))));
	}

	private MerchantFee createMerchantFee() {
		MerchantFee merchantFee = new MerchantFee();
		merchantFee.setMerchantFeePct(2D);
		return merchantFee;
	}
}

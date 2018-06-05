package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.HkSgAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class SgAirCalculatorTest {

	private Calculator calculator = new SgAirCalculator();

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
	public void shouldCalculate() {
		AirFeesBreakdown airFeesBreakdown = (AirFeesBreakdown) calculator
				.calculate(null, null);
		assertNotNull(airFeesBreakdown);
	}

	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeNotCT() {
		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setSellingPrice(bigDecimal("500"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		input.setNettFare(bigDecimal("300"));
		input.setCountryCode("HK");
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input, null);

		assertThat(afb.getCommission(), nullValue());
		assertThat(afb.getDiscount(), nullValue());
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(417D)));
	}

	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeCT() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setProductType("CT");
		input.setNettFare(bigDecimal("300"));
		input.setDiscountByPercent(false);
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input, null);

		assertThat(afb.getCommission(), nullValue());
		assertThat(afb.getDiscount(), nullValue());
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(217D)));
	}

	@Test
	public void shouldCalculateNotDiscountByPercent() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setProductType("CX");
		input.setApplyFormula(true);
		input.setDiscountByPercent(false);
		input.setNettFare(bigDecimal("300"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input, null);

		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getDiscount().doubleValue(), is(equalTo(150D)));
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(0D)));
	}

	@Test
	public void shouldCalculateWithApplyFormula() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(true);
		input.setCommissionPercent(Double.parseDouble("20"));
		input.setDiscountByPercent(true);
		input.setDiscountPercent(Double.parseDouble("15"));
		input.setCountryCode("SG");

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input, null);

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(0D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(60D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(240D)));
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(237D)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMF() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		MerchantFee merchantFee = new MerchantFee();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(false);
		input.setCommission(bigDecimal("15"));
		input.setDiscountByPercent(true);
		input.setDiscountPercent(Double.parseDouble("15"));
		input.setProductType("CT");
		input.setClientType("TF");
		input.setFopType("CX");
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(true);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		input.setCountryCode("SG");
		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input,
				merchantFee);

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(91.75D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(383.75)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCX() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		MerchantFee merchantFee = new MerchantFee();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(false);
		input.setCommission(bigDecimal("15"));
		input.setDiscountByPercent(true);
		input.setDiscountPercent(Double.parseDouble("15"));
		input.setProductType("CT");
		input.setClientType("DU");
		input.setFopType("CX");
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		input.setCountryCode("SG");

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input,
				merchantFee);

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(73D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(365D)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMerFee() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		MerchantFee merchantFee = new MerchantFee();
		input.setApplyFormula(true);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(false);
		input.setCommission(bigDecimal("15"));
		input.setDiscountByPercent(false);
		input.setDiscount(bigDecimal("45.00"));
		input.setProductType("CT");
		input.setClientType("TF");
		input.setFopType("CX");
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		input.setCountryCode("SG");

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input,
				merchantFee);

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(73D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(365D)));
	}

	private BigDecimal bigDecimal(String amount) {
		if (amount == null || amount.equals("")) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(amount);
	}

	@Test
	public void shouldCalculateNoMerchantFee() {

		HkSgAirFeesInput input = new HkSgAirFeesInput();
		input.setApplyFormula(true);
		input.setCwtAbsorb(false);
		input.setFopType("CX");
		input.setMerchantFeeWaive(false);
		input.setNettFare(bigDecimal("300"));
		input.setSellingPrice(bigDecimal("200"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommissionByPercent(true);
		input.setCommissionPercent(Double.parseDouble("20"));
		input.setDiscountByPercent(true);
		input.setDiscountPercent(Double.parseDouble("15"));
		input.setCountryCode("SG");

		AirFeesBreakdown afb = (AirFeesBreakdown) calculator.calculate(input, null);

		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(0D)));
		assertThat(afb.getDiscount().doubleValue(), is(equalTo(0D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(60D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(240D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(237D)));
	}
}

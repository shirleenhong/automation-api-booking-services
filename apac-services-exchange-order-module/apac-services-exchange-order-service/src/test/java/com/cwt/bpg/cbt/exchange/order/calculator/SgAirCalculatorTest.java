package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FopType;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class SgAirCalculatorTest {

	private Calculator<AirFeesBreakdown, AirFeesInput> calculator = new SgAirCalculator();

	@Mock
	private ScaleConfig scaleConfig;

	@Mock
	private RoundingConfig roundingConfig;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(scaleConfig.getScale(Mockito.eq("SG"))).thenReturn(2);
		Mockito.when(scaleConfig.getScale(Mockito.eq("HK"))).thenReturn(0);

		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);

		Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("nettFare"), Mockito.anyString())).thenReturn(RoundingMode.UP);
		Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("nettCost"), Mockito.anyString())).thenReturn(RoundingMode.UP);
		Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("merchantFee"), Mockito.anyString())).thenReturn(RoundingMode.UP);
		Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("totalSellingFare"), Mockito.anyString())).thenReturn(RoundingMode.UP);
		Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("commission"), Mockito.anyString())).thenReturn(RoundingMode.DOWN);

		ReflectionTestUtils.setField(calculator, "roundingConfig", roundingConfig);
	}

	@Test
	public void shouldCalculate() {
		AirFeesBreakdown AirFeesBreakdown = calculator
				.calculate(null, null, "SG");
		assertNotNull(AirFeesBreakdown);
	}

	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeNotCT() {
		AirFeesInput input = new AirFeesInput();
		input.setSellingPrice(bigDecimal("500"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		input.setNettFare(bigDecimal("300"));
		input.setFopType(FopType.CWT);
		AirFeesBreakdown afb = calculator.calculate(input, null, "HK");

		assertThat(afb.getCommission(), nullValue());
		assertThat(afb.getDiscount(), nullValue());
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(417D)));
	}

	@Test
	public void shouldCalculateNotApplyFormulaWithProductTypeCT() {

		AirFeesInput input = new AirFeesInput();
		input.setProductType("CT");
		input.setNettFare(bigDecimal("300"));
		input.setDiscountByPercent(false);
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		input.setFopType(FopType.CWT);

		AirFeesBreakdown afb = calculator.calculate(input, null, null);

		assertThat(afb.getCommission(), nullValue());
		assertThat(afb.getDiscount(), nullValue());
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(217D)));
	}

	@Test
	public void shouldCalculateNotDiscountByPercent() {

		AirFeesInput input = new AirFeesInput();
		input.setProductType("CX");
		input.setApplyFormula(true);
		input.setDiscountByPercent(false);
		input.setNettFare(bigDecimal("300"));
		input.setDiscount(bigDecimal("150"));
		input.setTax1(bigDecimal("23"));
		input.setTax2(bigDecimal("14"));
		input.setMerchantFee(bigDecimal("30"));
		input.setCommission(bigDecimal("15"));
		input.setFopType(FopType.INVOICE);

		AirFeesBreakdown afb = calculator.calculate(input, null, null);

		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getDiscount().doubleValue(), is(equalTo(150D)));
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(0D)));
	}

	@Test
	public void shouldCalculateWithApplyFormula() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.INVOICE);

		AirFeesBreakdown afb = calculator.calculate(input, null, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(0D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(60D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(240D)));
		assertThat(afb.getMerchantFee(), nullValue());
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(237D)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMF() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.CWT);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(true);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));
		AirFeesBreakdown afb = calculator.calculate(input,
				merchantFee, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(91.75D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(383.75)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCX() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.CWT);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));

		AirFeesBreakdown afb = calculator.calculate(input,
				merchantFee, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(73D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(365D)));
	}

	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMerFee() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.CWT);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));

		AirFeesBreakdown afb = calculator.calculate(input,
				merchantFee, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertThat(afb.getMerchantFee().doubleValue(), is(equalTo(73D)));
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(365D)));
	}
	
	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMerFee2() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.CWT);
		input.setCwtAbsorb(true);
		input.setMerchantFeeWaive(false);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));

		AirFeesBreakdown afb = calculator.calculate(input,
				merchantFee, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertNull(afb.getMerchantFee());
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(292D)));
	}
	
	@Test
	public void shouldCalculateWithApplyFormulaAndProductTypeCTAndFOPTypeCXIncMerFee3() {

		AirFeesInput input = new AirFeesInput();
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
		input.setFopType(FopType.CWT);
		input.setCwtAbsorb(false);
		input.setMerchantFeeWaive(true);
		merchantFee.setIncludeTransactionFee(false);
		merchantFee.setMerchantFeePercent(Double.parseDouble("25"));
		input.setTransactionFee(bigDecimal("75"));

		AirFeesBreakdown afb = calculator.calculate(input,
				merchantFee, "SG");

		assertThat(afb.getDiscount().doubleValue(), is(equalTo(45D)));
		assertThat(afb.getCommission().doubleValue(), is(equalTo(15D)));
		assertThat(afb.getNettCost().doubleValue(), is(equalTo(285D)));
		assertNull(afb.getMerchantFee());
		assertThat(afb.getTotalSellingFare().doubleValue(), is(equalTo(292D)));
	}

	private BigDecimal bigDecimal(String amount) {
		if (amount == null || amount.equals("")) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(amount);
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionWhenMerchantFeeIsNull() {

		AirFeesInput input = new AirFeesInput();
		input.setApplyFormula(true);
		input.setCwtAbsorb(false);
		input.setFopType(FopType.CWT);
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

		calculator.calculate(input, null, "SG");

	}
}

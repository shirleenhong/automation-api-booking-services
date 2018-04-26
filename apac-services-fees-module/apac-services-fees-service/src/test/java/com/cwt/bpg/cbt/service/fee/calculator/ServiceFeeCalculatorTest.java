package com.cwt.bpg.cbt.service.fee.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeCalculatorTest {
	
	@InjectMocks
	private ServiceFeeCalculator calculator = new ServiceFeeCalculator();
	
	@Mock
	private ScaleConfig scaleConfig;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(scaleConfig.getScale(Mockito.eq("SG"))).thenReturn(2);
		Mockito.when(scaleConfig.getScale(Mockito.eq("HK"))).thenReturn(0);
		
		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
	}
		
	@Test
	public void shouldReturnCalculatedFopAmount() {
		BigDecimal baseFare = new BigDecimal(2000);
		BigDecimal totalTaxes = new BigDecimal(2000);
		BigDecimal markupAmount = new BigDecimal(2000);
		BigDecimal commissionRebateAmount = new BigDecimal(2000);
		BigDecimal fopAmount = calculator.calFopAmount(baseFare, totalTaxes, markupAmount, commissionRebateAmount);
		assertEquals(new BigDecimal(4000), fopAmount);
	}

	@Test
	public void shouldReturnMerchantFeeInput() {
		Double merchantFeePercentage = 30D;
		BigDecimal merchantFeeAmountInput = new BigDecimal(2000);
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = calculator.calMerchantFeeAmount(fopAmount , merchantFeeAmountInput, merchantFeePercentage);
		assertEquals(new BigDecimal(2000), merchantFeeAmount);
	}
	
	@Test
	public void shouldReturnCalculatedMerchantFee() {
		Double merchantFeePercentage = 30D;
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = calculator.calMerchantFeeAmount(fopAmount , null, merchantFeePercentage);
		assertEquals(new BigDecimal(3000), merchantFeeAmount);
	}
	
	@Test
	public void shouldReturnTransactionFeeInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(10000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = calculator.calTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertEquals(new BigDecimal(3000), transactionFeeAmount);
	}
	
	@Test
	public void shouldReturnCalculatedTransactionFee() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(1000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = calculator.calTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertEquals(new BigDecimal(1000), transactionFeeAmount);
	}

	@Test
	public void shouldReturnMarkupAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal markupAmountInput = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = calculator.calMarkupAmount(baseFare, markupAmountInput, markupPercentage);
		assertEquals(new BigDecimal(10000), markupAmount);
	}
	
	@Test
	public void shouldReturnCalculatedMarkupAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = calculator.calMarkupAmount(baseFare, null, markupPercentage);
		assertEquals(new BigDecimal(3000), markupAmount);
	}

	@Test
	public void shouldReturnCommissionRebateAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal commissionRebateAmountInput = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = calculator.calMarkupAmount(baseFare, commissionRebateAmountInput, commissionRebatePercentage);
		assertEquals(new BigDecimal(10000), commissionRebateAmount);
	}
	
	@Test
	public void shouldReturnCalculatedCommissionRebateAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = calculator.calMarkupAmount(baseFare, null, commissionRebatePercentage);
		assertEquals(new BigDecimal(3000), commissionRebateAmount);
	}
	
	@Test
	public void shouldReturnCalculatedFareWithAirlineTax() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal taxes = new BigDecimal(10000);
		BigDecimal obFee = new BigDecimal(10000);
		BigDecimal markupAmount = new BigDecimal(10000);
		BigDecimal airlineCommissionAmount = new BigDecimal(10000);
		BigDecimal fareWithAirlineTax = calculator.calFareWithAirlineTax(baseFare, taxes, obFee, markupAmount, airlineCommissionAmount);
		assertEquals(new BigDecimal(30000), fareWithAirlineTax);
	}
	
	@Test
	public void shouldReturnCalculatedTotalAmount() {
		BigDecimal fareIncludingTaxes = new BigDecimal(10000);
		BigDecimal transactionFee = new BigDecimal(10000);
		BigDecimal merchantFee = new BigDecimal(10000);
		BigDecimal fuelSurcharge = new BigDecimal(10000);
		BigDecimal totalAmount = calculator.calTotalAmount(fareIncludingTaxes, transactionFee, merchantFee, fuelSurcharge);
		assertEquals(new BigDecimal(40000), totalAmount);
	}
	
	@Test
	public void shouldReturnRoundOffCalculatedTotalAmount() {
		BigDecimal fareIncludingTaxes = new BigDecimal(10000);
		BigDecimal transactionFee = new BigDecimal(10000);
		BigDecimal merchantFee = new BigDecimal(10000);
		BigDecimal fuelSurcharge = new BigDecimal(10000);
		BigDecimal totalAmount = calculator.calTotalAmount(fareIncludingTaxes, transactionFee, merchantFee, fuelSurcharge);
		assertEquals(new BigDecimal(40000), totalAmount);
	}
	
	@Test
	public void shouldCalculateFare() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("2441.00"), priceBreakdown.getAirFareWithTaxAmount());

	}

	@Test
	public void shouldCalculateMerchantFee() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("209.10"), priceBreakdown.getMerchantFeeAmount());

	}

	@Test
	public void shouldCalculateTransFeeLowCap() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(15));
		input.setTransactionFeePercentage(2D);
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("15.00"), priceBreakdown.getTransactionFeeAmount());

	}

	@Test
	public void shouldCalculateTransFee() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		input.setTransactionFeePercentage(2D);
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("40.00"), priceBreakdown.getTransactionFeeAmount());

	}

	@Test
	public void shouldCalculate() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupPercentage(15D);
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		input.setFuelSurcharge(new BigDecimal(620));
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("300.00"), priceBreakdown.getMarkupAmount());
		assertEquals(new BigDecimal("2300.00"), priceBreakdown.getFopAmount());
		assertEquals(new BigDecimal("230.00"), priceBreakdown.getMerchantFeeAmount());
		assertEquals(new BigDecimal("2650.00"), priceBreakdown.getAirFareWithTaxAmount());
		assertEquals(new BigDecimal("3550.00"), priceBreakdown.getTotalAmount());

	}

	@Test
	public void shouldCalculateAirCommission() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupPercentage(15D);
		input.setCommissionRebatePercentage(5D);
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		input.setFuelSurcharge(new BigDecimal(620));
		input.setCountryCode("SG");

		PriceBreakdown priceBreakdown = calculator.calculateFee(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal("100.00"), priceBreakdown.getCommissionRebateAmount());

	}

	@Test
	public void shouldUseNetFare() {

	}
}

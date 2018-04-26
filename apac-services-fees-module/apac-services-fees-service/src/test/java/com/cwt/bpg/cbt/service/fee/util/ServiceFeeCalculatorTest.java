package com.cwt.bpg.cbt.service.fee.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class ServiceFeeCalculatorTest {
	
	private ServiceFeeCalculator calculator = new ServiceFeeCalculator();

	@Test
	public void shouldReturnCalculatedFopAmount() {
		BigDecimal baseFare = new BigDecimal(2000);
		BigDecimal totalTaxes = new BigDecimal(2000);
		BigDecimal markupAmount = new BigDecimal(2000);
		BigDecimal commissionRebateAmount = new BigDecimal(2000);
		BigDecimal fopAmount = calculator.calculateFopAmount(baseFare, totalTaxes, markupAmount, commissionRebateAmount);
		assertThat(fopAmount.doubleValue(), is(equalTo(4000D)));
	}

	@Test
	public void shouldReturnMerchantFeeInput() {
		Double merchantFeePercentage = 30D;
		BigDecimal merchantFeeAmountInput = new BigDecimal(2000);
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = calculator.calculateMerchantFeeAmount(merchantFeeAmountInput, fopAmount , merchantFeePercentage);
		assertThat(merchantFeeAmount.doubleValue(), is(equalTo(2000D)));
	}
	
	@Test
	public void shouldReturnCalculatedMerchantFee() {
		Double merchantFeePercentage = 30D;
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = calculator.calculateMerchantFeeAmount(null, fopAmount , merchantFeePercentage);
		assertThat(merchantFeeAmount.doubleValue(), is(equalTo(3000D)));
	}
	
	@Test
	public void shouldReturnTransactionFeeInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(10000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = calculator.calculateTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertThat(transactionFeeAmount.doubleValue(), is(equalTo(10000D)));
	}
	
	@Test
	public void shouldReturnCalculatedTransactionFee() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(1000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = calculator.calculateTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertThat(transactionFeeAmount.doubleValue(), is(equalTo(1000D)));
	}

	@Test
	public void shouldReturnMarkupAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal markupAmountInput = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = calculator.calculateMarkupAmount(markupAmountInput, baseFare, markupPercentage);
		assertThat(markupAmount.doubleValue(), is(equalTo(10000D)));
	}
	
	@Test
	public void shouldReturnCalculatedMarkupAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = calculator.calculateMarkupAmount(null, baseFare, markupPercentage);
		assertThat(markupAmount.doubleValue(), is(equalTo(3000D)));
	}

	@Test
	public void shouldReturnCommissionRebateAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal commissionRebateAmountInput = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = calculator.calculateCommissionRebateAmount(commissionRebateAmountInput, baseFare, commissionRebatePercentage);
		assertThat(commissionRebateAmount.doubleValue(), is(equalTo(10000D)));
	}
	
	@Test
	public void shouldReturnCalculatedCommissionRebateAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = calculator.calculateMarkupAmount(null, baseFare, commissionRebatePercentage);
		assertThat(commissionRebateAmount.doubleValue(), is(equalTo(3000D)));
	}
	
	@Test
	public void shouldReturnCalculatedFareWithAirlineTax() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal taxes = new BigDecimal(10000);
		BigDecimal obFee = new BigDecimal(10000);
		BigDecimal markupAmount = new BigDecimal(10000);
		BigDecimal airlineCommissionAmount = new BigDecimal(10000);
		BigDecimal fareWithAirlineTax = calculator.calculateFareWithAirlineTax(baseFare, taxes, obFee, markupAmount, airlineCommissionAmount);
		assertEquals(new BigDecimal(30000), fareWithAirlineTax);
	}
	
	@Test
	public void shouldReturnCalculatedTotalAmount() {
		BigDecimal fareIncludingTaxes = new BigDecimal(10000);
		BigDecimal transactionFee = new BigDecimal(10000);
		BigDecimal merchantFee = new BigDecimal(10000);
		BigDecimal fuelSurcharge = new BigDecimal(10000);
		BigDecimal totalAmount = calculator.calculateTotalAmount(fareIncludingTaxes, transactionFee, merchantFee, fuelSurcharge);
		assertThat(totalAmount.doubleValue(), is(equalTo(40000D)));
	}
}

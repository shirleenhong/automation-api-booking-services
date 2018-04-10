package com.cwt.bpg.cbt.service.fee.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ServiceFeeUtilTest {

	@Test
	public void shouldReturnCalculatedFopAmount() {
		BigDecimal baseFare = new BigDecimal(2000);
		BigDecimal totalTaxes = new BigDecimal(2000);
		BigDecimal markupAmount = new BigDecimal(2000);
		BigDecimal commissionRebateAmount = new BigDecimal(2000);
		BigDecimal fopAmount = ServiceFeeUtil.calFopAmount(baseFare, totalTaxes, markupAmount, commissionRebateAmount);
		assertEquals(new BigDecimal(4000), fopAmount);
	}

	@Test
	public void shouldReturnMerchantFeeInput() {
		Double merchantFeePercentage = 30D;
		BigDecimal merchantFeeAmountInput = new BigDecimal(2000);
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = ServiceFeeUtil.calMerchantFeeAmount(fopAmount , merchantFeeAmountInput, merchantFeePercentage);
		assertEquals(new BigDecimal(2000), merchantFeeAmount);
	}
	
	@Test
	public void shouldReturnCalculatedMerchantFee() {
		Double merchantFeePercentage = 30D;
		BigDecimal fopAmount = new BigDecimal(10000);
		BigDecimal merchantFeeAmount = ServiceFeeUtil.calMerchantFeeAmount(fopAmount , null, merchantFeePercentage);
		assertEquals(new BigDecimal(3000), merchantFeeAmount);
	}
	
	@Test
	public void shouldReturnTransactionFeeInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(10000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = ServiceFeeUtil.calTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertEquals(new BigDecimal(10000), transactionFeeAmount);
	}
	
	@Test
	public void shouldReturnCalculatedTransactionFee() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal transactionFeeAmountInput = new BigDecimal(1000);
		Double transactionFeePercentage = 30D;
		BigDecimal transactionFeeAmount = ServiceFeeUtil.calTransactionFeeAmount(baseFare, transactionFeeAmountInput, transactionFeePercentage);
		assertEquals(new BigDecimal(3000), transactionFeeAmount);
	}

	@Test
	public void shouldReturnMarkupAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal markupAmountInput = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = ServiceFeeUtil.calMarkupAmount(baseFare, markupAmountInput, markupPercentage);
		assertEquals(new BigDecimal(10000), markupAmount);
	}
	
	@Test
	public void shouldReturnCalculatedMarkupAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double markupPercentage = 30D;
		BigDecimal markupAmount = ServiceFeeUtil.calMarkupAmount(baseFare, null, markupPercentage);
		assertEquals(new BigDecimal(3000), markupAmount);
	}

	@Test
	public void shouldReturnCommissionRebateAmountInput() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal commissionRebateAmountInput = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = ServiceFeeUtil.calMarkupAmount(baseFare, commissionRebateAmountInput, commissionRebatePercentage);
		assertEquals(new BigDecimal(10000), commissionRebateAmount);
	}
	
	@Test
	public void shouldReturnCalculatedCommissionRebateAmount() {
		BigDecimal baseFare = new BigDecimal(10000);
		Double commissionRebatePercentage = 30D;
		BigDecimal commissionRebateAmount = ServiceFeeUtil.calMarkupAmount(baseFare, null, commissionRebatePercentage);
		assertEquals(new BigDecimal(3000), commissionRebateAmount);
	}
	
	@Test
	public void shouldReturnCalculatedFareWithAirlineTax() {
		BigDecimal baseFare = new BigDecimal(10000);
		BigDecimal taxes = new BigDecimal(10000);
		BigDecimal obFee = new BigDecimal(10000);
		BigDecimal markupAmount = new BigDecimal(10000);
		BigDecimal airlineCommissionAmount = new BigDecimal(10000);
		BigDecimal fareWithAirlineTax = ServiceFeeUtil.calFareWithAirlineTax(baseFare, taxes, obFee, markupAmount, airlineCommissionAmount);
		assertEquals(new BigDecimal(30000), fareWithAirlineTax);
	}
	
	@Test
	public void shouldReturnCalculatedTotalAmount() {
		BigDecimal fareIncludingTaxes = new BigDecimal(10000);
		BigDecimal transactionFee = new BigDecimal(10000);
		BigDecimal merchantFee = new BigDecimal(10000);
		BigDecimal fuelSurcharge = new BigDecimal(10000);
		BigDecimal totalAmount = ServiceFeeUtil.calTotalAmount(fareIncludingTaxes, transactionFee, merchantFee, fuelSurcharge);
		assertEquals(new BigDecimal(40000), totalAmount);
	}
}

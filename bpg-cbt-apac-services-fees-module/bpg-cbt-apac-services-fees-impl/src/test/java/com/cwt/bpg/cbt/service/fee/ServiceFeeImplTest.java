package com.cwt.bpg.cbt.service.fee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public class ServiceFeeImplTest {

	private ServiceFeeApi serviceFee = new ServiceFeeImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void souldCalculateFare() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(2441), priceBreakdown.getAirFareWithTaxAmount());

	}
	
	@Test
	public void souldCalculateMerchantFee() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(209), priceBreakdown.getMerchantFeeAmount());

	}
	
	@Test
	public void souldCalculateTransFeeLowCap() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(15));
		input.setTransactionFeePercentage(2D);

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(15), priceBreakdown.getTransactionFeeAmount());

	}
	
	@Test
	public void souldCalculateTransFee() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
		input.setMarkupAmount(new BigDecimal(91));
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		input.setTransactionFeePercentage(2D);

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(40), priceBreakdown.getTransactionFeeAmount());

	}
	
	@Test
	public void souldCalculate() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
//		input.setMarkupAmount(new BigDecimal(91));
		input.setMarkupPercentage(15D);
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		input.setTransactionFeePercentage(2D);

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(300), priceBreakdown.getMarkupAmount());
		//assertEquals(new BigDecimal(300), priceBreakdown.getFopAmount());
		
		
//		FOP Amount = (Base Fare + Total Taxes + Markup Amount) - Commission Rebate Amount
//				Merchant Fee Amount = ((Charged Fare + Mark-Up Amount) - Commission Rebate Amount) * Merchant Fee Percentage 

	}
	

	@Test
	public void shouldUseNetFare() {
		
	}

	
}

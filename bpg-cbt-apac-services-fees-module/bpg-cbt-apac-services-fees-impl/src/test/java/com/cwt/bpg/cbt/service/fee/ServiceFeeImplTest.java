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
	public void shouldCalculateFare() {
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
	public void shouldCalculateMerchantFee() {
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

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(15), priceBreakdown.getTransactionFeeAmount());

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

		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(40), priceBreakdown.getTransactionFeeAmount());

	}
	
	@Test
	public void shouldCalculate() {
		PriceCalculationInput input = new PriceCalculationInput();

		input.setBaseFare(new BigDecimal(2000));
		input.setTotalTaxes(new BigDecimal(200));
		input.setObFee(new BigDecimal(350));
//		input.setMarkupAmount(new BigDecimal(91));
		input.setMarkupPercentage(15D);
		input.setCommissionRebateAmount(new BigDecimal(200));
		input.setMerchantFeePercentage(10D);
		input.setTransactionFeeAmount(new BigDecimal(50));
		//input.setTransactionFeePercentage(2D);
		input.setFuelSurcharge(new BigDecimal(620));
		
		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(300), priceBreakdown.getMarkupAmount());
		assertEquals(new BigDecimal(2300), priceBreakdown.getFopAmount());
		assertEquals(new BigDecimal(230), priceBreakdown.getMerchantFeeAmount());
		assertEquals(new BigDecimal(2650), priceBreakdown.getAirFareWithTaxAmount());
		assertEquals(new BigDecimal(3550), priceBreakdown.getTotalAmount());
		

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
		
		PriceBreakdown priceBreakdown = serviceFee.calculate(input);

		assertNotNull(priceBreakdown);
		assertEquals(new BigDecimal(100), priceBreakdown.getCommissionRebateAmount());

	}

	@Test
	public void shouldUseNetFare() {
		
	}

	
}

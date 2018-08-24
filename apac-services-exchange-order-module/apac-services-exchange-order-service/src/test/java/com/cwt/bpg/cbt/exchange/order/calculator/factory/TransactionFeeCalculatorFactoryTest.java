package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.exchange.order.calculator.tf.*;
import com.cwt.bpg.cbt.exchange.order.model.PricingName;

public class TransactionFeeCalculatorFactoryTest {

	private TransactionFeeCalculatorFactory factory = new TransactionFeeCalculatorFactory();

	@Before
	public void setup() {
		
		ReflectionTestUtils.setField(factory, "tfCalculator", new FeeCalculator());
		ReflectionTestUtils.setField(factory, "tfFullFareCalculator", new FullFareCalculator());
		ReflectionTestUtils.setField(factory, "tfNettFareCalculator", new NettFareCalculator());
		ReflectionTestUtils.setField(factory, "tfGrossFareCalculator", new GrossFareCalculator());
		ReflectionTestUtils.setField(factory, "tfFareCalculator", new FareCalculator());
		ReflectionTestUtils.setField(factory, "tfBasicCalculator", new BasicCalculator());
		ReflectionTestUtils.setField(factory, "noFeeCalculator", new NoFeeCalculator());
		ReflectionTestUtils.setField(factory, "tfPlusVatCalculator", new WithVatCalculator());
		ReflectionTestUtils.setField(factory, "tfRebateCalculator", new RebateCalculator());
		ReflectionTestUtils.setField(factory, "noFeeWithDiscountCalculator", new NoFeeWithDiscountCalculator());
		ReflectionTestUtils.setField(factory, "tfBaseAndYqCalculator", new BaseAndYqCalculator());

		factory.init();
	}

	@Test
	public void tfFullFareCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_FULL_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfNettFareCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_NETT_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfGrossFareCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_GROSS_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfFareCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfBasicCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_BASIC.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void noFeeCalculator() {
		assertTrue(factory.getCalculator(PricingName.NO_FEE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfPlusVatCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_PLUS_VAT.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfRebateCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_REBATE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void noFeeWithDiscountCalculator() {
		assertTrue(factory.getCalculator(PricingName.NO_FEE_WITH_DISCOUNT.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfBaseAndYqCalculator() {
		assertTrue(factory.getCalculator(PricingName.TF_ON_BASE_AND_YQ.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfCalculator() {
		assertTrue(factory.getCalculator(PricingName.TRANSACTION_FEE.getId()) instanceof FeeCalculator);
	}
}

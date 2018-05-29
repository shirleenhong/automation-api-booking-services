package com.cwt.bpg.cbt.exchange.order.calculator.factory;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.exchange.order.calculator.tf.NoFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.NoFeeWithDiscountCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.BaseAndYqCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.BasicCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.FareCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.FullFareCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.GrossFareCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.NettFareCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.WithVatCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.RebateCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.tf.FeeCalculator;
import com.cwt.bpg.cbt.exchange.order.model.PricingNames;

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
		assertTrue(factory.getCalculator(PricingNames.TF_ON_FULL_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfNettFareCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_ON_NETT_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfGrossFareCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_ON_GROSS_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfFareCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_ON_FARE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfBasicCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_ON_BASIC.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void noFeeCalculator() {
		assertTrue(factory.getCalculator(PricingNames.NO_FEE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfPlusVatCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_PLUS_VAT.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfRebateCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_REBATE.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void noFeeWithDiscountCalculator() {
		assertTrue(factory.getCalculator(PricingNames.NO_FEE_WITH_DISCOUNT.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfBaseAndYqCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TF_ON_BASE_AND_YQ.getId()) instanceof FeeCalculator);
	}
	
	@Test
	public void tfCalculator() {
		assertTrue(factory.getCalculator(PricingNames.TRANSACTION_FEE.getId()) instanceof FeeCalculator);
	}
}

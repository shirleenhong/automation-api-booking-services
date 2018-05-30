package com.cwt.bpg.cbt.exchange.order.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.calculator.HkAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.InMiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.MiscFeeCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.NettCostCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.SgAirCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.VisaFeesCalculator;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;
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

public class ExchangeOrderConfigTest {

	private ExchangeOrderConfig config = new ExchangeOrderConfig();

	@Test
	public void shouldCreateTransactionFeeCalculator() {
		assertThat(config.transactionFeeCalculatorFactory(), is(instanceOf(TransactionFeeCalculatorFactory.class)));
	}
	
	@Test
	public void shouldCreateOtherServiceCalculatorFactory() {
		assertThat(config.otherServiceCalculatorFactory(), is(instanceOf(OtherServiceCalculatorFactory.class)));
	}

}

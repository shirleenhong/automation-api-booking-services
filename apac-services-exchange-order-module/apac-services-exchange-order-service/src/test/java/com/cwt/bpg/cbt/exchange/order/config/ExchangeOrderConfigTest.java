package com.cwt.bpg.cbt.exchange.order.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceNonAirCalculatorFactory;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.calculator.factory.OtherServiceCalculatorFactory;
import com.cwt.bpg.cbt.exchange.order.calculator.factory.TransactionFeeCalculatorFactory;

public class ExchangeOrderConfigTest {

	private ExchangeOrderConfig config = new ExchangeOrderConfig();

	@Test
	public void shouldCreateTransactionFeeCalculator() {
		assertThat(config.transactionFeeCalculatorFactory(), is(instanceOf(TransactionFeeCalculatorFactory.class)));
	}
	
	@Test
	public void shouldCreateOtherServiceCalculatorFactory() {
		assertThat(config.AirCalculatorFactory(), is(instanceOf(OtherServiceCalculatorFactory.class)));
	}

	@Test
	public void shouldCreateOtherServiceNonAirCalculatorFactory() {
		assertThat(config.NonAirCalculatorFactory(), is(instanceOf(
				OtherServiceNonAirCalculatorFactory.class)));
	}

}

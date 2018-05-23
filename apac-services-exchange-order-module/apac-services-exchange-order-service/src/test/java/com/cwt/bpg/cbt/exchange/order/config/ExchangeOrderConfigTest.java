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

public class ExchangeOrderConfigTest {

	private ExchangeOrderConfig config = new ExchangeOrderConfig();

	@Test
	public void shouldCreateMiscFeeCalculator() {
		assertThat(config.miscFeeCalculator(), is(instanceOf(MiscFeeCalculator.class)));
	}

	@Test
	public void shouldCreateHkAirCalculator() {
		assertThat(config.hkAirCalculator(), is(instanceOf(HkAirCalculator.class)));
	}

	@Test
	public void shouldCreateSgAirCalculator() {
		assertThat(config.sgAirCalculator(), is(instanceOf(SgAirCalculator.class)));
	}

	@Test
	public void shouldNettCostCalculator() {
		assertThat(config.nettCostCalculator(), is(instanceOf(NettCostCalculator.class)));
	}

	@Test
	public void shouldCreateInCalculator() {
		assertThat(config.inMiscFeeCalculator(), is(instanceOf(InMiscFeeCalculator.class)));
	}
	
	@Test
	public void shouldVisaFeesCalculator() {
		assertThat(config.visaFeesCalculator(), is(instanceOf(VisaFeesCalculator.class)));
	}

	@Test
	public void shouldCreateOsFactory() {
		assertThat(config.otherServiceCalculatorFactory(),
				is(instanceOf(OtherServiceCalculatorFactory.class)));
	}

}

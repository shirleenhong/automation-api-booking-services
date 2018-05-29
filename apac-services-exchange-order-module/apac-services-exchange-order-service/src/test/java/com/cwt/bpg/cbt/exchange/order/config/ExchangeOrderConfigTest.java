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
	public void tfCalculator() {
		assertThat(config.tfCalculator(), is(instanceOf(FeeCalculator.class)));
	}

	@Test
	public void tfFullFareCalculator() {
		assertThat(config.tfFullFareCalculator(), is(instanceOf(FullFareCalculator.class)));
	}

	@Test
	public void tfNettFareCalculator() {
		assertThat(config.tfNettFareCalculator(), is(instanceOf(NettFareCalculator.class)));
	}

	@Test
	public void tfGrossFareCalculator() {
		assertThat(config.tfGrossFareCalculator(), is(instanceOf(GrossFareCalculator.class)));
	}

	@Test
	public void tfFareCalculator() {
		assertThat(config.tfFareCalculator(), is(instanceOf(FareCalculator.class)));
	}

	@Test
	public void tfBasicCalculator() {
		assertThat(config.tfBasicCalculator(), is(instanceOf(BasicCalculator.class)));
	}

	@Test
	public void noFeeCalculator() {
		assertThat(config.noFeeCalculator(), is(instanceOf(NoFeeCalculator.class)));
	}

	@Test
	public void tfPlusVatCalculator() {
		assertThat(config.tfPlusVatCalculator(), is(instanceOf(WithVatCalculator.class)));
	}

	@Test
	public void tfRebateCalculator() {
		assertThat(config.tfRebateCalculator(), is(instanceOf(RebateCalculator.class)));
	}

	@Test
	public void noFeeWithDiscountCalculator() {
		assertThat(config.noFeeWithDiscountCalculator(), is(instanceOf(NoFeeWithDiscountCalculator.class)));
	}

	@Test
	public void tfBaseAndYqCalculator() {
		assertThat(config.tfBaseAndYqCalculator(), is(instanceOf(BaseAndYqCalculator.class)));
	}
}

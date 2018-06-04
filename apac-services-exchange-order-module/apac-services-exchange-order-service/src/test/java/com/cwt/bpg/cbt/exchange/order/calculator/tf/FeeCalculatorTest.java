package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

@Ignore
public class FeeCalculatorTest {

	private FeeCalculator calculator = new FeeCalculator();
	
	@Test
	public void shouldCalculate() {
		Client client = new Client();
		TransactionFeesInput input = new TransactionFeesInput();
		calculator.calculate(input, null, client, new Airport());
	}

	@Test
	public void shouldGetTotalAirCommission() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalMarkup() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalDiscount() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalFare() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalOverheadCommission() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalOrCom2() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetMerchantFee() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetMfOnTf() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalNettFare() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalFee() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalSellingFare() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetTotalCharge() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetDdlFeeApply() {
		fail("Not yet implemented");
	}

}

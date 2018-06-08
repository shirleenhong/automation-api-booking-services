package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.*;

public class FeeCalculatorTest {

	private FeeCalculator calculator;
	private IndiaAirFeesInput input;
	private IndiaAirFeesBreakdown breakdown;
	private BigDecimal baseFare;
	private BigDecimal yqTax;
	private BigDecimal airlineCommission;
	private BigDecimal totalIataCommission;
	private BigDecimal totalOverheadCommission;
	private BigDecimal tax1;
	private BigDecimal tax2;
	private BigDecimal totalSellFare;
	private BigDecimal totalDiscount;
	private BigDecimal totalTaxes;
	private BigDecimal totalGst;
	private Double gst;
	private Double airlineCommissionPercent;
	private Double ot1;
	private Double ot2;

	@Before
	public void setup() {
		calculator = new FeeCalculator();
		breakdown = new IndiaAirFeesBreakdown();
		input = new IndiaAirFeesInput();
		baseFare = new BigDecimal(5);
		yqTax = new BigDecimal(5);
		airlineCommission = new BigDecimal(5);
		totalIataCommission = new BigDecimal(5);
		gst = new Double(30);
		airlineCommissionPercent = new Double(5);
		ot1 = new Double(5);
		ot2 = new Double(5);
	}
	@Test
	public void shouldCalculate() {
		Client client = new Client();
		IndiaAirFeesInput input = new IndiaAirFeesInput();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();

		product.setGst(gst);
		product.setOt1(ot1);
		product.setOt2(ot2);

		input.setGstEnabled(true);
		input.setProduct(product);
		input.setCommissionEnabled(true);
		input.setOverheadCommissionEnabled(true);
		input.setMarkupEnabled(true);
		input.setBaseFare(baseFare);
		input.setYqTax(yqTax);
		input.setAirlineCommission(airlineCommission);
		input.setDiscountEnabled(true);
		input.setTax1(tax1);
		input.setTax2(tax2);
		input.setTripType("I");
		input.setAirlineCommissionPercent(airlineCommissionPercent);
		input.setFeeOverride(false);

		breakdown.setTotalIataCommission(totalIataCommission);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("P");
		clientPricing.setTripType("I");

		transactionFee.setTerritoryCodes(new ArrayList<>());
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		calculator.calculate(input, airlineRule, client, new Airport(), new IndiaProduct());
	}




	@Test
	public void shouldGetTotalDiscountNotInternational() {
		input.setTripType("D");
		BigDecimal totalDiscount = calculator.getTotalDiscount(input, breakdown);

		assertEquals(new BigDecimal("0.0"), totalDiscount);
	}

	@Test
	public void shouldGetTotalOverheadCommission() {
		input.setTripType("D");
		BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission(input);

		assertNull(totalOverheadCommission);
	}

	@Test
	public void shouldGetTotalOrCom2() {
		input.setTripType("I");
		input.setAirlineOverheadCommission(airlineCommission);
		input.setOverheadCommissionPercent(airlineCommissionPercent);
		BigDecimal totalOrCom2 = calculator.getTotalOrCom2(input);

		assertEquals(new BigDecimal(0.25), totalOrCom2);

	}

	@Test
	public void shouldGetTotalOrCom2Null() {
		input.setTripType("D");
		input.setAirlineOverheadCommission(airlineCommission);
		input.setOverheadCommissionPercent(airlineCommissionPercent);

		assertNull(calculator.getTotalOrCom2(input));

	}


	@Test
	public void shouldGetTotalNettFare() {
		input.setBaseFare(baseFare);
		BigDecimal totalNetFare = calculator.getTotalNettFare(input);

		assertEquals(new BigDecimal(5), totalNetFare);
	}

	@Test
	public void shouldGetDdlFeeApply() {
		Boolean ddlFeeApply = calculator.getDdlFeeApply();
		assertTrue(ddlFeeApply);
	}

}

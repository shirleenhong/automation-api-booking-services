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
	private BigDecimal maxAmount;
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
		totalSellFare = new BigDecimal(1000);
		maxAmount = new BigDecimal(4);
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

		breakdown = calculator.calculate(input, airlineRule, client, new Airport(), new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionCoupon() {
		Client client = new Client();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("C");
		clientPricing.setTripType("I");

		transactionFee.setTerritoryCodes(new ArrayList<>());
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		breakdown = calculator.calculate(input, airlineRule, client, new Airport(), new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionTicket() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("T");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionTicketWithTfOperatorF() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("T");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFee.setOperator("F");
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionTicketWithTfOperatorM() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("T");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFee.setOperator("M");
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionTicketWithTfOperatorD() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("T");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFee.setOperator("D");
		transactionFee.setAmount(baseFare);
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionTicketWithTfOperatorDWithMaxAmt() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("T");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFee.setOperator("D");
		transactionFee.setAmount(totalSellFare);
		transactionFee.setMaxAmount(maxAmount);
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
	}

	@Test
	public void shouldCalculateFeeOptionPnr() {
		Client client = new Client();
		AirlineRule airlineRule = new AirlineRule();
		IndiaProduct product = new IndiaProduct();
		Airport airport = new Airport();
		List<ClientPricing> clientPricings = new ArrayList<>();
		ClientPricing clientPricing = new ClientPricing();
		List<TransactionFee> transactionFees = new ArrayList<>();
		TransactionFee transactionFee = new TransactionFee();
		List<String> territorryCodes = new ArrayList<>();
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
		breakdown.setClientDiscountPercent(clientDiscountPercent);
		breakdown.setTotalOverheadCommission(totalOverheadCommission);
		breakdown.setTotalSellFare(totalSellFare);
		breakdown.setTotalDiscount(totalDiscount);
		breakdown.setTotalTaxes(totalTaxes);
		breakdown.setTotalGst(totalGst);

		airlineRule.setIncludeYqCommission(false);

		clientPricing.setFeeOption("P");
		clientPricing.setTripType("I");

		territorryCodes.add("code1");
		transactionFee.setTerritoryCodes(territorryCodes);
		transactionFee.setStartAmount(baseFare);
		transactionFee.setEndAmount(baseFare);
		transactionFee.setOperator("D");
		transactionFee.setAmount(totalSellFare);
		transactionFee.setMaxAmount(maxAmount);
		transactionFees.add(transactionFee);
		clientPricing.setTransactionFees(transactionFees);
		clientPricings.add(clientPricing);

		client.setExemptTax(true);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");
		client.setClientPricings(clientPricings);

		airport.setCityCode("code1");

		breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		assertNotNull(breakdown);
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

		BigDecimal totalOrCom2 = calculator.getTotalOrCom2(input);
		assertNull(totalOrCom2);
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

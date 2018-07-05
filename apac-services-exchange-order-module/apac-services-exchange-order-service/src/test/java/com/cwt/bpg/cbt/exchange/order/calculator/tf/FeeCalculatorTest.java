package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class FeeCalculatorTest {

	private FeeCalculator calculator;
	private IndiaAirFeesInput input;
    private AirlineRule airlineRule;
    private Airport airport;
    private IndiaProduct airProduct;

    @Mock
	private ScaleConfig scaleConfig;

    @Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		calculator = new FeeCalculator();
		
		Mockito.when(scaleConfig.getScale(Mockito.eq("IN"))).thenReturn(2);
		
		ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
		
		input = new IndiaAirFeesInput();
		input.setCountryCode(Country.INDIA.getCode());
		input.setAirSegmentCount(2);
		input.setBaseFare(new BigDecimal(500));
		input.setYqTax(new BigDecimal(50));
		input.setAirlineCommission(new BigDecimal(45));
		input.setAirlineOverheadCommissionPercent(5d);
		input.setTax1(new BigDecimal(25));
		input.setTax2(new BigDecimal(35));
        input.setGstEnabled(true);
        input.setCommissionEnabled(true);
        input.setOverheadCommissionEnabled(true);
        input.setClientOverheadCommissionPercent(15d);
        input.setMarkupEnabled(true);
        input.setDiscountEnabled(true);
        input.setDiscountPercent(5d);
        input.setFeeOverride(false);
        input.setCityCode("BLR");
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
		input.setProduct(createProduct());

        airlineRule = createAirlineRule();
        airport = createAirport();
        airProduct = createAirProduct();
    }

    private Client createClient(String feeOption)
    {
        TransactionFee transactionFee = new TransactionFee();
        transactionFee.setTerritoryCodes(asList("BLR"));
        transactionFee.setStartAmount(new BigDecimal(25));
        transactionFee.setEndAmount(new BigDecimal(50));
        transactionFee.setStartCoupon(1);
        transactionFee.setEndCoupon(2);
        transactionFee.setAmount(new BigDecimal(20));
        List<TransactionFee> transactionFees = new ArrayList<>();
        transactionFees.add(transactionFee);

        ClientPricing clientPricing = new ClientPricing();
        clientPricing.setFeeOption(feeOption);
        clientPricing.setTripType("I");
        clientPricing.setTransactionFees(transactionFees);

        List<ClientPricing> clientPricingList = new ArrayList<>();
        clientPricingList.add(clientPricing);

        Client client = new Client();
        client.setExemptTax(true);
        client.setLccSameAsInt(true);
        client.setIntDdlFeeApply("N");
        client.setClientPricings(clientPricingList);
        return client;
    }

    private AirlineRule createAirlineRule()
    {
        AirlineRule airlineRule = new AirlineRule();
        airlineRule.setIncludeYqCommission(false);
        return airlineRule;
    }

    private IndiaAirProductInput createProduct()
    {
    	IndiaAirProductInput product = new IndiaAirProductInput();
        product.setGstPercent(7d);
        product.setOt1Percent(5d);
        product.setOt2Percent(3d);
        return product;
    }

    private IndiaProduct createAirProduct()
    {
        IndiaProduct product = new IndiaProduct();
        product.setGstPercent(8d);
        product.setOt1Percent(6d);
        product.setOt2Percent(4d);
        return product;
    }

	private Airport createAirport()
	{
		Airport airport = new Airport();
		airport.setCityCode("BLR");
		airport.setCode("BLR");
		airport.setCountryCode("IN");
		return airport;
	}

	@Test
	public void shouldCalculateFeeOptionPnr() {
        Client client = createClient(FeeCalculator.PNR);
		
        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
	}

	@Test
	public void shouldCalculateFeeOptionCouponSoloWithinCouponRange() {
        Client client = createClient(FeeCalculator.COUPON);
        TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
        transactionFee.setType(FeeCalculator.SOLO);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

		assertThat(breakdown, is(not(nullValue())));
		assertThat(breakdown.getOverheadPercent(), is(nullValue()));
		assertThat(breakdown.getGstPercent(), is(nullValue()));
		assertThat(breakdown.getOt1Percent(), is(nullValue()));
		assertThat(breakdown.getOt2Percent(), is(nullValue()));
		assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
		assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
		assertThat(breakdown.getBaseAmount(), is(nullValue()));
		assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
		assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
		assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
		assertThat(breakdown.getTotalMarkup(), is(nullValue()));
		assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
		assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
		assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
		assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
		assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
		assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
		assertThat(breakdown.getTotalCharge().doubleValue(), is(575.19d));
		assertThat(breakdown.getFee(), is(nullValue()));
		
		assertThat(breakdown.getCommission(), is(nullValue()));
	}

    @Test
    public void shouldCalculateFeeOptionCouponSoloOutsideCouponRange() {
        Client client = createClient(FeeCalculator.COUPON);
        TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
        transactionFee.setType(FeeCalculator.SOLO);

        input.setAirSegmentCount(3);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldCalculateFeeOptionCouponGroupSegmentCountEqualToStartCoupon() {
        Client client = createClient(FeeCalculator.COUPON);
        TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
        transactionFee.setType(FeeCalculator.GROUP);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(595.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldCalculateFeeOptionCouponGroupSegmentCountGreaterThanStartCoupon() {
        Client client = createClient(FeeCalculator.COUPON);
        TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
        transactionFee.setType(FeeCalculator.GROUP);

        input.setAirSegmentCount(2);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(595.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldCalculateFeeOptionCouponGroupSegmentCountLessThanStartCoupon() {
        Client client = createClient(FeeCalculator.COUPON);
        TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
        transactionFee.setType(FeeCalculator.GROUP);
        transactionFee.setStartCoupon(2);

        input.setAirSegmentCount(1);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldCalculateFeeOptionTicket() {
        Client client = createClient(FeeCalculator.TICKET);

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));
        
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldGetTotalDiscountInternational() {
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
        input.setDiscountPercent(10d);
        IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
        breakdown.setTotalAirlineCommission(new BigDecimal(50));
        breakdown.setTotalOverheadCommission(new BigDecimal(5));

        BigDecimal totalDiscount = calculator.getTotalDiscount(input, breakdown);

        assertThat(totalDiscount.doubleValue(), is(10d));
    }

    @Test
    public void shouldGetTotalDiscountNotInternational() {
        input.setTripType(TripTypes.DOMESTIC.getCode());
        input.setDiscountPercent(10d);
        IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
        breakdown.setTotalAirlineCommission(new BigDecimal(50));
        breakdown.setTotalOverheadCommission(new BigDecimal(5));

        BigDecimal totalDiscount = calculator.getTotalDiscount(input, breakdown);

        assertThat(totalDiscount.doubleValue(), is(5d));
    }

    @Test
    public void shouldGetTotalOverheadCommissionInternational() {
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
        input.setBaseFare(new BigDecimal(100));
        input.setAirlineOverheadCommissionPercent(10d);
        input.setClientOverheadCommissionPercent(50d);

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission(input, new BigDecimal(15));

        assertThat(totalOverheadCommission.doubleValue(), is(4.25d));
    }

    @Test
    public void shouldGetTotalOverheadCommissionNotInternational() {
        input.setTripType(TripTypes.DOMESTIC.getCode());

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission(input, new BigDecimal(15));

        assertThat(totalOverheadCommission.doubleValue(), is(equalTo(0.00d)));
    }

    @Test
    public void shouldGetTotalOverheadCommission2International() {
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
        input.setBaseFare(new BigDecimal(100));
        input.setAirlineOverheadCommission(new BigDecimal(15));
        input.setClientOverheadCommissionPercent(50d);

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission2(input);

        assertThat(totalOverheadCommission.doubleValue(), is(7.5d));
    }

    @Test
    public void shouldGetTotalOverheadCommission2NotInternational() {
        input.setTripType(TripTypes.DOMESTIC.getCode());

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission2(input);

        assertThat(totalOverheadCommission, is(nullValue(BigDecimal.class)));
    }


    @Test
    public void shouldGetTotalNettFare() {
        input.setBaseFare(new BigDecimal(100));

        BigDecimal totalNettFare = calculator.getTotalNettFare(input);

        assertThat(totalNettFare.doubleValue(), is(100d));
    }

    @Test
    public void shouldGetDdlFeeApply() {
        Boolean ddlFeeApply = calculator.getDdlFeeApply();

        assertThat(ddlFeeApply, is(true));
    }
    
	
	@Test
	public void shouldCalculateFlagsFalseTicketAllOpF() {

		Client client = createClient(FeeCalculator.TICKET);
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setExemptTax(false);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");

		transactionFee.setTerritoryCodes(Arrays.asList("BLR"));
		transactionFee.setOperator("F");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));
		
		AirlineRule airlineRule = new AirlineRule();
		airlineRule.setIncludeYqCommission(true);
		
		setInputFlagsToFalse();
		
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		
        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission(), is(nullValue()));
        assertThat(breakdown.getTotalOverheadCommission(), is(nullValue()));
        assertThat(breakdown.getTotalDiscount(), is(nullValue()));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst(), is(nullValue()));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(500.0d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(630d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
		
	}
	
	private void setInputFlagsToFalse() {

        input.setGstEnabled(false);
        input.setCommissionEnabled(false);
        input.setOverheadCommissionEnabled(false);
        input.setClientOverheadCommissionPercent(15d);
        input.setMarkupEnabled(false);
        input.setDiscountEnabled(false);
        input.setFeeOverride(false);
	}

	@Test
	public void shouldCalculateFlagsFalseTicketAllOpM() {

		Client client = createClient(FeeCalculator.TICKET);
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setExemptTax(false);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");

		transactionFee.setTerritoryCodes(Arrays.asList("BLR"));
		transactionFee.setOperator("M");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));

		AirlineRule airlineRule = new AirlineRule();
		airlineRule.setIncludeYqCommission(true);
		
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, new IndiaProduct());
		
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(27.5d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.54d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.92d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(82.5d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(577.58));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(687.58d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
	}

	
	@Test
	public void shouldCalculateFlagsFalseTicketAllOpMFeeGrMax() {

		Client client = createClient(FeeCalculator.TICKET);
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setExemptTax(false);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");

		transactionFee.setTerritoryCodes(Arrays.asList("BLR"));
		transactionFee.setOperator("D");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));
		transactionFee.setMaxAmount(new BigDecimal(10));

		AirlineRule airlineRule = new AirlineRule();
		airlineRule.setIncludeYqCommission(true);
				
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);

		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(27.5d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.54d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.92d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(82.5d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(577.58d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(687.58d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
	}

	@Test
	public void shouldCalculateFlagsNoAirlineRule() {

		Client client = createClient("P");
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setExemptTax(false);
		client.setLccSameAsInt(true);
		client.setIntDdlFeeApply("N");

		transactionFee.setTerritoryCodes(new ArrayList<>());
		transactionFee.setOperator("M");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));
		transactionFee.setMaxAmount(new BigDecimal(10));
		
		input.setTripType(TripTypes.DOMESTIC.getCode());
		input.setFeeOverride(true);
		input.setFee(new BigDecimal(500));

		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, null, client, 
				new Airport(), airProduct);
		
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(27.5d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(equalTo(0.00d)));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(1.38d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(82.5d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(581.12d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(1191.12d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
		
	}
	
	@Test
	public void shouldCalculateApplyFeeNA() {

		Client client = createClient(FeeCalculator.TICKET);
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setLccSameAsInt(false);
		client.setLccDdlFeeApply("NA");

		transactionFee.setTerritoryCodes(Arrays.asList("X"));
		transactionFee.setOperator("F");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));

		AirlineRule airlineRule = new AirlineRule();
		airlineRule.setIncludeYqCommission(true);		

		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, new Airport(), airProduct);
		
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(27.5d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.54d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.92d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.08d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(605.08d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
	}

	@Test
	public void shouldCalculateInvalidFeeOption() {
		
		Client client = createClient("X");
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setLccSameAsInt(false);
		client.setLccDdlFeeApply("N");

		transactionFee.setTerritoryCodes(Arrays.asList("X"));
		transactionFee.setOperator("F");
		transactionFee.setStartAmount(BigDecimal.ZERO);
		transactionFee.setEndAmount(new BigDecimal(1000));

		AirlineRule airlineRule = new AirlineRule();
		airlineRule.setIncludeYqCommission(true);
		
		setInputFlagsToFalse();
		
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, new Airport(), airProduct);
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission(), is(nullValue()));
        assertThat(breakdown.getTotalOverheadCommission(), is(nullValue()));
        assertThat(breakdown.getTotalDiscount(), is(nullValue()));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst(), is(nullValue()));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(500.0d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(610d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
		
	}
	
	@Test
	public void shouldCalculateNonExistentOp() {
		
		Client client = createClient(FeeCalculator.TICKET);
		TransactionFee transactionFee = client.getClientPricings().get(0).getTransactionFees().get(0);
		client.setLccSameAsInt(false);
		client.setLccDdlFeeApply("N");

		transactionFee.setTerritoryCodes(Arrays.asList("BLR"));
		transactionFee.setOperator("X");
		transactionFee.setEndAmount(BigDecimal.ZERO);
		
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);
		
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25.00d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
		
	}

	@Test
	public void shouldCalculatePricingNotPresent() {

		Client client = createClient(FeeCalculator.TICKET);
		client.setClientPricings(new ArrayList<>());
		
		IndiaAirFeesBreakdown breakdown = calculator.calculate(input, airlineRule, client, airport, airProduct);
		
		assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(25.00d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), is(3.56d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(4.81d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(0d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(495.19d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(60d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(555.19d));
        assertThat(breakdown.getFee(), is(nullValue()));        
        assertThat(breakdown.getCommission(), is(nullValue()));
		
	}

}

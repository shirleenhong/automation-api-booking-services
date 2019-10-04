package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.FlatTransactionFeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@RunWith(MockitoJUnitRunner.class)
public class FeeCalculatorTest
{

    @InjectMocks
    private FeeCalculator calculator;
    private IndiaAirFeesInput input;
    private IndiaProduct airProduct;

    @Mock
    private ScaleConfig scaleConfig;

    @Mock
    private FlatTransactionFeeService transactionFeeService;

    @Before
    public void setup()
    {
        when(scaleConfig.getScale(Mockito.eq("IN"))).thenReturn(2);

        input = new IndiaAirFeesInput();
        input.setAirSegmentCount(2);
        input.setBaseFare(new BigDecimal(500));
        input.setYqTax(new BigDecimal(50));
        input.setAirlineCommissionPercent(new Double(45));
        input.setAirlineOverheadCommissionPercent(5d);
        input.setOthTax1(new BigDecimal(25));
        input.setOthTax2(new BigDecimal(35));
        input.setGstEnabled(true);
        input.setCommissionEnabled(true);
        input.setOverheadCommissionEnabled(true);
        input.setClientOverheadCommissionPercent(15d);
        input.setMarkupEnabled(true);
        input.setDiscountEnabled(true);
        input.setDiscountPercent(5d);
        input.setFeeOverride(false);
        input.setCityCode("BLR");
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setProduct(createProduct());
        input.setAirlineOverheadCommissionByPercent(true);

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
        clientPricing.setFieldId(5);

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

    @Test
    public void shouldGetTotalDiscountInternational()
    {
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setDiscountPercent(10d);
        IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
        breakdown.setTotalAirlineCommission(new BigDecimal(50));
        breakdown.setTotalOverheadCommission(new BigDecimal(5));

        BigDecimal totalDiscount = calculator.getTotalDiscount(input, breakdown);

        assertThat(totalDiscount.doubleValue(), is(10d));
    }

    @Test
    public void shouldGetTotalDiscountNotInternational()
    {
        input.setTripType(TripType.DOMESTIC.getCode());
        input.setDiscountPercent(10d);
        IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
        breakdown.setTotalAirlineCommission(new BigDecimal(50));
        breakdown.setTotalOverheadCommission(new BigDecimal(5));

        BigDecimal totalDiscount = calculator.getTotalDiscount(input, breakdown);

        assertThat(totalDiscount.doubleValue(), is(5d));
    }

    @Test
    public void shouldGetTotalOverheadCommissionInternational()
    {
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setBaseFare(new BigDecimal(100));
        input.setAirlineOverheadCommissionPercent(10d);
        input.setClientOverheadCommissionPercent(50d);

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission(input, new BigDecimal(15));

        assertThat(totalOverheadCommission.doubleValue(), is(4.25d));
    }

    @Test
    public void shouldGetTotalOverheadCommissionNotInternational()
    {
        input.setTripType(TripType.DOMESTIC.getCode());

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission(input, new BigDecimal(15));

        assertThat(totalOverheadCommission.doubleValue(), is(equalTo(0.00d)));
    }

    @Test
    public void shouldGetTotalOverheadCommission2International()
    {
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setBaseFare(new BigDecimal(100));
        input.setAirlineOverheadCommissionByPercent(false);

        input.setAirlineOverheadCommission(new BigDecimal(15));
        input.setClientOverheadCommissionPercent(50d);

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission2(input);

        assertThat(totalOverheadCommission.doubleValue(), is(7.5d));
    }

    @Test
    public void shouldGetTotalOverheadCommission2NotInternational()
    {
        input.setTripType(TripType.DOMESTIC.getCode());

        BigDecimal totalOverheadCommission = calculator.getTotalOverheadCommission2(input);

        assertThat(totalOverheadCommission, is(BigDecimal.ZERO));
    }

    @Test
    public void shouldGetTotalNettFare()
    {
        input.setBaseFare(new BigDecimal(100));

        BigDecimal totalNettFare = calculator.getTotalNettFare(input);

        assertThat(totalNettFare.doubleValue(), is(100d));
    }

    @Test
    public void shouldGetDdlFeeApply()
    {
        Boolean ddlFeeApply = calculator.getDdlFeeApply();

        assertThat(ddlFeeApply, is(true));
    }

    @Test
    public void shouldCalculateFlagsNoAirlineRule()
    {

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

        input.setTripType(TripType.DOMESTIC.getCode());
        input.setFeeOverride(true);
        input.setFee(new BigDecimal(500));

        IndiaAirFeesBreakdown breakdown = calculator.calculate(input, null, client, airProduct);

        assertThat(breakdown, is(not(nullValue())));
        assertThat(breakdown.getOverheadPercent(), is(nullValue()));
        assertThat(breakdown.getGstPercent(), is(nullValue()));
        assertThat(breakdown.getOt1Percent(), is(nullValue()));
        assertThat(breakdown.getOt2Percent(), is(nullValue()));
        assertThat(breakdown.getMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getSubMerchantFeePercent(), is(nullValue()));
        assertThat(breakdown.getBaseAmount(), is(nullValue()));
        assertThat(breakdown.getTotalAirlineCommission().doubleValue(), is(225.0d));
        assertThat(breakdown.getTotalOverheadCommission().doubleValue(), equalTo(0d));
        assertThat(breakdown.getTotalDiscount().doubleValue(), is(11.25d));
        assertThat(breakdown.getTotalMarkup(), is(nullValue()));
        assertThat(breakdown.getTotalSellFare().doubleValue(), is(500d));
        assertThat(breakdown.getTotalGst().doubleValue(), is(82.5d));
        assertThat(breakdown.getTotalMerchantFee().doubleValue(), is(0d));
        assertThat(breakdown.getTotalSellingFare().doubleValue(), is(571.25d));
        assertThat(breakdown.getTotalTaxes().doubleValue(), is(110d));
        assertThat(breakdown.getMerchantFeeOnTf().doubleValue(), is(0d));
        assertThat(breakdown.getTotalCharge().doubleValue(), is(1181.25d));
        assertThat(breakdown.getFee().doubleValue(), is(500d));
        assertThat(breakdown.getCommission(), is(nullValue()));
    }

    @Test
    public void shouldGetIntOfflineAmount()
    {
        Client client = new Client();
        client.setClientAccountNumber("9000000042");
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setFeeOverride(false);

        FlatTransactionFee tf = new FlatTransactionFee();
        tf.setIntOfflineAmount(BigDecimal.valueOf(100));
        when(transactionFeeService.getTransactionFee(anyString())).thenReturn(tf);
        IndiaAirFeesBreakdown result = calculator.calculate(input, null, client, airProduct);
        assertThat(result.getFee().doubleValue(), is(tf.getIntOfflineAmount().doubleValue()));
    }

    @Test
    public void shouldGetDomOfflineAmount()
    {
        Client client = new Client();
        client.setClientAccountNumber("9000000043");
        input.setTripType(TripType.DOMESTIC.getCode());
        input.setFeeOverride(false);

        FlatTransactionFee tf = new FlatTransactionFee();
        tf.setDomOfflineAmount(BigDecimal.valueOf(100));
        when(transactionFeeService.getTransactionFee(anyString())).thenReturn(tf);
        IndiaAirFeesBreakdown result = calculator.calculate(input, null, client, airProduct);
        assertThat(result.getFee().doubleValue(), is(tf.getDomOfflineAmount().doubleValue()));
    }

    @Test
    public void shouldGetLccAmount()
    {
        Client client = new Client();
        client.setClientAccountNumber("9000000043");
        input.setTripType(TripType.LCC.getCode());
        input.setFeeOverride(false);

        FlatTransactionFee tf = new FlatTransactionFee();
        tf.setLccOfflineAmount(BigDecimal.valueOf(100.00));
        when(transactionFeeService.getTransactionFee(anyString())).thenReturn(tf);
        IndiaAirFeesBreakdown result = calculator.calculate(input, null, client, airProduct);
        assertThat(result.getFee().doubleValue(), is(tf.getLccOfflineAmount().doubleValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnNoFeeforClient() {
        Client client = new Client();
        client.setClientAccountNumber("9000000042");
        input.setTripType(TripType.INTERNATIONAL.getCode());
        input.setFeeOverride(false);

        when(transactionFeeService.getTransactionFee(anyString())).thenReturn(null);
        calculator.calculate(input, null, client, airProduct);
    }
}

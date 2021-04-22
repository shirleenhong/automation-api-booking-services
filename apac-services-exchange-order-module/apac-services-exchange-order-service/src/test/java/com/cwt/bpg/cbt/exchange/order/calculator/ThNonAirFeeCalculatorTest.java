package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.NonAirFeesInput;

public class ThNonAirFeeCalculatorTest {

    @InjectMocks
    private ThNonAirFeeCalculator calculator;

    @Mock
    private ScaleConfig scaleConfig;

    @Mock
    private RoundingConfig roundingConfig;

    private MerchantFee merchantFee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(scaleConfig.getScale(eq("TH"))).thenReturn(0);

        ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);

        when(roundingConfig.getRoundingMode(eq("nettFare"), anyString())).thenReturn(RoundingMode.HALF_UP);
        when(roundingConfig.getRoundingMode(eq("nettCost"), anyString())).thenReturn(RoundingMode.HALF_UP);
        when(roundingConfig.getRoundingMode(eq("merchantFee"), anyString())).thenReturn(RoundingMode.HALF_UP);
        when(roundingConfig.getRoundingMode(eq("totalSellingFare"), anyString())).thenReturn(RoundingMode.HALF_UP);
        when(roundingConfig.getRoundingMode(eq("commission"), anyString())).thenReturn(RoundingMode.HALF_UP);

        ReflectionTestUtils.setField(calculator, "roundingConfig", roundingConfig);

        merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(1.1D);
    }

    @Test
    public void shouldCalculateFees0DScaleExceptGST() {

        NonAirFeesInput input = new NonAirFeesInput();

        input.setSellingPrice(new BigDecimal(500.20D));
        input.setGstPercent(7D);
        input.setNettCost(new BigDecimal(1228.27));
        input.setTax(new BigDecimal(10.12D));
        input.setCommission(new BigDecimal(5.2D));

        NonAirFeesBreakdown result = calculator.calculate(input, merchantFee, "TH");
        assertNotNull(result);

        assertThat(result.getCommission(), Matchers.equalTo(BigDecimal.valueOf(5)));
        assertThat(result.getGstAmount(), Matchers.equalTo(BigDecimal.valueOf(35.7)));
        assertThat(result.getMerchantFee(), Matchers.equalTo(BigDecimal.valueOf(10)));
        assertThat(result.getNettCost(), Matchers.equalTo(BigDecimal.valueOf(1228)));
        assertThat(result.getSellingPrice(), Matchers.equalTo(BigDecimal.valueOf(500)));
        assertThat(result.getTax(), Matchers.equalTo(BigDecimal.valueOf(10)));
        assertThat(result.getTotalSellingPrice(), Matchers.equalTo(BigDecimal.valueOf(556)));
    }

    @Test
    public void shouldNotFailOnNullInput() {
        NonAirFeesBreakdown result = calculator.calculate(null, null, null);

        assertThat(result.getCommission(), is(nullValue(BigDecimal.class)));
        assertThat(result.getGstAmount(), is(nullValue(BigDecimal.class)));
        assertThat(result.getMerchantFee(), is(nullValue(BigDecimal.class)));
        assertThat(result.getNettCost(), is(nullValue(BigDecimal.class)));
        assertThat(result.getSellingPrice(), is(nullValue(BigDecimal.class)));
        assertThat(result.getTax(), is(nullValue(BigDecimal.class)));
        assertThat(result.getTotalSellingPrice(), is(nullValue(BigDecimal.class)));
    }

    @Test
    public void shouldNotFailOnEmptyInput() {
        NonAirFeesInput input = new NonAirFeesInput();
        NonAirFeesBreakdown result = calculator.calculate(input, null, "TH");

        assertThat(result.getCommission(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getGstAmount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getMerchantFee(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getNettCost(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getSellingPrice(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getTax(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(result.getTotalSellingPrice(), Matchers.comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void shouldCalculateGstAmount() {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(2.2D);

        NonAirFeesInput input = new NonAirFeesInput();
        input.setSellingPrice(new BigDecimal(500));
        input.setGstPercent(7D);
        input.setNettCost(new BigDecimal(1000));
        input.setTax(new BigDecimal(5D));
        input.setCommission(new BigDecimal(5D));
        NonAirFeesBreakdown result = calculator.calculate(input, merchantFee, "TH");

        assertThat(result.getGstAmount(), Matchers.equalTo(BigDecimal.valueOf(35.4)));
    }

    @Test
    public void shouldCalculateMerchantFee() {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(2.2D);

        NonAirFeesInput input = new NonAirFeesInput();
        input.setSellingPrice(new BigDecimal(300));
        input.setGstPercent(7D);
        input.setNettCost(new BigDecimal(800));
        input.setTax(new BigDecimal(20D));
        input.setCommission(new BigDecimal(5D));
        NonAirFeesBreakdown result = calculator.calculate(input, merchantFee, "TH");

        assertThat(result.getMerchantFee(), Matchers.equalTo(BigDecimal.valueOf(10)));
    }

}

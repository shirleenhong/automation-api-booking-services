package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public class ThAirCalculatorTest {

    private Calculator<AirFeesBreakdown, AirFeesInput> calculator = new ThAirCalculator();

    @Mock
    private ScaleConfig scaleConfig;

    @Mock
    private RoundingConfig roundingConfig;

    private String countryCode = "TH";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(scaleConfig.getScale(Mockito.eq("TH"))).thenReturn(0);

        Mockito.when(
                roundingConfig.getRoundingMode(Mockito.eq("merchantFee"), anyString()))
                .thenReturn(RoundingMode.UP);
        Mockito.when(
                roundingConfig.getRoundingMode(Mockito.eq("commission"), anyString()))
                .thenReturn(RoundingMode.DOWN);
        Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("nettFare"), anyString()))
                .thenReturn(RoundingMode.UP);
        Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("totalSellingFare"),
                anyString())).thenReturn(RoundingMode.UP);
        Mockito.when(roundingConfig.getRoundingMode(Mockito.eq("nettCost"), anyString()))
                .thenReturn(RoundingMode.UP);

        ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
        ReflectionTestUtils.setField(calculator, "roundingConfig", roundingConfig);
    }

    @Test
    public void shouldHandleNullInput() {
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(null, null, null);
        assertNotNull(airFeesBreakdown);
        assertNull(airFeesBreakdown.getCommission());
        assertNull(airFeesBreakdown.getTotalSellingFare());
        assertNull(airFeesBreakdown.getNettCost());
        assertNull(airFeesBreakdown.getSellingPrice());
        assertNull(airFeesBreakdown.getMerchantFee());
        assertNull(airFeesBreakdown.getNettFare());
    }

    @Test
    public void shouldHandleNullFieldsFromInput() {
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(new AirFeesInput(), null, countryCode);
        assertNotNull(airFeesBreakdown);
        assertEquals(0D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
        assertEquals(0D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
        assertEquals(0D, airFeesBreakdown.getCommission().doubleValue(), 0D);
        assertEquals(0D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
    }

    @Test
    public void shouldCalculateMerchantFee() {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(20D);
        AirFeesInput input = new AirFeesInput();
        input.setSellingPrice(new BigDecimal(10000));
        input.setCommission(new BigDecimal(2000));
        input.setNettFare(new BigDecimal(10000));
        input.setTax1(new BigDecimal(1000));
        input.setTax2(new BigDecimal(1000));
        input.setClientType("TF");
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(input, merchantFee, countryCode);

        assertEquals(2000D, airFeesBreakdown.getCommission().doubleValue(), 0D);
        assertEquals(10000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
        assertEquals(12000D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
        assertEquals(2400D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
        assertEquals(14400D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
        assertEquals(10000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
    }

    @Test
    public void shouldRoundAirFeesByFive() {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(1.75D);
        AirFeesInput input = new AirFeesInput();
        input.setSellingPrice(new BigDecimal(5000));
        input.setCommission(new BigDecimal(800));
        input.setNettFare(new BigDecimal(5000));
        input.setTax1(new BigDecimal(300));
        input.setTax2(new BigDecimal(200));
        input.setClientType("TF");
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(input, merchantFee, countryCode);

        assertEquals(800D, airFeesBreakdown.getCommission().doubleValue(), 0D);
        assertEquals(5000D, airFeesBreakdown.getSellingPrice().doubleValue(), 0D);
        assertEquals(5500D, airFeesBreakdown.getNettFare().doubleValue(), 0D);
        assertEquals( 100D, airFeesBreakdown.getMerchantFee().doubleValue(), 0D);
        assertEquals(5600D, airFeesBreakdown.getTotalSellingFare().doubleValue(), 0D);
        assertEquals(5000D, airFeesBreakdown.getNettCost().doubleValue(), 0D);
    }
}

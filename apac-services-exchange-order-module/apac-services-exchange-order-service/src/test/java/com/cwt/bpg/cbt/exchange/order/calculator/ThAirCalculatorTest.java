package com.cwt.bpg.cbt.exchange.order.calculator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.hamcrest.Matchers;
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

public class ThAirCalculatorTest
{

    private Calculator<AirFeesBreakdown, AirFeesInput> calculator = new ThAirCalculator();

    @Mock
    private ScaleConfig scaleConfig;

    @Mock
    private RoundingConfig roundingConfig;

    private String countryCode = "TH";

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        Mockito.when(scaleConfig.getScale(Mockito.eq("TH"))).thenReturn(2);

        Mockito.when(
                roundingConfig.getRoundingMode(anyString(), anyString()))
                .thenReturn(RoundingMode.UP);
        Mockito.when(
                roundingConfig.getRoundingMode(Mockito.eq("commission"), anyString()))
                .thenReturn(RoundingMode.DOWN);

        ReflectionTestUtils.setField(calculator, "scaleConfig", scaleConfig);
        ReflectionTestUtils.setField(calculator, "roundingConfig", roundingConfig);
    }

    @Test
    public void shouldHandleNullInput()
    {
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(null, null, null);
        assertNotNull(airFeesBreakdown);
        assertNull(airFeesBreakdown.getCommission());
        assertNull(airFeesBreakdown.getTotalSellingFare());
        assertNull(airFeesBreakdown.getNettCost());
        assertNull(airFeesBreakdown.getSellingPrice());
        assertNull(airFeesBreakdown.getMerchantFee());
        assertNull(airFeesBreakdown.getNettFare());
        assertNull(airFeesBreakdown.getDiscount());
        assertNull(airFeesBreakdown.getVat());
    }

    @Test
    public void shouldHandleNullFieldsFromInput()
    {
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(new AirFeesInput(), null, countryCode);
        assertNotNull(airFeesBreakdown);
        assertThat(airFeesBreakdown.getNettFare(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(airFeesBreakdown.getSellingPrice(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(airFeesBreakdown.getCommission(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(airFeesBreakdown.getMerchantFee(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(airFeesBreakdown.getDiscount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(airFeesBreakdown.getVat(), Matchers.comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void shouldCalculateMerchantFee()
    {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(20D);
        AirFeesInput input = new AirFeesInput();
        input.setSellingPrice(new BigDecimal(10000));
        input.setCommission(new BigDecimal(2000));
        input.setNettFare(new BigDecimal(10000));
        input.setTax1(new BigDecimal(1000));
        input.setTax2(new BigDecimal(1000));
        input.setDiscount(new BigDecimal(20));
        input.setTransactionFee(new BigDecimal(350));
        input.setGst(7D);
        input.setClientType("TF");
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(input, merchantFee, countryCode);

        assertThat(airFeesBreakdown.getCommission(), Matchers.comparesEqualTo(BigDecimal.valueOf(2000)));
        assertThat(airFeesBreakdown.getSellingPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(10000.00)));
        assertThat(airFeesBreakdown.getNettFare(), Matchers.comparesEqualTo(BigDecimal.valueOf(13980.00)));
        assertThat(airFeesBreakdown.getMerchantFee(), Matchers.comparesEqualTo(BigDecimal.valueOf(2800)));
        assertThat(airFeesBreakdown.getTotalSellingFare(), Matchers.comparesEqualTo(BigDecimal.valueOf(16780.00)));
        assertThat(airFeesBreakdown.getNettCost(), Matchers.comparesEqualTo(BigDecimal.valueOf(10000.00)));
        assertThat(airFeesBreakdown.getVat(), Matchers.comparesEqualTo(BigDecimal.valueOf(24.50)));
    }

    @Test
    public void shouldRoundAirFeesByFive()
    {
        MerchantFee merchantFee = new MerchantFee();
        merchantFee.setMerchantFeePercent(1.75D);
        AirFeesInput input = new AirFeesInput();
        input.setSellingPrice(new BigDecimal(5000));
        input.setCommission(new BigDecimal(800));
        input.setDiscount(new BigDecimal(70));
        input.setNettFare(new BigDecimal(5000));
        input.setTax1(new BigDecimal(300));
        input.setTax2(new BigDecimal(200));
        input.setClientType("TF");
        AirFeesBreakdown airFeesBreakdown = calculator.calculate(input, merchantFee, countryCode);

        assertThat(airFeesBreakdown.getCommission(), Matchers.comparesEqualTo(BigDecimal.valueOf(800)));
        assertThat(airFeesBreakdown.getSellingPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(5000.00)));
        assertThat(airFeesBreakdown.getNettFare(), Matchers.comparesEqualTo(BigDecimal.valueOf(6230.00)));
        assertThat(airFeesBreakdown.getMerchantFee(), Matchers.comparesEqualTo(BigDecimal.valueOf(110)));
        assertThat(airFeesBreakdown.getTotalSellingFare(), Matchers.comparesEqualTo(BigDecimal.valueOf(6340.00)));
        assertThat(airFeesBreakdown.getNettCost(), Matchers.comparesEqualTo(BigDecimal.valueOf(5000.00)));
    }
}

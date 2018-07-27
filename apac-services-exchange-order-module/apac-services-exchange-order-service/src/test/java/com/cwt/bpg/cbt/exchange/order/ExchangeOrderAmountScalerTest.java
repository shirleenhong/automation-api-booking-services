package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ServiceInfo;

@Ignore
public class ExchangeOrderAmountScalerTest
{
    @InjectMocks
    private ExchangeOrderAmountScaler scaler;

    @Mock
    private ScaleConfig scaleConfig;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldScaleAmounts() {
        ExchangeOrder exchangeOrder = mock(ExchangeOrder.class);
        ServiceInfo serviceInfo = mock(ServiceInfo.class);
        exchangeOrder.setServiceInfo(serviceInfo);

        scaler.scale(exchangeOrder);

        verify(scaleConfig, times(1)).getScale(anyString());
        verify(exchangeOrder, times(1)).getServiceInfo().setCommission(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setMerchantFee(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setNettCost(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setGst(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setTax1(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setTax2(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).setTotal(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setSellingPrice(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).getServiceInfo().setTotalSellingPrice(any(BigDecimal.class));
    }
}

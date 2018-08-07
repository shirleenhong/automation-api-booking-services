package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaServiceInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ServiceInfo;

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
        Mockito.when(exchangeOrder.getServiceInfo()).thenReturn(mock(ServiceInfo.class));
        
        scaler.scale(exchangeOrder);

        verify(scaleConfig, times(1)).getScale(anyString());
        verify(exchangeOrder.getServiceInfo(), times(1)).setCommission(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setMerchantFeeAmount(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setNettCost(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setGstAmount(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setTax1(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setTax2(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).setTotal(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setSellingPrice(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setTotalSellingPrice(any(BigDecimal.class));
    }

    @Test
    public void scaleCanHandleNullServiceInfo() {

        ExchangeOrder exchangeOrder = mock(ExchangeOrder.class);
        Mockito.when(exchangeOrder.getServiceInfo()).thenReturn(null);

        scaler.scale(exchangeOrder);

        verify(scaleConfig, times(1)).getScale(anyString());
    }

    @Test
    public void shouldScaleAmountsForIndia() {

        IndiaExchangeOrder exchangeOrder = mock(IndiaExchangeOrder.class);
        Mockito.when(exchangeOrder.getCountryCode()).thenReturn(Country.INDIA.getCode());
        Mockito.when(exchangeOrder.getServiceInfo()).thenReturn(mock(IndiaServiceInfo.class));

        scaler.scale(exchangeOrder);

        verify(scaleConfig, times(1)).getScale(anyString());
        verify(exchangeOrder.getServiceInfo(), times(1)).setCommission(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setMerchantFeeAmount(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setNettCost(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setGstAmount(any(BigDecimal.class));
        verify(exchangeOrder, times(1)).setTotal(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setSellingPrice(any(BigDecimal.class));
        verify(exchangeOrder.getServiceInfo(), times(1)).setTotalSellingPrice(any(BigDecimal.class));
    }

    @Test
    public void scaleForIndiaCanHandleNullServiceInfo() {

        IndiaExchangeOrder exchangeOrder = mock(IndiaExchangeOrder.class);
        Mockito.when(exchangeOrder.getCountryCode()).thenReturn(Country.INDIA.getCode());
        Mockito.when(exchangeOrder.getServiceInfo()).thenReturn(null);

        scaler.scale(exchangeOrder);

        verify(scaleConfig, times(1)).getScale(anyString());
    }
}

package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;
import com.cwt.bpg.cbt.exchange.order.model.ServiceInfo;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public class ExchangeOrderUpdateServiceTest
{

    @InjectMocks
    private ExchangeOrderUpdateService service;

    @Mock
    private ExchangeOrderRepository repository;

    @Mock
    private ExchangeOrderAmountScaler scaler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doNothing().when(scaler).scale(any(ExchangeOrder.class));
    }

    @Test
    public void shouldCallUpdateExchangeOrder() throws ExchangeOrderNoContentException {

        String eoNumber = "1234567890";
        BigDecimal oldGstAmount = new BigDecimal(50);
        ExchangeOrder existingExchangeOrder = createExchangeOrder(eoNumber, oldGstAmount);
        BigDecimal newGstAmount = new BigDecimal(75);
        ExchangeOrder exchangeOrder = createExchangeOrder(eoNumber, newGstAmount);

        when(repository.getExchangeOrder(eoNumber)).thenReturn(existingExchangeOrder);
        when(repository.save(existingExchangeOrder)).thenReturn(eoNumber);

        ExchangeOrder updatedExchangeOrder = service.update(exchangeOrder);

        assertThat(updatedExchangeOrder.getServiceInfo().getGstAmount().doubleValue(), is(equalTo(75d)));
        assertThat(updatedExchangeOrder.getUpdateDateTime(), is(notNullValue()));

        InOrder inOrder = Mockito.inOrder(repository, scaler);
        inOrder.verify(repository, times(1)).getExchangeOrder(eoNumber);
        inOrder.verify(scaler, times(1)).scale(existingExchangeOrder);
        inOrder.verify(repository, times(1)).save(existingExchangeOrder);
    }

    @Test(expected = ExchangeOrderNoContentException.class)
    public void shouldThrowExceptionWhenEoDoesNotExist() throws ExchangeOrderNoContentException {

        String eoNumber = "1234567890";
        BigDecimal newGstAmount = new BigDecimal(75);
        ExchangeOrder exchangeOrder = createExchangeOrder(eoNumber, newGstAmount);

        when(repository.getExchangeOrder(eoNumber)).thenReturn(null);

        service.update(exchangeOrder);
    }

    private ExchangeOrder createExchangeOrder(String eoNumber, BigDecimal gstAmount) {
        Vendor vendor = new Vendor();
        vendor.setCode("022000");

        ExchangeOrder eo = new ExchangeOrder();
        eo.setEoNumber(eoNumber);
        eo.setCountryCode("HK");
        eo.setProductCode("1");
        eo.setVendor(vendor);
        eo.setServiceInfo(new ServiceInfo());
        eo.getServiceInfo().setGstAmount(gstAmount);
        eo.getServiceInfo().setFormOfPayment(new FormOfPayment());
        return eo;
    }
}

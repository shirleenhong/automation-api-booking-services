package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;

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

        when(repository.getExchangeOrder(exchangeOrder.getCountryCode(), eoNumber)).thenReturn(existingExchangeOrder);
        when(repository.save(exchangeOrder)).thenReturn(eoNumber);

        ExchangeOrder updatedExchangeOrder = (ExchangeOrder) service.update(exchangeOrder);

        InOrder inOrder = Mockito.inOrder(repository, scaler);
        inOrder.verify(repository, times(1)).getExchangeOrder(exchangeOrder.getCountryCode(),eoNumber);
        inOrder.verify(scaler, times(1)).scale(exchangeOrder);
        inOrder.verify(repository, times(1)).save(exchangeOrder);
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
        eo.getServiceInfo().setGst(gstAmount);
        eo.getServiceInfo().setFormOfPayment(new FormOfPayment());
        return eo;
    }
}

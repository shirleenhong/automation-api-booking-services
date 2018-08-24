package com.cwt.bpg.cbt.exchange.order.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.*;

public class FopTypeValidatorTest {

    @InjectMocks
    private FopTypeValidator validator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fopTypeCcWithCreditCardShouldNotThrowException() {
    	
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setServiceInfo(new ServiceInfo());
        exchangeOrder.getServiceInfo().setFormOfPayment(new FormOfPayment());
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopType.CREDIT_CARD);
        CreditCard creditCard = new CreditCard();
        exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fopTypeCcWithoutCreditCardShouldThrowException() {
    	
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setServiceInfo(new ServiceInfo());
        exchangeOrder.getServiceInfo().setFormOfPayment(new FormOfPayment());
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopType.CWT);

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithCreditCardShouldNotThrowException() {
    	
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setServiceInfo(new ServiceInfo());
        exchangeOrder.getServiceInfo().setFormOfPayment(new FormOfPayment());
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopType.INVOICE);
        CreditCard creditCard = new CreditCard();
        exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithoutCreditCardShouldNotThrowException() {
    	
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setServiceInfo(new ServiceInfo());
        exchangeOrder.getServiceInfo().setFormOfPayment(new FormOfPayment());
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopType.INVOICE);

        validator.validate(exchangeOrder);
    }
}

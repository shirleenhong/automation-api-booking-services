package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@Ignore
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
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopTypes.CREDIT_CARD);
        CreditCard creditCard = new CreditCard();
        exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fopTypeCcWithoutCreditCardShouldThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopTypes.CWT);

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithCreditCardShouldNotThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopTypes.INVOICE);
        CreditCard creditCard = new CreditCard();
        exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithoutCreditCardShouldNotThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.getServiceInfo().getFormOfPayment().setFopType(FopTypes.INVOICE);

        validator.validate(exchangeOrder);
    }
}
package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.CreditCard;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


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
        exchangeOrder.setFopType(FopTypes.CREDIT_CARD.getCode());
        CreditCard creditCard = new CreditCard();
        exchangeOrder.setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fopTypeCcWithoutCreditCardShouldThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setFopType(FopTypes.CWT.getCode());

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithCreditCardShouldNotThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setFopType(FopTypes.INVOICE.getCode());
        CreditCard creditCard = new CreditCard();
        exchangeOrder.setCreditCard(creditCard);

        validator.validate(exchangeOrder);
    }

    @Test
    public void fopTypeNonCcWithoutCreditCardShouldNotThrowException() {
        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setFopType(FopTypes.INVOICE.getCode());

        validator.validate(exchangeOrder);
    }
}
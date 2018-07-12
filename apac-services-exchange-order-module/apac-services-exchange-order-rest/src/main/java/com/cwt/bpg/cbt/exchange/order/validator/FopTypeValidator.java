package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FopTypeValidator {

    private List<String> creditCardTypes = Arrays.asList(FopTypes.CWT.getCode(),FopTypes.CREDIT_CARD.getCode());

    public void validate(ExchangeOrder input) {

        if (creditCardTypes.contains(input.getFopType()) && input.getCreditCard() == null ) {

            throw new IllegalArgumentException("Credit Card required for FopType " + input.getFopType());
        }
    }
}

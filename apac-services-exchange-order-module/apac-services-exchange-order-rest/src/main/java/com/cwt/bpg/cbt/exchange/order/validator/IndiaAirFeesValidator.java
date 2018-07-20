package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class IndiaAirFeesValidator extends CompositeValidator {
    public IndiaAirFeesValidator() {
        super();
        setValidators(Arrays.asList(
                new AirlineOverheadCommissionValidator(),
                new FeeValidator(),
                new OthTaxValidator()));
    }
}

package com.cwt.bpg.cbt.exchange.order.validator;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

@Component
public class IndiaAirFeesValidator extends CompositeValidator<Validator<IndiaAirFeesInput>, IndiaAirFeesInput> {
    public IndiaAirFeesValidator() {
        super();
        setValidators(Arrays.asList(
                new AirlineOverheadCommissionValidator(),
                new FeeValidator(),
                new OthTaxValidator()));
    }
}

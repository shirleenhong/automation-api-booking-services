package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import org.springframework.stereotype.Component;

@Component
public class FeeValidator {

    public void validate(IndiaAirFeesInput input) {

        if (input.isFeeOverride() && input.getFee() == null) {

            throw new IllegalArgumentException("Fee required if feeOverride true.");
        }
    }
}

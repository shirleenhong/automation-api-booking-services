package com.cwt.bpg.cbt.exchange.order.validator;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import org.springframework.stereotype.Component;

@Component
public class AirlineOverheadCommissionValidator {

    public void validate(IndiaAirFeesInput input) {

        if (input.isAirlineOverheadCommissionByPercent() && input.getAirlineOverheadCommissionPercent() == null) {

            throw new IllegalArgumentException("AirlineOverheadCommissionPercent required if AirlineOverheadCommissionByPercent true.");
        }
        else if (!input.isAirlineOverheadCommissionByPercent() && input.getAirlineOverheadCommission() == null) {

            throw new IllegalArgumentException("AirlineOverheadCommission required if AirlineOverheadCommissionByPercent false.");
        }
    }
}

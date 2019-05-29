package com.cwt.bpg.cbt.exchange.order.validator;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class IndiaAirFeesValidatorTest
{

    private IndiaAirFeesValidator validator = new IndiaAirFeesValidator();

    @Test
    public void shouldInvokeAllValidators() {

        List<Validator<IndiaAirFeesInput>> validators = validator.getValidators();

        assertThat(validators, hasSize(3));
        assertThat(validators, hasItems(
                instanceOf(AirlineOverheadCommissionValidator.class),
                instanceOf(FeeValidator.class),
                instanceOf(OthTaxValidator.class)));
    }
}

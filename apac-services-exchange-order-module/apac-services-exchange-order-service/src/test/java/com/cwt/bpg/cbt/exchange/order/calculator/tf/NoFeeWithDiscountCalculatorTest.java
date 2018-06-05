package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

public class NoFeeWithDiscountCalculatorTest {

    private NoFeeWithDiscountCalculator noFeeWithDiscountCalculator = new NoFeeWithDiscountCalculator();

	@Test
	public void getMfOnTfShouldReturnNullIfTripTypeInt() {
        InAirFeesInput input = new InAirFeesInput();
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
		assertNull(noFeeWithDiscountCalculator.getMfOnTf(input, new InAirFeesBreakdown(), new BigDecimal(1)));
	}

    @Test
    public void getMfOnTfShouldReturnNotNullIfTripTypeNotInt() {
        InAirFeesInput input = new InAirFeesInput();
        assertNotNull(noFeeWithDiscountCalculator.getMfOnTf(input, new InAirFeesBreakdown(), new BigDecimal(1)));
    }
}

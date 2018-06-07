package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

public class NoFeeWithDiscountCalculatorTest {

    private NoFeeWithDiscountCalculator noFeeWithDiscountCalculator = new NoFeeWithDiscountCalculator();

	@Test
	public void getMfOnTfShouldReturnNullIfTripTypeInt() {
        IndiaAirFeesInput input = new IndiaAirFeesInput();
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
		assertNull(noFeeWithDiscountCalculator.getMfOnTf(input, new IndiaAirFeesBreakdown(), new BigDecimal(1)));
	}

    @Test
    public void getMfOnTfShouldReturnNotNullIfTripTypeNotInt() {
        IndiaAirFeesInput input = new IndiaAirFeesInput();
        assertNotNull(noFeeWithDiscountCalculator.getMfOnTf(input, new IndiaAirFeesBreakdown(), new BigDecimal(1)));
    }
}

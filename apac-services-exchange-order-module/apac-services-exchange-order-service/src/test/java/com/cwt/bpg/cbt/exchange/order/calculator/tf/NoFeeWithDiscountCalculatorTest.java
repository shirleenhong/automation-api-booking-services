package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
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
        input.setMerchantFeePercent(2D);
        IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
        breakdown.setFee(new BigDecimal(1000));
        
        BigDecimal result = noFeeWithDiscountCalculator.getMfOnTf(input, breakdown, new BigDecimal(2)); 
        assertEquals(new BigDecimal(20).setScale(2), result);
    }
}

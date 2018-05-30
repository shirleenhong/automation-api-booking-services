package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoFeeWithDiscountCalculatorTest {

    private NoFeeWithDiscountCalculator noFeeWithDiscountCalculator = new NoFeeWithDiscountCalculator();

	@Test
	public void getMfOnTfShouldReturnNullIfTripTypeInt() {
        TransactionFeesInput input = new TransactionFeesInput();
        input.setTripType(TripTypes.INTERNATIONAL.toString());
		assertNull(noFeeWithDiscountCalculator.getMfOnTf(input, new BigDecimal(1)));
	}

    @Test
    public void getMfOnTfShouldReturnNotNullIfTripTypeNotInt() {
        TransactionFeesInput input = new TransactionFeesInput();
        assertNotNull(noFeeWithDiscountCalculator.getMfOnTf(input, new BigDecimal(1)));
    }
}

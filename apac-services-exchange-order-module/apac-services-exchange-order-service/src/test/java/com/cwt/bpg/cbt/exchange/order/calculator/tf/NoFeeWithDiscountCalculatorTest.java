package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.TripTypes;

public class NoFeeWithDiscountCalculatorTest {

    private NoFeeWithDiscountCalculator noFeeWithDiscountCalculator = new NoFeeWithDiscountCalculator();

	@Test
	public void getMfOnTfShouldReturnNullIfTripTypeInt() {
        TransactionFeesInput input = new TransactionFeesInput();
        input.setTripType(TripTypes.INTERNATIONAL.getCode());
		assertNull(noFeeWithDiscountCalculator.getMfOnTf(input, new TransactionFeesBreakdown(), new BigDecimal(1)));
	}

    @Test
    public void getMfOnTfShouldReturnNotNullIfTripTypeNotInt() {
        TransactionFeesInput input = new TransactionFeesInput();
        assertNotNull(noFeeWithDiscountCalculator.getMfOnTf(input, new TransactionFeesBreakdown(), new BigDecimal(1)));
    }
}

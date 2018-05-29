package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoFeeWithDiscountCalculatorTest {

    private NoFeeWithDiscountCalculator noFeeWithDiscountCalculator = new NoFeeWithDiscountCalculator();

	@Test
	public void getMfOnTfShouldReturnNullIfTripTypeInt() {
        TransactionFeesInput input = new TransactionFeesInput();
		assertNull(noFeeWithDiscountCalculator.getMfOnTf(1, input, new BigDecimal(1)));
	}

    @Test
    public void getMfOnTfShouldReturnNotNullIfTripTypeNotInt() {
        TransactionFeesInput input = new TransactionFeesInput();
        assertNotNull(noFeeWithDiscountCalculator.getMfOnTf(2, input, new BigDecimal(1)));
    }
}

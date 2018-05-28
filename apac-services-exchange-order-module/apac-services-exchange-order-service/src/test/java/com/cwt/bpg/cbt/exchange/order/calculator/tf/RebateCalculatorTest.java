package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class RebateCalculatorTest {
    private RebateCalculator rebateCalc = new RebateCalculator();

    @Test
    public void getIntMfFeeTFShouldReturnNull() {
        assertNull(rebateCalc.getIntMfFeeTF());
    }

    @Test
    public void getIntDdlFeeApplyShouldReturnNull() {
        assertNull(rebateCalc.getIntDdlFeeApply());
    }

    @Test
    public void getIntTotalChargeShouldReturnNotNull() {
        assertNotNull(rebateCalc.getIntTotalCharge(1d,1d,1d,1d,1d,1d));
    }
}

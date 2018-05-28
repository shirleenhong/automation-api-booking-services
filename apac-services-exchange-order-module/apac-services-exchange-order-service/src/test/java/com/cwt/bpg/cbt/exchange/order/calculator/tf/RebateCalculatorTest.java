package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
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
        Double totalSellFare = 1d;
        Double totalDiscount = 1d;
        Double totalGST = 1d;
        Double totalMerchantFee = 1d;
        Double fee = 1d;
        Double totalTaxes = 1d;

        Double expectedResult = totalSellFare - totalDiscount + totalGST + totalMerchantFee + fee + totalTaxes;
        Double actualResult = rebateCalc.getIntTotalCharge(1d, 1d, 1d, 1d, 1d, 1d);

        assertNotNull(actualResult);
        assertEquals(actualResult, expectedResult);
    }
}

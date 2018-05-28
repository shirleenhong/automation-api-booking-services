package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RebateCalculatorTest {
    private RebateCalculator rebateCalc = new RebateCalculator();

    @Test
    public void getIntMfFeeTFShouldReturnNull() {
        assertNull(rebateCalc.getMfFeeTF());
    }

    @Test
    public void getIntDdlFeeApplyShouldReturnNull() {
        assertNull(rebateCalc.getDdlFeeApply());
    }

    @Test
    public void getIntTotalChargeShouldReturnNotNull() {
        BigDecimal totalSellFare = new BigDecimal(1),
                totalDiscount = new BigDecimal(1),
                totalGST = new BigDecimal(1),
                totalMerchantFee = new BigDecimal(1),
                fee = new BigDecimal(1),
                totalTaxes = new BigDecimal(1);

        BigDecimal expectedResult = totalSellFare.subtract(totalDiscount).add(totalGST).add(totalMerchantFee).add(fee).add(totalTaxes);
        BigDecimal actualResult = rebateCalc.getTotalCharge(totalSellFare, totalDiscount, totalGST, totalMerchantFee, fee, totalTaxes);

        assertNotNull(actualResult);
        assertEquals(actualResult, expectedResult);
    }
}

package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;

public class RebateCalculatorTest {
    private RebateCalculator rebateCalc = new RebateCalculator();

    @Test
    public void getIntMfFeeTFShouldReturnNull() {
        assertNull(rebateCalc.getMfOnTf(null, null, BigDecimal.ZERO));
    }

    @Test
    public void getIntDdlFeeApplyShouldReturnNull() {
        assertNull(rebateCalc.getDdlFeeApply());
    }

    @Test
    public void getIntTotalChargeShouldReturnNotNull() {
    	
    	TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalTaxes(new BigDecimal(10));
    	breakdown.setFee(new BigDecimal(50));    	
    	
        BigDecimal actualResult = rebateCalc.getTotalCharge(breakdown);

        assertEquals(new BigDecimal(160), actualResult);
    }
}

package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class RebateCalculatorTest {
    private RebateCalculator rebateCalc = new RebateCalculator();

    @Test
    public void getMfOnTfShouldReturnNull() {
        assertNull(rebateCalc.getMfOnTf(0, null, BigDecimal.ZERO));
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
    	
    	TransactionFeesInput input = new TransactionFeesInput();
    	input.setFee(new BigDecimal(50));    	
    	
        BigDecimal actualResult = rebateCalc.getTotalCharge(input, breakdown);

        assertEquals(new BigDecimal(160), actualResult);
    }
}

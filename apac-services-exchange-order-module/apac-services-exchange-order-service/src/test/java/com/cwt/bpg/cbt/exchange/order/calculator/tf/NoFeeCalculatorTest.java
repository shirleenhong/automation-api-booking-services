package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.TransactionFeesInput;

public class NoFeeCalculatorTest {
    private NoFeeCalculator noFeeCalc = new NoFeeCalculator();

    @Test
    public void getTotalDiscountShouldReturnNull() {
        assertNull(noFeeCalc.getTotalDiscount(0, null));
    }

    @Test
    public void getTotalOrComShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOrCom(0, null));
    }

    @Test
    public void getTotalOrCom2ShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOrCom2(0, null));
    }

    @Test
    public void getMfFeeShouldReturnNotNull() {
    	assertNotNull(noFeeCalc.getMerchantFee(
    			new TransactionFeesInput(), new TransactionFeesBreakdown()));
    }

    @Test
    public void getMfFeeTfShouldReturnNull() {
        assertNull(noFeeCalc.getMfOnTf(0, null, BigDecimal.ZERO));
    }

    @Test
    public void getDdlFeeApplyShouldReturnNull() {
        assertNull(noFeeCalc.getDdlFeeApply());
    }

    @Test
    public void getTotalFeeShouldReturnNull() {
        assertNull(noFeeCalc.getTotalFee(new TransactionFeesInput(),new TransactionFeesBreakdown()));
    }

    @Test
    public void getTotalSellingFareShouldReturnNotNull() {
    	
    	TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	
        assertNotNull(noFeeCalc.getTotalSellingFare(breakdown));
    }

    @Test
    public void getTotalChargeShouldReturnNotNull() {

    	TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	
        assertNotNull(noFeeCalc.getTotalCharge(null, breakdown));
    }
}

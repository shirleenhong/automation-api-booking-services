package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.IndiaAirFeesInput;

public class NoFeeCalculatorTest {
    private NoFeeCalculator noFeeCalc = new NoFeeCalculator();

    @Test
    public void getTotalDiscountShouldReturnNull() {
        assertNull(noFeeCalc.getTotalDiscount(null, null));
    }

    @Test
    public void getTotalOrComShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOverheadCommission(null, null));
    }

    @Test
    public void getTotalOrCom2ShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOverheadCommission2(null));
    }

    @Test
    public void getMerchantFeeShouldReturnNotNull() {
    	assertNotNull(noFeeCalc.getMerchantFee(
    			new IndiaAirFeesInput(), new IndiaAirFeesBreakdown()));
    }

    @Test
    public void getMfFeeTfShouldReturnNull() {
        assertNull(noFeeCalc.getMfOnTf(null, null, BigDecimal.ZERO));
    }

    @Test
    public void getDdlFeeApplyShouldReturnNull() {
        assertFalse(noFeeCalc.getDdlFeeApply());
    }

    @Test
    public void getTotalFeeShouldReturnNull() {
        assertNull(noFeeCalc.getTotalFee(new IndiaAirFeesInput(), new IndiaAirFeesBreakdown(), BigDecimal.ZERO));
    }

    @Test
    public void getTotalSellingFareShouldReturnNotNull() {
    	
    	IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	
        assertEquals(new BigDecimal(160), noFeeCalc.getTotalSellingFare(breakdown));
    }

    @Test
    public void getTotalChargeShouldReturnNotNull() {

    	IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	breakdown.setTotalTaxes(new BigDecimal(1));

        assertEquals(new BigDecimal(161), noFeeCalc.getTotalCharge(breakdown));
    }
}

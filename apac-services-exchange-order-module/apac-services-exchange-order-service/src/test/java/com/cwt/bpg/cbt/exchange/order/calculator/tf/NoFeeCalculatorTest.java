package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InAirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.InAirFeesInput;

public class NoFeeCalculatorTest {
    private NoFeeCalculator noFeeCalc = new NoFeeCalculator();

    @Test
    public void getTotalDiscountShouldReturnNull() {
        assertNull(noFeeCalc.getTotalDiscount(null, null));
    }

    @Test
    public void getTotalOrComShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOverheadCommission(null));
    }

    @Test
    public void getTotalOrCom2ShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOrCom2(null));
    }

    @Test
    public void getMerchantFeeShouldReturnNotNull() {
    	assertNotNull(noFeeCalc.getMerchantFee(
    			new InAirFeesInput(), new InAirFeesBreakdown()));
    }

    @Test
    public void getMfFeeTfShouldReturnNull() {
        assertNull(noFeeCalc.getMfOnTf(null, null, BigDecimal.ZERO));
    }

    @Test
    public void getDdlFeeApplyShouldReturnNull() {
        assertNull(noFeeCalc.getDdlFeeApply());
    }

    @Test
    public void getTotalFeeShouldReturnNull() {
        assertNull(noFeeCalc.getTotalFee(new InAirFeesInput(), new InAirFeesBreakdown()));
    }

    @Test
    public void getTotalSellingFareShouldReturnNotNull() {
    	
    	InAirFeesBreakdown breakdown = new InAirFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	
        assertNotNull(noFeeCalc.getTotalSellingFare(breakdown));
    }

    @Test
    public void getTotalChargeShouldReturnNotNull() {

    	InAirFeesBreakdown breakdown = new InAirFeesBreakdown();
    	breakdown.setTotalSellFare(new BigDecimal(100));
    	breakdown.setTotalGst(new BigDecimal(50));
    	breakdown.setTotalMerchantFee(new BigDecimal(10));
    	
        assertNotNull(noFeeCalc.getTotalCharge(breakdown));
    }
}

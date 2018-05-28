package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoFeeCalculatorTest {
    private NoFeeCalculator noFeeCalc = new NoFeeCalculator();

    @Test
    public void getTotalDiscountShouldReturnNull(){
        assertNull(noFeeCalc.getTotalDiscount());
    }

    @Test
    public void getTotalOrComShouldReturnNull(){
        assertNull(noFeeCalc.getTotalOrCom());
    }

    @Test
    public void getTotalOrCom2ShouldReturnNull(){
        assertNull(noFeeCalc.getTotalOrCom2());
    }

    @Test
    public void getMfFeeShouldReturnNotNull(){
        assertNotNull(noFeeCalc.getMfFee(1d,1d,1d,1d));
    }

    @Test
    public void getMfFeeTfShouldReturnNull(){
        assertNull(noFeeCalc.getMfFeeTf());
    }

    @Test
    public void getDdlFeeApplyShouldReturnNull(){
        assertNull(noFeeCalc.getDdlFeeApply());
    }

    @Test
    public void getTotalFeeShouldReturnNull(){
        assertNull(noFeeCalc.getTotalFee());
    }

    @Test
    public void getTotalSellingFareShouldReturnNotNull(){
        assertNotNull(noFeeCalc.getTotalSellingFare(1d,1d,1d));
    }

    @Test
    public void getTotalChargeShouldReturnNotNull(){
        assertNotNull(noFeeCalc.getTotalCharge(1d,1d,1d,1d));
    }
}

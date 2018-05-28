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
    public void getTotalORComShouldReturnNull(){
        assertNull(noFeeCalc.getTotalORCom());
    }

    @Test
    public void getTotalORCom2ShouldReturnNull(){
        assertNull(noFeeCalc.getTotalORCom2());
    }

    @Test
    public void getMFFeeShouldReturnNotNull(){
        assertNotNull(noFeeCalc.getMFFee(1d,1d,1d,1d));
    }

    @Test
    public void getMFFeeTFShouldReturnNull(){
        assertNull(noFeeCalc.getMFFeeTF());
    }

    @Test
    public void getDDLFeeApplyShouldReturnNull(){
        assertNull(noFeeCalc.getDDLFeeApply());
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

package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoFeeCalculatorTest {
    private NoFeeCalculator noFeeCalc = new NoFeeCalculator();

    @Test
    public void getTotalDiscountShouldReturnNull() {
        assertNull(noFeeCalc.getTotalDiscount());
    }

    @Test
    public void getTotalOrComShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOrCom());
    }

    @Test
    public void getTotalOrCom2ShouldReturnNull() {
        assertNull(noFeeCalc.getTotalOrCom2());
    }

    @Test
    public void getMfFeeShouldReturnNotNull() {
        assertNotNull(noFeeCalc.getMerchantFee(new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), 1d));
    }

    @Test
    public void getMfFeeTfShouldReturnNull() {
        assertNull(noFeeCalc.getMfOnTf());
    }

    @Test
    public void getDdlFeeApplyShouldReturnNull() {
        assertNull(noFeeCalc.getDdlFeeApply());
    }

    @Test
    public void getTotalFeeShouldReturnNull() {
        assertNull(noFeeCalc.getTotalFee());
    }

    @Test
    public void getTotalSellingFareShouldReturnNotNull() {
        assertNotNull(noFeeCalc.getTotalSellingFare(new BigDecimal(1), new BigDecimal(1), new BigDecimal(1)));
    }

    @Test
    public void getTotalChargeShouldReturnNotNull() {
        assertNotNull(noFeeCalc.getTotalCharge(new BigDecimal(1), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1)));
    }
}

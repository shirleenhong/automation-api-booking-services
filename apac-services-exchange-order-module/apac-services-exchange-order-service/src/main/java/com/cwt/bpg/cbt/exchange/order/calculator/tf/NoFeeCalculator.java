package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

public class NoFeeCalculator extends TransactionFeeCalculator {
    public BigDecimal getTotalDiscount() {
        return null;
    }

    public Double getTotalOrCom() {
        return null;
    }

    public Double getTotalOrCom2() {
        return null;
    }

    public BigDecimal getMerchantFee(BigDecimal totalSellFare, BigDecimal totalTaxes, BigDecimal totalGst, Double merchantFeePct) {
        return calculatePercentage(safeValue(totalSellFare).add(safeValue(totalTaxes)).add(safeValue(totalGst)), merchantFeePct);
    }

    public BigDecimal getMfOnTf() {
        return null;
    }

    public Double getDdlFeeApply() {
        return null;
    }

    public BigDecimal getTotalFee() {
        return null;
    }

    public BigDecimal getTotalSellingFare(BigDecimal totalSellFare, BigDecimal totalGst, BigDecimal totalMerchantFee) {
        return safeValue(totalSellFare).add(safeValue(totalGst)).add(safeValue(totalMerchantFee));
    }

    public BigDecimal getTotalCharge(BigDecimal totalSellFare, BigDecimal totalGst, BigDecimal totalMerchantFee, BigDecimal totalTaxes) {
        return safeValue(totalSellFare).add(safeValue(totalGst)).add(safeValue(totalMerchantFee)).add(safeValue(totalTaxes));
    }

}

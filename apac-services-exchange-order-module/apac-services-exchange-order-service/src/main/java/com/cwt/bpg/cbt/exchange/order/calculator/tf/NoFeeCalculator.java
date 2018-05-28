package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

public class NoFeeCalculator extends TransactionFeeCalculator {
    public Double getTotalDiscount() {
        return null;
    }

    public Double getTotalOrCom() {
        return null;
    }

    public Double getTotalOrCom2() {
        return null;
    }

    public BigDecimal getMerchantFee(BigDecimal totalSellFare, BigDecimal totalTaxes, BigDecimal totalGST, Double merchantFeePct) {
        return calculatePercentage(safeValue(totalSellFare).add(safeValue(totalTaxes)).add(safeValue(totalGST)), merchantFeePct);
    }

    public Double getMfTf() {
        return null;
    }

    public Double getDdlFeeApply() {
        return null;
    }

    public Double getTotalFee() {
        return null;
    }

    public BigDecimal getTotalSellingFare(BigDecimal totalSellFare, BigDecimal totalGST, BigDecimal totalMerchantFee) {
        return safeValue(totalSellFare).add(safeValue(totalGST)).add(safeValue(totalMerchantFee));
    }

    public BigDecimal getTotalCharge(BigDecimal totalSellFare, BigDecimal totalGST, BigDecimal totalMerchantFee, BigDecimal totalTaxes) {
        return safeValue(totalSellFare).add(safeValue(totalGST)).add(safeValue(totalMerchantFee)).add(safeValue(totalTaxes));
    }

}

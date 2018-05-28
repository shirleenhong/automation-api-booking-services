package com.cwt.bpg.cbt.exchange.order.calculator.tf;

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

    public Double getMfFee(Double totalSellFare, Double totalTaxes, Double totalGST, Double merchantFee) {
        return (totalSellFare + totalTaxes + totalGST) * merchantFee;
    }

    public Double getMfFeeTf() {
        return null;
    }

    public Double getDdlFeeApply() {
        return null;
    }

    public Double getTotalFee() {
        return null;
    }

    public Double getTotalSellingFare(Double totalSellFare, Double totalGST, Double totalMerchantFee) {
        return totalSellFare + totalGST + totalMerchantFee;
    }

    public Double getTotalCharge(Double totalSellFare, Double totalGST, Double totalMerchantFee, Double totalTaxes) {
        return totalSellFare + totalGST + totalMerchantFee + totalTaxes;
    }

}

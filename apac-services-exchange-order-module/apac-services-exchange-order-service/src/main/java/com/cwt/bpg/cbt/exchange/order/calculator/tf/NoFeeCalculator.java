package com.cwt.bpg.cbt.exchange.order.calculator.tf;

public class NoFeeCalculator extends TransactionFeeCalculator {
    public Double getTotalDiscount() {
        return null;
    }

    public Double getTotalORCom() {
        return null;
    }

    public Double getTotalORCom2() {
        return null;
    }

    public Double getMFFee(Double totalSellFare, Double totalTaxes, Double totalGST, Double merchantFee) {
        return (totalSellFare + totalTaxes + totalGST) * merchantFee;
    }

    public Double getMFFeeTF() {
        return null;
    }

    public Double getDDLFeeApply() {
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

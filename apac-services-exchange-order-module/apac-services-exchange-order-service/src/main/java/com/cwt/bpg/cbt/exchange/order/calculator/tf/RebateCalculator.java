package com.cwt.bpg.cbt.exchange.order.calculator.tf;

public class RebateCalculator extends TransactionFeeCalculator {

    public Double getIntMfFeeTF() {
        return null;
    }

    public Double getIntDdlFeeApply() {
        return null;
    }

    public Double getIntTotalCharge(Double totalSellFare, Double totalDiscount,
                                    Double totalGST, Double totalMerchantFee, Double fee, Double totalTaxes) {
        return safeValue(totalSellFare) - safeValue(totalDiscount) + safeValue(totalGST) +
                safeValue(totalMerchantFee) + safeValue(fee) + safeValue(totalTaxes);
    }
}

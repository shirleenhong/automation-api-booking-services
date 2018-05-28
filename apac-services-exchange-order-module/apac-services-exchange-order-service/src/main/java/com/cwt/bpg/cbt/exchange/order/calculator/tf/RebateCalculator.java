package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

public class RebateCalculator extends TransactionFeeCalculator {

    public BigDecimal getMfOnTf() {
        return null;
    }

    public Boolean getDdlFeeApply() {
        return null;
    }

    public BigDecimal getTotalCharge(BigDecimal totalSellFare, BigDecimal totalDiscount,
                                     BigDecimal totalGst, BigDecimal totalMerchantFee, BigDecimal fee, BigDecimal totalTaxes) {
        return safeValue(totalSellFare).subtract(safeValue(totalDiscount)).add(safeValue(totalGst))
                .add(safeValue(totalMerchantFee)).add(safeValue(fee)).add(safeValue(totalTaxes));
    }
}

package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

public class RebateCalculator extends TransactionFeeCalculator {

    public BigDecimal getMfFeeTF() {
        return null;
    }

    public BigDecimal getDdlFeeApply() {
        return null;
    }

    public BigDecimal getTotalCharge(BigDecimal totalSellFare, BigDecimal totalDiscount,
                                     BigDecimal totalGST, BigDecimal totalMerchantFee, BigDecimal fee, BigDecimal totalTaxes) {
        return safeValue(totalSellFare).subtract(safeValue(totalDiscount)).add(safeValue(totalGST))
                .add(safeValue(totalMerchantFee)).add(safeValue(fee)).add(safeValue(totalTaxes));
    }
}

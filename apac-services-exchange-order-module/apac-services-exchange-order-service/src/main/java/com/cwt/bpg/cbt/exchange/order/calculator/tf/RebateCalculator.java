package com.cwt.bpg.cbt.exchange.order.calculator.tf;

public class RebateCalculator extends TransactionFeeCalculator {

    public Double getIntMfFeeTF(){ return null; }

    public Double getIntDdlFeeApply(){ return null; }

    public Double getIntTotalCharge(Double totalSellFare, Double totalDiscount,
           Double totalGST, Double totalMerchantFee, Double fee, Double totalTaxes){
        return totalSellFare-totalDiscount+totalGST+totalMerchantFee+fee+totalTaxes;
    }
}

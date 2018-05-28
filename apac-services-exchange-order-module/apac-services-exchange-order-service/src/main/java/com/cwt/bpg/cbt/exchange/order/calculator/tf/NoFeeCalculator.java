package com.cwt.bpg.cbt.exchange.order.calculator.tf;

public class NoFeeCalculator extends TransactionFeeCalculator {
    public Double getTotalDiscount(){return null;}
    public Double getTotalORCom(){return null;}
    public Double getTotalORCom2(){return null;}
    public Double getMFFee(Double totalSellFare, Double totalTaxes, Double totalGST, Double merchantFee){
        //({Total Sell Fare}+{Total Taxes}+{Total GST})*{Merchant Fee %}
        return (totalSellFare+totalTaxes+totalGST)*merchantFee;
    }

    public Double getMFFeeTF(){return null;}
    public Double getDDLFeeApply(){/*TODO return NA; what is NA?*/return null;}
    public Double getTotalFee(){return null;}
    public Double getTotalSellingFare(Double totalSellFare,Double totalGST,Double totalMerchantFee){
        //{Total Sell Fare}+{Total GST}+{Total Merchant Fee}
        return totalSellFare+totalGST+totalMerchantFee;
    }
    public Double getTotalCharge(Double totalSellFare,Double totalGST,Double totalMerchantFee, Double totalTaxes){
        //{Total Sell Fare}+{Total GST}+{Total Merchant Fee}+{Total Taxes}
        return totalSellFare+totalGST+totalMerchantFee+totalTaxes;
    }

}

package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.Valid;

import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;

public class AirCharges implements Serializable {

    private static final long serialVersionUID = -1917788559776695996L;

    private String cwtRefNo;
    private String otherRelatedNo;
    private Boolean reissuedTicket;
    private String issueInExchangeFor;

    private String pricing;
    private String finalDestination;
    private String tripType;

    @Valid
    private FormOfPayment formOfPayment;
    private String uatp;

    private BigDecimal baseFare;
    private BigDecimal yqTax;
    private Tax othTax1;
    private Tax othTax2;
    private Tax othTax3;
    private Double iataCommissionPercent;
    private Double airlineOrCommissionPercent;
    private Boolean airlineOrCommissionPercentFlag;
    private BigDecimal airlineOrCommission;
    private Double returnableOrPercent;
    private Double clientDiscountPercent;
    private Double markUpPercent;
    private Double conditionalMarkUpPercent;
    private Double mfOnAirFareVatGstPercent;
    private Double mfOnAirFareOt1Percent;
    private Double mfOnAirFareOt2Percent;
    private Double mfOnAirFareMerchantFeePercent;
    private BigDecimal mfOnCwtTxFeeOverride;
    private Boolean mfOnCwtTxFeeOverrideFlag;
    private Double mfOnCwtTxFeeMerchantFeePercent;
    private BigDecimal mfOnCwtTxFeeFuelSurcharge;
    private BigDecimal baseAmount;
    private BigDecimal totalIataCommission;
    private BigDecimal totalReturnableOr;
    private BigDecimal totalDiscount;
    private BigDecimal totalMarkup;
    private BigDecimal totalSellFare;
    private BigDecimal totalVatGst;
    private BigDecimal totalMerchantFee;
    private BigDecimal totalSellingFare;
    private BigDecimal totalTax;
    private BigDecimal merchantFeeOnTf;
    private BigDecimal totalCharge;

    public String getCwtRefNo() {
        return cwtRefNo;
    }

    public void setCwtRefNo(String cwtRefNo) {
        this.cwtRefNo = cwtRefNo;
    }

    public String getOtherRelatedNo() {
        return otherRelatedNo;
    }

    public void setOtherRelatedNo(String otherRelatedNo) {
        this.otherRelatedNo = otherRelatedNo;
    }

    public Boolean getReissuedTicket() {
        return reissuedTicket;
    }

    public void setReissuedTicket(Boolean reissuedTicket) {
        this.reissuedTicket = reissuedTicket;
    }

    public String getIssueInExchangeFor() {
        return issueInExchangeFor;
    }

    public void setIssueInExchangeFor(String issueInExchangeFor) {
        this.issueInExchangeFor = issueInExchangeFor;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public String getUatp() {
        return uatp;
    }

    public void setUatp(String uatp) {
        this.uatp = uatp;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public BigDecimal getYqTax() {
        return yqTax;
    }

    public void setYqTax(BigDecimal yqTax) {
        this.yqTax = yqTax;
    }

    public Tax getOthTax1() {
        return othTax1;
    }

    public void setOthTax1(Tax othTax1) {
        this.othTax1 = othTax1;
    }

    public Tax getOthTax2() {
        return othTax2;
    }

    public void setOthTax2(Tax othTax2) {
        this.othTax2 = othTax2;
    }

    public Tax getOthTax3() {
        return othTax3;
    }

    public void setOthTax3(Tax othTax3) {
        this.othTax3 = othTax3;
    }

    public Double getIataCommissionPercent() {
        return iataCommissionPercent;
    }

    public void setIataCommissionPercent(Double iataCommissionPercent) {
        this.iataCommissionPercent = iataCommissionPercent;
    }

    public Double getAirlineOrCommissionPercent() {
        return airlineOrCommissionPercent;
    }

    public void setAirlineOrCommissionPercent(Double airlineOrCommissionPercent) {
        this.airlineOrCommissionPercent = airlineOrCommissionPercent;
    }

    public Double getReturnableOrPercent() {
        return returnableOrPercent;
    }

    public void setReturnableOrPercent(Double returnableOrPercent) {
        this.returnableOrPercent = returnableOrPercent;
    }

    public Double getClientDiscountPercent() {
        return clientDiscountPercent;
    }

    public void setClientDiscountPercent(Double clientDiscountPercent) {
        this.clientDiscountPercent = clientDiscountPercent;
    }

    public Double getMarkUpPercent() {
        return markUpPercent;
    }

    public void setMarkUpPercent(Double markUpPercent) {
        this.markUpPercent = markUpPercent;
    }

    public Double getConditionalMarkUpPercent() {
        return conditionalMarkUpPercent;
    }

    public void setConditionalMarkUpPercent(Double conditionalMarkUpPercent) {
        this.conditionalMarkUpPercent = conditionalMarkUpPercent;
    }

    public Double getMfOnAirFareVatGstPercent() {
        return mfOnAirFareVatGstPercent;
    }

    public void setMfOnAirFareVatGstPercent(Double mfOnAirFareVatGstPercent) {
        this.mfOnAirFareVatGstPercent = mfOnAirFareVatGstPercent;
    }

    public Double getMfOnAirFareOt1Percent() {
        return mfOnAirFareOt1Percent;
    }

    public void setMfOnAirFareOt1Percent(Double mfOnAirFareOt1Percent) {
        this.mfOnAirFareOt1Percent = mfOnAirFareOt1Percent;
    }

    public Double getMfOnAirFareOt2Percent() {
        return mfOnAirFareOt2Percent;
    }

    public void setMfOnAirFareOt2Percent(Double mfOnAirFareOt2Percent) {
        this.mfOnAirFareOt2Percent = mfOnAirFareOt2Percent;
    }

    public Double getMfOnAirFareMerchantFeePercent() {
        return mfOnAirFareMerchantFeePercent;
    }

    public void setMfOnAirFareMerchantFeePercent(Double mfOnAirFareMerchantFeePercent) {
        this.mfOnAirFareMerchantFeePercent = mfOnAirFareMerchantFeePercent;
    }

    public BigDecimal getMfOnCwtTxFeeOverride() {
        return mfOnCwtTxFeeOverride;
    }

    public void setMfOnCwtTxFeeOverride(BigDecimal mfOnCwtTxFeeOverride) {
        this.mfOnCwtTxFeeOverride = mfOnCwtTxFeeOverride;
    }

    public Boolean getMfOnCwtTxFeeOverrideFlag() {
        return mfOnCwtTxFeeOverrideFlag;
    }

    public void setMfOnCwtTxFeeOverrideFlag(Boolean mfOnCwtTxFeeOverrideFlag) {
        this.mfOnCwtTxFeeOverrideFlag = mfOnCwtTxFeeOverrideFlag;
    }

    public Double getMfOnCwtTxFeeMerchantFeePercent() {
        return mfOnCwtTxFeeMerchantFeePercent;
    }

    public void setMfOnCwtTxFeeMerchantFeePercent(Double mfOnCwtTxFeeMerchantFeePercent) {
        this.mfOnCwtTxFeeMerchantFeePercent = mfOnCwtTxFeeMerchantFeePercent;
    }

    public BigDecimal getMfOnCwtTxFeeFuelSurcharge() {
        return mfOnCwtTxFeeFuelSurcharge;
    }

    public void setMfOnCwtTxFeeFuelSurcharge(BigDecimal mfOnCwtTxFeeFuelSurcharge) {
        this.mfOnCwtTxFeeFuelSurcharge = mfOnCwtTxFeeFuelSurcharge;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getTotalIataCommission() {
        return totalIataCommission;
    }

    public void setTotalIataCommission(BigDecimal totalIataCommission) {
        this.totalIataCommission = totalIataCommission;
    }

    public BigDecimal getTotalReturnableOr() {
        return totalReturnableOr;
    }

    public void setTotalReturnableOr(BigDecimal totalReturnableOr) {
        this.totalReturnableOr = totalReturnableOr;
    }

    public BigDecimal getTotalMarkup() {
        return totalMarkup;
    }

    public void setTotalMarkup(BigDecimal totalMarkup) {
        this.totalMarkup = totalMarkup;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getMerchantFeeOnTf() {
        return merchantFeeOnTf;
    }

    public void setMerchantFeeOnTf(BigDecimal merchantFeeOnTf) {
        this.merchantFeeOnTf = merchantFeeOnTf;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Boolean getAirlineOrCommissionPercentFlag() {
        return airlineOrCommissionPercentFlag;
    }

    public void setAirlineOrCommissionPercentFlag(Boolean airlineOrCommissionPercentFlag) {
        this.airlineOrCommissionPercentFlag = airlineOrCommissionPercentFlag;
    }

    public BigDecimal getAirlineOrCommission() {
        return airlineOrCommission;
    }

    public void setAirlineOrCommission(BigDecimal airlineOrCommission) {
        this.airlineOrCommission = airlineOrCommission;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalSellFare() {
        return totalSellFare;
    }

    public void setTotalSellFare(BigDecimal totalSellFare) {
        this.totalSellFare = totalSellFare;
    }

    public BigDecimal getTotalVatGst() {
        return totalVatGst;
    }

    public void setTotalVatGst(BigDecimal totalVatGst) {
        this.totalVatGst = totalVatGst;
    }

    public BigDecimal getTotalMerchantFee() {
        return totalMerchantFee;
    }

    public void setTotalMerchantFee(BigDecimal totalMerchantFee) {
        this.totalMerchantFee = totalMerchantFee;
    }

    public BigDecimal getTotalSellingFare() {
        return totalSellingFare;
    }

    public void setTotalSellingFare(BigDecimal totalSellingFare) {
        this.totalSellingFare = totalSellingFare;
    }
}

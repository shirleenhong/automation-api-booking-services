package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;

public class FeeCalculator extends CommonCalculator {

	public FeesBreakdown calculate(Client client, TransactionFeesInput input) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = null;
		
		if(input.isGstEnabled()) {
			gstAmount = BigDecimal.ZERO;
		}

		if(!getAirlineRule(input.getPlatCarrier()).isIncludeYqComm()) {
			yqTax = BigDecimal.ZERO;
		}

		if(input.isGstEnabled() && client.isExemptTax()) {
			gstAmount = calculatePercentage(safeValue(input.getBaseFare())
							.add(input.getYqTax()), input.getProduct().getGst())
					.add(calculatePercentage(safeValue(input.getBaseFare())
							.add(input.getYqTax()), input.getProduct().getOt1()))
					.add(calculatePercentage(safeValue(input.getBaseFare())
							.add(input.getYqTax()), input.getProduct().getOt2()));
		}
		/*
		BigDecimal commission = null;
		BigDecimal orCommission = null;
		BigDecimal markup = null;
		BigDecimal discount = null;
		
		if(input.isCommissionEnabled()) {
			commission = 
		} 
		
		if(input.isOrCommissionEnabled()) {
			orCommission =l
		}
		
		if(input.isMarkUpEnabled()) {
			markup = 
		}
		
		if(input.isDiscountEnabled()) {
			discount = 
		}
		
		
		GetCalculate Total Taxes
		Total Taxes = Total Taxes + txtTax(1) + txtTax(2)

		GetCalculate Total Fare

		GetCalculate lblFeeBaseText

		If Fee Override Checkbox is Unchecked Then
		   ClientPricing = client.getClientPricings where triptype = ttype and cmpid = ????
		   FeeOption = ClientPricing.FeeOption
		   ApplyFee = client.lccsameasint ? client.intddlfeeapply : client.lccddlfeeapply
		   
			If ApplyFee <> "NA" Then
				If FeeOption = "P" Then
					Transaction Fee = Transaction Fee by PNR
				ElseIf FeeOption = "C" Then
					Transaction Fee = Transaction Fee by Coupon
				ElseIf FeeOption = "T" Then
					Transaction Fee = Transaction Fee by Tkt
				End If
			Else
				Transaction Fee = 0
			End If
		End If

		GetCalculate Merchant Fee AMount
		GetCalculate Merchant Fee TF Amount	

		GetCalculate Total Sell Fare

		GetCalculate Total Quote

		txtRefFare = Base Fare + Total Taxes
			

		If txtDeclineFare < ((Total Fare - Discount Amount) + Total Taxes) Then
			txtLowFare = txtDeclineFare
			txtLowFareCarrier = txtDeclineAirline
		Else
			txtLowFare = (Total Fare - Discount Amount) + Total Taxes
			txtLowFareCarrier = cmbPlatCarrier.Text
		End If
		*/
		return new FeesBreakdown();
	}
	
	private AirlineRule getAirlineRule(String platCarrier) {
		// TODO Get airline rule;
		return new AirlineRule();
	}

	public BigDecimal getTotalAirCom(TransactionFeesInput input) {
		return safeValue(input.getBaseFare())
					.add(safeValue(input.getYqTax()))
					.add(safeValue(input.getAirlineCommission()));
	}
	
	public BigDecimal getTotalMarkUp() {
		return null;
	}
	
	public BigDecimal getTotalDiscount(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {
		
		BigDecimal commissionAmount = calculatePercentage(
					safeValue(breakdown.getTotalIataCommission()),
				breakdown.getClientDiscountPercent());
		
		if(TripTypes.isInternational(input.getTripType())) {
			return commissionAmount.add(safeValue(breakdown.getTotalReturnableOr()));
		}
		
		return commissionAmount;
	}
	
	public BigDecimal getTotalFare(TransactionFeesInput input) {
		return safeValue(input.getBaseFare());
	}
	
	public BigDecimal getTotalOrCom(TransactionFeesInput input) {
		
		if(TripTypes.isInternational(input.getTripType())) {
			calculatePercentage(calculatePercentage(
					safeValue(input.getBaseFare())
						.subtract(input.getAirlineCommission()),
							input.getAirlineCommissionPercent()),
					input.getReturnOrCommissionPercent());
		}
		
		return null;

	}

	public BigDecimal getTotalOrCom2(TransactionFeesInput input) {
		
		if(TripTypes.isInternational(input.getTripType())) {
			calculatePercentage(input.getAirlineOrCommission(), 
					input.getReturnOrCommissionPercent());
		}
		
		return null;
	}
	
	public BigDecimal getMerchantFee(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown
			) {
		
		return calculatePercentage(
				safeValue(breakdown.getTotalSellFare())
				.subtract(breakdown.getTotalDiscount())
				.add(breakdown.getTotalTaxes())
				.add(breakdown.getTotalGst()), input.getMerchantFeePercent());
	}
	
	public BigDecimal getMfOnTf(TransactionFeesInput input, 
			BigDecimal totalGstOnTf) {

		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(safeValue(input.getFee()).add(totalGstOnTf), 
					input.getMerchantFeePercent());
		}
		
		return calculatePercentage(input.getFee(), 
				input.getMerchantFeePercent());
	}
	
	public BigDecimal getTotalNettFare(TransactionFeesInput input) {
		return safeValue(input.getBaseFare());		
	}
	
	public BigDecimal getTotalFee(TransactionFeesInput input, TransactionFeesBreakdown breakdown) {
		return null;		
	}
	
	public BigDecimal getTotalSellingFare(TransactionFeesBreakdown breakdown) {

		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getTotalDiscount()))
				.add(safeValue(breakdown.getTotalGst()))
				.add(safeValue(breakdown.getTotalMerchantFee()));		
	}
	
	public BigDecimal getTotalCharge(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {
		
		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getMerchantFeeOnTF()))
				.add(safeValue(input.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));	
	}
	
	public Boolean getDdlFeeApply() {
		return Boolean.TRUE;
	}

}

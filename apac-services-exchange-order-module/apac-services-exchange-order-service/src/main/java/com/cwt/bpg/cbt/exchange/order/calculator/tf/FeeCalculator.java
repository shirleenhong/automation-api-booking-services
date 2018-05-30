package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component("tfCalculator")
public class FeeCalculator extends CommonCalculator {

	public FeesBreakdown calculate(Client client, TransactionFeesInput input) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = null;
		
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
		
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
				
		if(input.isCommissionEnabled()) {
			breakdown.setTotalIataCommission(getTotalAirCommission(input));
		} 
		
		if(input.isOverheadCommissionEnabled()) {
			breakdown.setTotalOverheadCommission(getTotalOverheadCommission(input));
		}
		
		if(input.isMarkupEnabled()) {
			breakdown.setTotalMarkup(getTotalMarkup());
		}
		
		if(input.isDiscountEnabled()) {
			breakdown.setTotalDiscount(getTotalDiscount(input, breakdown));
		}		
		
		breakdown.setTotalTaxes(safeValue(input.getYqTax())
									.add(input.getTax1())
									.add(input.getTax1()));
		
		breakdown.setTotalGst(gstAmount);
		breakdown.setTotalSellingFare(getTotalFare(input));
		breakdown.setBaseAmount(getTotalFee(input, breakdown));
		
		/*
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
		*/
		
		breakdown.setTotalMerchantFee(getMerchantFee(input, breakdown));
		
		BigDecimal totalGstOnTf = null; // ??
		breakdown.setMerchantFeeOnTf(getMfOnTf(input, totalGstOnTf));
		breakdown.setTotalSellFare(getTotalSellingFare(breakdown));		
		breakdown.setTotalCharge(getTotalCharge(input, breakdown));
		
		/*
		**txtRefFare = Base Amount + Total Tax(es)
		
		If txtDeclineFare < ((Total Sell Fare - Total Discount) + Total Tax(es)) Then
			txtLowFare = txtDeclineFare
			txtLowFareCarrier = txtDeclineAirline
		Else
			txtLowFare = (Total Sell Fare - Total Discount) + Total Tax(es)
			txtLowFareCarrier = cmbPlatCarrier.Text
		End If			
		*/
		return new FeesBreakdown();
	}
	
	private AirlineRule getAirlineRule(String platCarrier) {
		// TODO Get airline rule, move out to service
		return new AirlineRule();
	}

	//TODO Spell out commission
	public BigDecimal getTotalAirCommission(TransactionFeesInput input) {
		return safeValue(input.getBaseFare())
					.add(safeValue(input.getYqTax()))
					.add(safeValue(input.getAirlineCommission()));
	}
	
	public BigDecimal getTotalMarkup() {
		return null;
	}
	
	public BigDecimal getTotalDiscount(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown) {
		
		BigDecimal commissionAmount = calculatePercentage(
					safeValue(breakdown.getTotalIataCommission()),
				breakdown.getClientDiscountPercent());
		
		if(TripTypes.isInternational(input.getTripType())) {
			return commissionAmount.add(safeValue(breakdown.getTotalOverheadCommission()));
		}
		
		return commissionAmount;
	}
	
	public BigDecimal getTotalFare(TransactionFeesInput input) {
		return safeValue(input.getBaseFare());
	}
	
	public BigDecimal getTotalOverheadCommission(TransactionFeesInput input) {
		
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
				.subtract(safeValue(breakdown.getMerchantFeeOnTf()))
				.add(safeValue(input.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));	
	}
	
	public Boolean getDdlFeeApply() {
		return Boolean.TRUE;
	}

}

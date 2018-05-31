package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component("tfCalculator")
public class FeeCalculator extends CommonCalculator {

	public FeesBreakdown calculate(AirlineRule airlineRule, Client client, TransactionFeesInput input) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = null;
		
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
		
		if(input.isGstEnabled()) {
			gstAmount = BigDecimal.ZERO;
		}

		// airlineRule shall be get via platCarrier
		// TODO: AirlineRuleService to inject or not?
		if(!airlineRule.isIncludeYqComm()) {
			yqTax = BigDecimal.ZERO;
		}

		if(input.isGstEnabled() && client.isExemptTax()) {
			final InProduct product = input.getProduct();
			final BigDecimal baseFarePlusYqTax = safeValue(input.getBaseFare())
							.add(input.getYqTax());
			
			gstAmount = calculatePercentage(baseFarePlusYqTax, product.getGst())
					.add(calculatePercentage(baseFarePlusYqTax, product.getOt1()))
					.add(calculatePercentage(baseFarePlusYqTax, product.getOt2()));
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
									.add(input.getTax2()));
		
		breakdown.setTotalGst(gstAmount);
		breakdown.setTotalSellingFare(getTotalFare(input));
		breakdown.setBaseAmount(getTotalFee(input, breakdown));
		
		BigDecimal transactionFee = getTransactionFee(client, input, breakdown);
		
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

	private BigDecimal getTransactionFee(Client client, TransactionFeesInput input, TransactionFeesBreakdown breakdown) {


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
		
		if (!input.isFeeOverride()) {
			// cmpid needed?
			Optional<ClientPricing> cp = client.getClientPricings().stream()
							.filter(i -> i.getTripType().equals(input.getTripType()))
							.findFirst();
			
			if (cp.isPresent()) {
				final ClientPricing clientPricing = cp.get();
				final String feeOption = clientPricing.getFeeOption();
				
				final String applyFee = client.getLccSameAsInt() == true ? 
						client.getIntDdlFeeApply() : client.getLccDdlFeeApply();
						
				if (!applyFee.equals("NA")) {
					if (feeOption.equals("P")) {
						// transaction fee by PNR
						BigDecimal totalFee = getTotalFee(input, breakdown);
					}
					else if (feeOption.equals("C")) {
						// transaction fee by coupon
						
					}
					else if (feeOption.equals("T")) {
						// transaction fee by ticket
					}
					else {
						// Fee == 0
						return BigDecimal.ZERO;
					}
				}
			}
			
		}
		return null;
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
		
		BigDecimal commissionAmount = calculatePercentage(breakdown.getTotalIataCommission(),
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
			return calculatePercentage(calculatePercentage(safeValue(input.getBaseFare())
						.subtract(input.getAirlineCommission()), input.getAirlineCommissionPercent()),
					input.getReturnOrCommissionPercent());
		}
		return null;
	}

	public BigDecimal getTotalOrCom2(TransactionFeesInput input) {
		
		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(input.getAirlineOrCommission(), 
					input.getReturnOrCommissionPercent());
		}
		
		return null;
	}
	
	public BigDecimal getMerchantFee(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown
			) {
		
		return calculatePercentage(safeValue(breakdown.getTotalSellFare())
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

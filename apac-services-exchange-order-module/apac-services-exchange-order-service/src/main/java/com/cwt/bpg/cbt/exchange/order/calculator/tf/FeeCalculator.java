package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component("tfCalculator")
public class FeeCalculator extends CommonCalculator {
	
	private static final String PNR = "P";
	private static final String COUPON = "C";
	private static final String TICKET = "T";
	private static final String NA = "NA";
	private static final String ALL = "ALL";

	public FeesBreakdown calculate(TransactionFeesInput input, AirlineRule airlineRule, Client client, Airport airport) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = null;
		
		TransactionFeesBreakdown breakdown = new TransactionFeesBreakdown();
		
		if(input.isGstEnabled()) {
			gstAmount = BigDecimal.ZERO;
		}

		if(!airlineRule.isIncludeYqCommission()) {
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
		
		breakdown.setFee(getTransactionFee(client, input, breakdown, airport));
		
		breakdown.setTotalMerchantFee(getMerchantFee(input, breakdown));
		
		BigDecimal totalGstOnTf = null; // ??
		breakdown.setMerchantFeeOnTf(getMfOnTf(input, breakdown, totalGstOnTf));
		breakdown.setTotalSellFare(getTotalSellingFare(breakdown));		
		breakdown.setTotalCharge(getTotalCharge(breakdown));
				
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
		breakdown.setFee(null);
		
		return breakdown;
	}

	private BigDecimal getTransactionFee(Client client, 
			TransactionFeesInput input, 
			TransactionFeesBreakdown breakdown, 
			Airport airport) {

		BigDecimal result = BigDecimal.ZERO;
		
		if (!input.isFeeOverride()) {
			
			Optional<ClientPricing> pricing = client.getClientPricings().stream()
							.filter(i -> i.getTripType().equals(input.getTripType()))
							.findFirst();
			
			if (pricing.isPresent()) {
				final ClientPricing clientPricing = pricing.get();
				final String feeOption = clientPricing.getFeeOption();
				
				final String applyFee = client.getLccSameAsInt() 
							? client.getIntDdlFeeApply() 
							: client.getLccDdlFeeApply();
						
				if (!applyFee.equals(NA)) {
					if (feeOption.equals(PNR)) {
						result = getFeeByPnr(input, breakdown, client, airport, clientPricing);
					}
					else if (feeOption.equals(COUPON)) {
						result = getFeeByCoupon();
					}
					else if (feeOption.equals(TICKET)) {
						result = getFeeByTicket();
					}
				}
			}
			
		}
		return result;
	}
	
	private BigDecimal getFeeByTicket() {
		// TODO Auto-generated method stub
		return null;
	}

	private BigDecimal getFeeByCoupon() {
		// TODO Auto-generated method stub
		return null;
	}

	private BigDecimal getFeeByPnr(
			TransactionFeesInput input,
			TransactionFeesBreakdown breakdown,
			Client client,
			Airport airport,
			ClientPricing pricing) {
		
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal baseAmount = getTotalFee(input, breakdown);
		
		if(baseAmount == null) {
			baseAmount = input.getBaseFare();
		}
		
		TransactionFee transactionFee = getFeeByTerritory(pricing, airport.getCityCode(), baseAmount);
		
		if(transactionFee == null) {
			transactionFee = getFeeByTerritory(pricing, airport.getCountryCode(), baseAmount);
		}
		
		if(transactionFee == null) {			
			transactionFee = getFeeByTerritory(pricing, airport.getRegionCode(), baseAmount);
		}
		
		if(transactionFee == null) {
			transactionFee = getFeeByTerritory(pricing, ALL, baseAmount);
		}
		
		if(transactionFee != null) {
			result = transactionFee.getAmount();
		}		
		
		return result;
	}

	private TransactionFee getFeeByTerritory(
			ClientPricing pricing, 
			String territoryCode,
			BigDecimal amount) {
		
		List<TransactionFee> list = pricing.getTransactionFees().stream()
				.filter(i -> i.getTerritoryCodes().stream()
						.anyMatch(code -> 
							code.equals(territoryCode)
							&& amount.compareTo(i.getStartAmount()) <= 0
							&& amount.compareTo(i.getEndAmount()) >= 0
							)
						)
				.collect(Collectors.toList());
		
		return list.get(0);
	}

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
	
	public BigDecimal getMfOnTf(TransactionFeesInput input, TransactionFeesBreakdown breakdown,
			BigDecimal totalGstOnTf) {

		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(safeValue(breakdown.getFee()).add(totalGstOnTf), 
					input.getMerchantFeePercent());
		}
		
		return calculatePercentage(breakdown.getFee(), 
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
			TransactionFeesBreakdown breakdown) {
		
		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getMerchantFeeOnTf()))
				.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));	
	}
	
	public Boolean getDdlFeeApply() {
		return Boolean.TRUE;
	}

}

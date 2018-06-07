package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import java.math.BigDecimal;
import java.util.Optional;

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

	public IndiaAirFeesBreakdown calculate(IndiaAirFeesInput input,
										   AirlineRule airlineRule,
										   Client client,
										   Airport airport,
										   Product airProduct) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = null;
		
		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
		
		if(input.isGstEnabled()) {
			gstAmount = BigDecimal.ZERO;
		}

		if(!airlineRule.isIncludeYqCommission()) {
			yqTax = BigDecimal.ZERO;
		}

		if(input.isGstEnabled() && client.isExemptTax()) {
			final IndiaProduct product = input.getProduct();
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
									.add(safeValue(input.getTax1()))
									.add(safeValue(input.getTax2())));
		
		breakdown.setTotalGst(gstAmount);
		breakdown.setTotalSellingFare(getTotalFare(input));
		breakdown.setBaseAmount(getTotalFee(input, breakdown));
		
		BigDecimal baseAmount = getTotalFee(input, breakdown);
		breakdown.setFee(getTransactionFee(client, input, airport, baseAmount));
		
		breakdown.setTotalMerchantFee(getMerchantFee(input, breakdown));
		
		breakdown.setMerchantFeeOnTf(getMfOnTf(input, breakdown, 
								getGstOnTf((IndiaProduct) airProduct, breakdown.getFee())));
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
		breakdown.setYqTax(yqTax);
		breakdown.setFee(null);
		
		return breakdown;
	}

	private BigDecimal getGstOnTf(IndiaProduct airProduct, BigDecimal fee) {

		return calculatePercentage(fee, airProduct.getGst())
				.add(calculatePercentage(fee, airProduct.getOt1()))
				.add(calculatePercentage(fee, airProduct.getOt2()));
	}

	private BigDecimal getTransactionFee(Client client, 
			IndiaAirFeesInput input,
			Airport airport, BigDecimal baseAmount) {

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
						result = getFeeByPnr(input, airport, clientPricing, baseAmount);
					}
					else if (feeOption.equals(COUPON)) {
						result = getFeeByCoupon();
					}
					else if (feeOption.equals(TICKET)) {
						result = getFeeByTicket(input, airport, clientPricing, baseAmount);
					}
				}
			}
			
		}
		return result;
	}
	
	private BigDecimal getFeeByTicket(
			IndiaAirFeesInput input,
			Airport airport, 
			ClientPricing pricing,
			BigDecimal totalFee) {
		
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal baseAmount = totalFee;
		
		if(baseAmount == null) {
			baseAmount = input.getBaseFare();
		}
		
		TransactionFee tf = getFeeByTerritorry(airport, pricing, baseAmount);

		if(tf != null) {
		
			if("F".equals(tf.getOperator())) {
				result = safeValue(tf.getAmount()).add(safeValue(tf.getExtraAmount()));
			} 
			else if("M".equals(tf.getOperator())) {
				result = calculatePercentage(baseAmount, tf.getPerAmount()).add(safeValue(tf.getExtraAmount()));
			}
			else if("D".equals(tf.getOperator())) {
				result = tf.getAmount()
							.divide(new BigDecimal(100).subtract(percentDecimal(safeValue(tf.getPerAmount()))))
							.add(safeValue(tf.getExtraAmount()))
							.subtract(safeValue(baseAmount));
			}
			
			if(safeValue(tf.getMaxAmount()).compareTo(BigDecimal.ZERO) > 0 
				&& result.compareTo(safeValue(tf.getMaxAmount())) > 0) {
				 result = tf.getMaxAmount();
			}
			
			if(result.compareTo(safeValue(tf.getMinAmount())) < 0){
				result = safeValue(tf.getMinAmount());
			}			
		}
		
		return result;
	}

	private BigDecimal getFeeByCoupon() {
		return null;
	}

	private BigDecimal getFeeByPnr(
			IndiaAirFeesInput input,
			Airport airport,
			ClientPricing pricing, BigDecimal totalFee) {
		
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal baseAmount = totalFee;
		
		if(baseAmount == null) {
			baseAmount = input.getBaseFare();
		}
		
		TransactionFee transactionFee = getFeeByTerritorry(airport, pricing, baseAmount);
		
		if(transactionFee != null) {
			result = transactionFee.getAmount();
		}		
		
		return result;
	}

	private TransactionFee getFeeByTerritorry(Airport airport, ClientPricing pricing, BigDecimal baseAmount) {
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
		
		return transactionFee;
	}

	private TransactionFee getFeeByTerritory(
			ClientPricing pricing, 
			String territoryCode,
			BigDecimal amount) {
		
		Optional<TransactionFee> result = pricing.getTransactionFees().stream()
				.filter(i -> i.getTerritoryCodes().stream()
						.anyMatch(code -> 
							code.equals(territoryCode)
							&& amount.compareTo(i.getStartAmount()) <= 0
							&& amount.compareTo(i.getEndAmount()) >= 0
							)
				).findFirst();

		return result.orElse(null);

	}

	public BigDecimal getTotalAirCommission(IndiaAirFeesInput input) {
		return safeValue(input.getBaseFare())
					.add(safeValue(input.getYqTax()))
					.add(safeValue(input.getAirlineCommission()));
	}
	
	public BigDecimal getTotalMarkup() {
		return null;
	}
	
	public BigDecimal getTotalDiscount(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown) {
		
		BigDecimal commissionAmount = calculatePercentage(breakdown.getTotalIataCommission(),
				breakdown.getClientDiscountPercent());
		
		if(TripTypes.isInternational(input.getTripType())) {
			return commissionAmount.add(safeValue(breakdown.getTotalOverheadCommission()));
		}
		
		return commissionAmount;
	}
	
	public BigDecimal getTotalFare(IndiaAirFeesInput input) {
		return safeValue(input.getBaseFare());
	}
	
	public BigDecimal getTotalOverheadCommission(IndiaAirFeesInput input) {
		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(calculatePercentage(safeValue(input.getBaseFare())
						.subtract(input.getAirlineCommission()), input.getAirlineCommissionPercent()),
					input.getReturnOrCommissionPercent());
		}
		return null;
	}

	public BigDecimal getTotalOrCom2(IndiaAirFeesInput input) {
		
		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(input.getAirlineOrCommission(), 
					input.getReturnOrCommissionPercent());
		}
		
		return null;
	}
	
	public BigDecimal getMerchantFee(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown
			) {
		
		return calculatePercentage(safeValue(breakdown.getTotalSellFare())
					.subtract(safeValue(breakdown.getTotalDiscount()))
					.add(breakdown.getTotalTaxes())
					.add(safeValue(breakdown.getTotalGst())), input.getMerchantFeePercent());
	}
	
	public BigDecimal getMfOnTf(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown,
								BigDecimal totalGstOnTf) {

		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(safeValue(breakdown.getFee()).add(totalGstOnTf), 
					input.getMerchantFeePercent());
		}
		
		return calculatePercentage(breakdown.getFee(), 
				input.getMerchantFeePercent());
	}
	
	public BigDecimal getTotalNettFare(IndiaAirFeesInput input) {
		return safeValue(input.getBaseFare());		
	}
	
	public BigDecimal getTotalFee(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown) {
		return null;		
	}
	
	public BigDecimal getTotalSellingFare(IndiaAirFeesBreakdown breakdown) {

		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getTotalDiscount()))
				.add(safeValue(breakdown.getTotalGst()))
				.add(safeValue(breakdown.getTotalMerchantFee()));		
	}
	
	public BigDecimal getTotalCharge(
			IndiaAirFeesBreakdown breakdown) {
		
		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getMerchantFeeOnTf()))
				.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));	
	}
	
	public Boolean getDdlFeeApply() {
		return Boolean.TRUE;
	}

}

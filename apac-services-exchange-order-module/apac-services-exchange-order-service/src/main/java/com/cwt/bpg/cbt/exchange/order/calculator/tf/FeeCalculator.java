package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component("tfCalculator")
public class FeeCalculator {
	
	static final String PNR = "P";
	static final String COUPON = "C";
	static final String TICKET = "T";
	private static final String NA = "NA";
	private static final String ALL = "X";	
	static final String SOLO = "SOLO";
	static final String GROUP = "GROUP";
	
	@Autowired
	private ScaleConfig scaleConfig;

	public IndiaAirFeesBreakdown calculate(final IndiaAirFeesInput input,
										   AirlineRule airlineRule,
										   Client client,
										   Airport airport,
										   BaseProduct airProduct) {
		
		BigDecimal gstAmount = null;
		BigDecimal yqTax = getYqTax(airlineRule, input.getYqTax());
		
		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();
		
		int scale = scaleConfig.getScale(input.getCountryCode().toUpperCase());
		
		if(input.isGstEnabled()) {
			gstAmount = BigDecimal.ZERO;
		}

		if(input.isGstEnabled() && client.isExemptTax()) {
			final IndiaAirProductInput product = input.getProduct();
			final BigDecimal baseFarePlusYqTax = safeValue(input.getBaseFare())
							.add(yqTax);
			
			gstAmount = calculatePercentage(baseFarePlusYqTax, product.getGstPercent())
					.add(calculatePercentage(baseFarePlusYqTax, product.getOt1Percent()))
					.add(calculatePercentage(baseFarePlusYqTax, product.getOt2Percent()));
		}
				
		if(input.isCommissionEnabled()) {
			breakdown.setTotalAirlineCommission(round(getTotalAirCommission(input, yqTax), scale));
		} 
		
		if(input.isOverheadCommissionEnabled()) {
			breakdown.setTotalOverheadCommission(round(getTotalOverheadCommission(input), scale));
		}
		
		if(input.isMarkupEnabled()) {
			breakdown.setTotalMarkup(round(getTotalMarkup(), scale));
		}
		
		if(input.isDiscountEnabled()) {
			breakdown.setTotalDiscount(round(getTotalDiscount(input, breakdown), scale));
		}		
		
		breakdown.setTotalTaxes(round(safeValue(yqTax)
									.add(safeValue(input.getTax1()))
									.add(safeValue(input.getTax2())), scale));
		
		breakdown.setTotalGst(round(gstAmount, scale));
		breakdown.setTotalSellFare(round(safeValue(input.getBaseFare()), scale));
		breakdown.setBaseAmount(round(getTotalFee(input, breakdown, yqTax), scale));
		
		breakdown.setFee(round(getTransactionFee(client, input, airport, breakdown.getBaseAmount()), scale));
		
		breakdown.setTotalMerchantFee(round(getMerchantFee(input, breakdown), scale));
		
		breakdown.setMerchantFeeOnTf(round(getMfOnTf(input, breakdown, 
								getGstOnTf((IndiaProduct) airProduct, breakdown.getFee())), scale));
		breakdown.setTotalSellingFare(round(getTotalSellingFare(breakdown), scale));		
		breakdown.setTotalCharge(round(getTotalCharge(breakdown), scale));
				
		breakdown.setFee(null);
		
		return breakdown;
	}
		
	private BigDecimal getYqTax(AirlineRule airlineRule, BigDecimal yqTax) {
		
		if(airlineRule != null && !airlineRule.isIncludeYqCommission()) {
			return BigDecimal.ZERO;
		}		
		
		return yqTax;		
	}

	private BigDecimal getGstOnTf(IndiaProduct airProduct, BigDecimal fee) {

		return calculatePercentage(fee, airProduct.getGstPercent())
				.add(calculatePercentage(fee, airProduct.getOt1Percent()))
				.add(calculatePercentage(fee, airProduct.getOt2Percent()));
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
						
				if (applyFee != null && !applyFee.equals(NA)) {
					if (feeOption.equals(PNR)) {
						result = getFeeByPnr(input, airport, clientPricing, baseAmount);
					}
					else if (feeOption.equals(COUPON)) {
						result = getFeeByCoupon(input, clientPricing);
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
		
		TransactionFee tf = getFeeByTerritory(airport, pricing, baseAmount);

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

	private BigDecimal getFeeByCoupon(IndiaAirFeesInput input, ClientPricing pricing) {
		
		BigDecimal tfAmount = BigDecimal.ZERO;
		int segmentCount = input.getAirSegmentCount();
		
		for(TransactionFee tf : pricing.getTransactionFees()) {
			
			if(SOLO.equalsIgnoreCase(tf.getType())) {
				
				if(tf.getStartCoupon() <= segmentCount && segmentCount <= tf.getEndCoupon()) {
						
				   tfAmount = tf.getAmount();
				}
			}			
			else if(GROUP.equalsIgnoreCase(tf.getType())) {
			
				for(int i = 0; i < segmentCount; i++) {
				
					if (tf.getStartCoupon() <= segmentCount) {
						
						tfAmount = tfAmount.add(safeValue(tf.getAmount()));
					}
				}
			}	
		}
		
		return tfAmount;
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
		
		TransactionFee transactionFee = getFeeByTerritory(airport, pricing, baseAmount);
		
		if(transactionFee != null) {
			result = transactionFee.getAmount();
		}		
		
		return result;
	}

	private TransactionFee getFeeByTerritory(Airport airport, ClientPricing pricing, BigDecimal baseAmount) {
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
							&& amount.compareTo(i.getStartAmount()) >= 0
							&& (amount.compareTo(i.getEndAmount()) <= 0 
							  || i.getEndAmount().compareTo(BigDecimal.ZERO) == 0)
							)
				).findFirst();

		return result.orElse(null);

	}

	public BigDecimal getTotalAirCommission(IndiaAirFeesInput input, BigDecimal yqTax) {
		return safeValue(input.getBaseFare())
					.add(safeValue(yqTax))
					.add(safeValue(input.getAirlineCommission()));
	}
	
	public BigDecimal getTotalMarkup() {
		return null;
	}
	
	public BigDecimal getTotalDiscount(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown) {
		
		BigDecimal discountAmount = calculatePercentage(breakdown.getTotalAirlineCommission(),
				input.getDiscountPercent());
		
		if(TripTypes.isInternational(input.getTripType())) {
			return discountAmount.add(safeValue(breakdown.getTotalOverheadCommission()));
		}
		
		return discountAmount;
	}
		
	public BigDecimal getTotalOverheadCommission(IndiaAirFeesInput input) {
		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(calculatePercentage(safeValue(input.getBaseFare())
						.subtract(input.getAirlineCommission()), input.getAirlineOverheadCommissionPercent()),
					input.getClientOverheadCommissionPercent());
		}
		return null;
	}

	public BigDecimal getTotalOverheadCommission2(IndiaAirFeesInput input) {
		
		if(TripTypes.isInternational(input.getTripType())) {
			return calculatePercentage(input.getAirlineOverheadCommission(), 
					input.getClientOverheadCommissionPercent());
		}
		
		return null;
	}
	
	public BigDecimal getMerchantFee(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown) {
		
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
	
	public BigDecimal getTotalFee(IndiaAirFeesInput input, IndiaAirFeesBreakdown breakdown, BigDecimal yqTax) {
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

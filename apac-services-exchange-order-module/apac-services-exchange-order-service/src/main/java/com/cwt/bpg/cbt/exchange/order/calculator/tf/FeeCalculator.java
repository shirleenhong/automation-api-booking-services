package com.cwt.bpg.cbt.exchange.order.calculator.tf;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.FlatTransactionFeeService;
import com.cwt.bpg.cbt.exchange.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;
import static com.cwt.bpg.cbt.exchange.order.model.TripType.isDomestic;
import static com.cwt.bpg.cbt.exchange.order.model.TripType.isInternational;

@Component("tfCalculator")
public class FeeCalculator {

	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private FlatTransactionFeeService transactionFeeService;

	public IndiaAirFeesBreakdown calculate(final IndiaAirFeesInput input,
										   AirlineRule airlineRule,
										   Client client,
										   BaseProduct airProduct) {

		BigDecimal gstAmount = null;

		IndiaAirFeesBreakdown breakdown = new IndiaAirFeesBreakdown();

		int scale = scaleConfig.getScale(Country.INDIA.getCode());

		if (input.isGstEnabled()) {

			gstAmount = BigDecimal.ZERO;
			
			if(!client.isExemptTax()) {
			    
			    final BigDecimal yqTax = getYqTax(airlineRule, input.getYqTax());
				final IndiaAirProductInput product = input.getProduct();
				final BigDecimal baseFarePlusYqTax = safeValue(input.getBaseFare())
								.add(yqTax);
				
				gstAmount = round(
						round(calculatePercentage(baseFarePlusYqTax.doubleValue(), product.getGstPercent()), scale)
						.add(round(calculatePercentage(baseFarePlusYqTax.doubleValue(), product.getOt1Percent()), scale))
						.add(round(calculatePercentage(baseFarePlusYqTax.doubleValue(), product.getOt2Percent()), scale)),
					scale);
			}
		}		
				
		if(input.isCommissionEnabled()) {
			breakdown.setTotalAirlineCommission(round(getTotalAirlineCommission(input, BigDecimal.ZERO), scale));
		}

		if (input.isOverheadCommissionEnabled()) {
            if (input.isAirlineOverheadCommissionByPercent()) {
                //Airline OR Commission %
                breakdown.setTotalOverheadCommission(
                        round(getTotalOverheadCommission(input, breakdown.getTotalAirlineCommission()), scale));
            }
            else{
                //Airline OR Commission $
                breakdown.setTotalOverheadCommission(round(getTotalOverheadCommission2(input), scale));
            }
		}

		if (input.isMarkupEnabled()) {
			breakdown.setTotalMarkup(round(getTotalMarkup(), scale));
		}

		if (input.isDiscountEnabled()) {
			breakdown.setTotalDiscount(round(getTotalDiscount(input, breakdown), scale));
		}

		breakdown.setTotalTaxes(round(safeValue(input.getYqTax())
									.add(safeValue(input.getOthTax1()))
									.add(safeValue(input.getOthTax2()))
									.add(safeValue(input.getOthTax3())), scale));

		breakdown.setTotalGst(gstAmount);
		breakdown.setTotalSellFare(round(safeValue(input.getBaseFare()), scale));
		breakdown.setBaseAmount(round(getTotalFee(input, breakdown, input.getYqTax()), scale));

		breakdown.setFee(round(getTransactionFee(client, input), scale));

		breakdown.setTotalMerchantFee(round(getMerchantFee(input, breakdown), scale));

		breakdown.setMerchantFeeOnTf(
				round(getMfOnTf(input, breakdown, getGstOnTf((IndiaProduct) airProduct, breakdown.getFee())),
						scale));
		breakdown.setTotalSellingFare(round(getTotalSellingFare(breakdown), scale));
		breakdown.setTotalCharge(round(getTotalCharge(breakdown), scale));
	
		breakdown.setReferenceFare(round(safeValue(input.getBaseFare()).add(breakdown.getTotalTaxes()), scale));

		return breakdown;
	}

	private BigDecimal getYqTax(AirlineRule airlineRule, BigDecimal yqTax) {

		if (airlineRule != null && !airlineRule.isIncludeYqCommission()) {
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
										 IndiaAirFeesInput input) {

		BigDecimal result = BigDecimal.ZERO;

		if (!input.isFeeOverride()) {
			FlatTransactionFee tf = transactionFeeService.getTransactionFee(client.getClientAccountNumber());
			if (tf != null) {

				if (isInternational(input.getTripType())) {
					result = tf.getIntOfflineAmount();
				}
				else if (isDomestic(input.getTripType())) {
					result = tf.getDomOfflineAmount();
				}
				else {
					result = tf.getLccOfflineAmount();
				}
			}
			else {
				throw new IllegalArgumentException(
					"Client [ client account number: " + input.getClientAccountNumber() + " ] not found in flat transaction fee collection.");
			}
		}
		else {
			result = input.getFee();
		}
		
		return result;
	}

	/**
	 * Airline commission replaced by airlineOverheadCommissionPercent
	 * @param input
	 * @param yqTax
	 * @return
	 */
	public BigDecimal getTotalAirlineCommission(IndiaAirFeesInput input, BigDecimal yqTax) {
		return calculatePercentage(safeValue(input.getBaseFare())
					.add(safeValue(yqTax)),
					input.getAirlineCommissionPercent());
	}

	public BigDecimal getTotalMarkup() {
		return null;
	}

	public BigDecimal getTotalDiscount(
			IndiaAirFeesInput input,
			IndiaAirFeesBreakdown breakdown) {

		BigDecimal discountAmount = calculatePercentage(breakdown.getTotalAirlineCommission(),
				input.getDiscountPercent());

		if (isInternational(input.getTripType())) {
			return discountAmount.add(safeValue(breakdown.getTotalOverheadCommission()));
		}

		return discountAmount;
	}

	//Airline OR Commission %
	public BigDecimal getTotalOverheadCommission(IndiaAirFeesInput input, BigDecimal totalAirlineCommission) {
		if (isInternational(input.getTripType())) {
			return calculatePercentage(calculatePercentage(safeValue(input.getBaseFare())
						.subtract(safeValue(totalAirlineCommission)), input.getAirlineOverheadCommissionPercent()),
					input.getClientOverheadCommissionPercent());
		}
		return BigDecimal.ZERO;
	}

	//Airline OR Commission $
	public BigDecimal getTotalOverheadCommission2(IndiaAirFeesInput input) {
		if (isInternational(input.getTripType())) {
			return calculatePercentage(input.getAirlineOverheadCommission(),
					input.getClientOverheadCommissionPercent());
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getMerchantFee(IndiaAirFeesInput input,
									 IndiaAirFeesBreakdown breakdown) {

		return calculatePercentage(safeValue(breakdown.getTotalSellFare())
						.subtract(safeValue(breakdown.getTotalDiscount()))
						.add(breakdown.getTotalTaxes())
						.add(safeValue(breakdown.getTotalGst())), input.getMerchantFeePercent());
	}

	public BigDecimal getMfOnTf(IndiaAirFeesInput input,
								IndiaAirFeesBreakdown breakdown,
								BigDecimal totalGstOnTf) {

		if (isInternational(input.getTripType())) {
			return calculatePercentage(safeValue(breakdown.getFee()).add(totalGstOnTf),
					input.getMerchantFeePercent());
		}

		return calculatePercentage(breakdown.getFee(), input.getMerchantFeePercent());
	}

	public BigDecimal getTotalNettFare(IndiaAirFeesInput input) {
		return safeValue(input.getBaseFare());
	}

	public BigDecimal getTotalFee(IndiaAirFeesInput input,
								  IndiaAirFeesBreakdown breakdown,
								  BigDecimal yqTax) {
		return null;
	}

	public BigDecimal getTotalSellingFare(IndiaAirFeesBreakdown breakdown) {

		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getTotalDiscount()))
				.add(safeValue(breakdown.getTotalGst()))
				.add(safeValue(breakdown.getTotalMerchantFee()));
	}

	public BigDecimal getTotalCharge(IndiaAirFeesBreakdown breakdown) {

		return safeValue(breakdown.getTotalSellFare())
				.subtract(safeValue(breakdown.getTotalDiscount()))
				.add(safeValue(breakdown.getTotalGst()))
				.add(safeValue(breakdown.getTotalMerchantFee()))
				.add(safeValue(breakdown.getMerchantFeeOnTf()))
				.add(safeValue(breakdown.getFee()))
				.add(safeValue(breakdown.getTotalTaxes()));
	}

	public boolean getDdlFeeApply() {
		return true;
	}

}

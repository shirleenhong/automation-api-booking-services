package com.cwt.bpg.cbt.exchange.order.calculator;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.*;
import static com.cwt.bpg.cbt.exchange.order.calculator.MerchantFeeCalculatorUtils.getMerchantFeeForVendorCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.cwt.bpg.cbt.calculator.config.RoundingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class SgAirCalculator implements Calculator<AirFeesBreakdown, AirFeesInput> {

	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private RoundingConfig roundingConfig;

	@Override
	public AirFeesBreakdown calculate(AirFeesInput input, MerchantFee merchantFeeObj, String countryCode) {

        AirFeesBreakdown result = new AirFeesBreakdown();

		if (input == null) {
			return result;
		}

		int scale = scaleConfig.getScale(Country.SINGAPORE.getCode());

		BigDecimal totalSellingFare;
		BigDecimal nettCost;
		BigDecimal merchantFee = BigDecimal.ZERO;
		BigDecimal totalTax = safeValue(input.getTax1()).add(safeValue(input.getTax2()));
		BigDecimal inMerchantFee = safeValue(input.getMerchantFee());
		BigDecimal inNettFare = round(safeValue(input.getNettFare()), scale);
		BigDecimal inDiscount = safeValue(input.getDiscount());
		BigDecimal inCommission = safeValue(input.getCommission());
		Boolean isConstTkt = "CT".equals(input.getProductType());
		Boolean isFopTypeCX = safeValue(input.getFopType().getCode()).equals(FopType.CWT.getCode());
		String inClientType = safeValue(input.getClientType());

		if (!input.isApplyFormula()) {
			if (isConstTkt) {
				totalSellingFare = inNettFare.subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}
			else {
				totalSellingFare = safeValue(input.getSellingPrice()).subtract(inDiscount).add(totalTax)
						.add(inMerchantFee);
			}
			nettCost = inNettFare.subtract(inCommission);
		}
		else {
			BigDecimal commission = getCommission(input, scale, inNettFare, inCommission);
			BigDecimal discount = getDiscount(input, scale, inNettFare, inDiscount, inClientType);
			nettCost = inNettFare.subtract(commission);
			BigDecimal totalNettFare = getTotalNettFare(input, discount, totalTax, inNettFare, isConstTkt);

			if (!input.isCwtAbsorb() && isFopTypeCX && !input.isMerchantFeeWaive()) {
				BigDecimal totalPlusTF = getTotal(totalNettFare,
						input.getTransactionFee(),
						inClientType,
						merchantFeeObj.isIncludeTransactionFee());

				Double merchantFeePercent = getMerchantFeeForVendorCode(merchantFeeObj, input.getVendorCode());

				merchantFee = getMerchantFee(totalPlusTF, merchantFeePercent, scale, getRoundingMode("merchantFee", countryCode));
				result.setMerchantFee(merchantFee);
			}

			totalSellingFare = totalNettFare.add(merchantFee);

			result.setCommission(round(commission, scale, getRoundingMode("commission", countryCode)));
			result.setDiscount(discount);
		}

		result.setNettCost(round(nettCost, scale, getRoundingMode("nettCost", countryCode)));
		result.setTotalSellingFare(round(totalSellingFare, scale, getRoundingMode("totalSellingFare", countryCode)));

		return result;
	}

	private BigDecimal getTotalNettFare(AirFeesInput input, BigDecimal discount, BigDecimal totalTax,
                                        BigDecimal inNettFare, Boolean isConstTkt) {
		BigDecimal totalNettFare;
		if (isConstTkt) {
			totalNettFare = inNettFare.subtract(discount).add(totalTax);
		}
		else {
			totalNettFare = safeValue(input.getSellingPrice()).subtract(discount).add(totalTax);
		}
		return totalNettFare.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : totalNettFare;
	}

	private BigDecimal getDiscount(AirFeesInput input, int scale, BigDecimal inNettFare,
                                   BigDecimal inDiscount, String inClientType) {
		BigDecimal discount;
		if (input.isDiscountByPercent()) {
			discount = getDiscountAmt(inNettFare, input.getDiscountPercent(), inClientType, scale);
		}
		else {
			discount = inDiscount;
		}
		return discount;
	}

	private BigDecimal getCommission(AirFeesInput input, int scale, BigDecimal inNettFare,
                                     BigDecimal inCommission) {
		BigDecimal commission;
		if (input.isCommissionByPercent()) {
			commission = round(inNettFare.multiply(percentDecimal(input.getCommissionPercent())), scale);
		}
		else {
			commission = inCommission;
		}
		return commission;
	}

	private BigDecimal getDiscountAmt(BigDecimal sellFare, Double discountPercent, String clientType, int scale) {
		BigDecimal discountAmt = BigDecimal.ZERO;
		if (ClientType.clientsWithDiscount().contains(clientType)) {
			discountAmt = round(sellFare.multiply(percentDecimal(discountPercent)), scale);
		}
		return discountAmt;
	}

	private BigDecimal getTotal(BigDecimal totalCharge, BigDecimal transFee, String clientType,
			Boolean incMF) {
		BigDecimal total;

		if (ClientType.TF.getCode().equals(clientType) && incMF) {
			total = totalCharge.add(transFee);
		}
		else {
			total = totalCharge;
		}

		return total;
	}

	private BigDecimal getMerchantFee(BigDecimal totalCharge, Double merchantFeePercent,
			int scale, RoundingMode roundingMode) {

		return round(totalCharge.multiply(percentDecimal(merchantFeePercent)), scale,
				roundingMode);
	}

	private RoundingMode getRoundingMode(String field, String countryCode) {
		return roundingConfig.getRoundingMode(field, countryCode);
	}
}

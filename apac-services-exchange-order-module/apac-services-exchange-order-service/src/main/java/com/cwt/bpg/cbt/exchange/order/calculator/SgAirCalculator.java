package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class SgAirCalculator extends CommonCalculator implements Calculator {

	@Autowired
	private ScaleConfig scaleConfig;

	@Override
	public FeesBreakdown calculate(FeesInput genericInput, MerchantFee merchantFeeObj) {

		AirFeesBreakdown result = new AirFeesBreakdown();

		if (genericInput == null) {
			return result;
		}

		AirFeesInput input = (AirFeesInput) genericInput;

		int scale = scaleConfig.getScale(input.getCountryCode());

		BigDecimal totalSellingFare;
		BigDecimal nettCost;
		BigDecimal merchantFee = BigDecimal.ZERO;
		BigDecimal totalTax = safeValue(input.getTax1()).add(safeValue(input.getTax2()));
		BigDecimal inMerchantFee = safeValue(input.getMerchantFee());
		BigDecimal inNettFare = round(safeValue(input.getNettFare()), scale);
		BigDecimal inDiscount = safeValue(input.getDiscount());
		BigDecimal inCommission = safeValue(input.getCommission());
		Boolean isConstTkt = "CT".equals(input.getProductType());
		Boolean isFopTypeCX = safeValue(input.getFopType()).equals(FOPTypes.CWT.getCode());
		String inClientType = safeValue(input.getClientType());

		if (!input.isApplyFormula()) {
			if (isConstTkt) {
				totalSellingFare = inNettFare.subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}
			else {
				totalSellingFare = input.getSellingPrice().subtract(inDiscount).add(totalTax)
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
						getTransactionFeeFlag(merchantFeeObj));
				merchantFee = getMerchantFee(totalPlusTF, safeMerchantFeePct(merchantFeeObj), scale);
				result.setMerchantFee(merchantFee);
			}

			totalSellingFare = totalNettFare.add(merchantFee);

			result.setCommission(commission);
			result.setDiscount(discount);
		}

		result.setNettCost(nettCost);
		result.setTotalSellingFare(totalSellingFare);

		return result;
	}

	private Double safeMerchantFeePct(MerchantFee merchantFee) {
		return (merchantFee != null) ? merchantFee.getMerchantFeePct() : 0D;
	}

	private Boolean getTransactionFeeFlag(MerchantFee merchantFee) {
		return (merchantFee != null) ? merchantFee.isIncludeTransactionFee() : Boolean.FALSE;
	}

	private BigDecimal getTotalNettFare(AirFeesInput input, BigDecimal discount, BigDecimal totalTax,
			BigDecimal inNettFare, Boolean isConstTkt) {
		BigDecimal totalNettFare;
		if (isConstTkt) {
			totalNettFare = inNettFare.subtract(discount).add(totalTax);
		}
		else {
			totalNettFare = input.getSellingPrice().subtract(discount).add(totalTax);
		}
		return totalNettFare.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : totalNettFare;
	}

	private BigDecimal getDiscount(AirFeesInput input, int scale, BigDecimal inNettFare,
			BigDecimal inDiscount, String inClientType) {
		BigDecimal discount;
		if (input.isDiscountByPercent()) {
			discount = getDiscountAmt(inNettFare, input.getDiscountPct(), inClientType, scale);
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
			commission = round(inNettFare.multiply(percentDecimal(input.getCommissionPct())), scale);
		}
		else {
			commission = inCommission;
		}
		return commission;
	}

	private BigDecimal getDiscountAmt(BigDecimal sellFare, Double discountPct, String clientType, int scale) {
		BigDecimal discountAmt = BigDecimal.ZERO;
		if (ClientTypes.clientsWithDiscount().contains(clientType)) {
			discountAmt = round(sellFare.multiply(percentDecimal(discountPct)), scale);
		}
		return discountAmt;
	}

	private BigDecimal getTotal(BigDecimal totalCharge, BigDecimal transFee, String clientType,
			Boolean incMF) {
		BigDecimal total;

		if (ClientTypes.TF.getCode().equals(clientType) && incMF) {
			total = totalCharge.add(transFee);
		}
		else {
			total = totalCharge;
		}

		return total;
	}

	private BigDecimal getMerchantFee(BigDecimal totalCharge, Double merchantFeePct, int scale) {

		return round(totalCharge.multiply(percentDecimal(merchantFeePct)), scale);
	}
}

package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.ClientTypes;
import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class SgAirCalculator extends CommonCalculator implements Calculator {

	@Autowired
	private ScaleConfig scaleConfig;
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput,
			MerchantFee merchantFeeObj) {

		AirFeesBreakdown result = new AirFeesBreakdown();

		if (genericInput == null) {
			return result;
		}
		
		AirFeesInput input = (AirFeesInput) genericInput;

		int scale = scaleConfig.getScale(input.getCountryCode());

		BigDecimal totalSellingFare;
		BigDecimal nettCostInEO;
		
		BigDecimal merchantFee = BigDecimal.ZERO;
		
		BigDecimal totalTax = safeValue(input.getTax1()).add(safeValue(input.getTax2()));
		BigDecimal inMerchantFee = safeValue(input.getMerchantFee());
		BigDecimal inNettFare = safeValue(input.getNettFare());
		BigDecimal inDiscount = safeValue(input.getDiscount());
		BigDecimal inCommission = safeValue(input.getCommission());
		Boolean isConstTkt = "CT".equals(input.getProductType());
		Boolean isFopTypeCX = safeValue(input.getFopType()).equals(FOPTypes.CWT.getCode());
		String inClientType = safeValue(input.getClientType());

		if (!input.isApplyFormula()) {

			if (isConstTkt) {
				totalSellingFare = inNettFare
						.subtract(inDiscount)
						.add(totalTax)
						.add(inMerchantFee);
			}
			else {
				totalSellingFare = input.getSellingPrice()
						.subtract(inDiscount)
						.add(totalTax)
						.add(inMerchantFee);
			}
			nettCostInEO = inNettFare.subtract(inCommission);

		}
		else {

			BigDecimal commission = getCommission(input, scale, inNettFare, inCommission);

			BigDecimal discount = getDiscount(input, scale, inNettFare, inDiscount, inClientType);

			nettCostInEO = inNettFare.subtract(commission);

			BigDecimal totalNettFare = getTotalNettFare(input, discount, totalTax,
					inNettFare, isConstTkt);

			if (!input.isCwtAbsorb() && isFopTypeCX && !input.isMerchantFeeWaive()) {
				BigDecimal totalPlusTF = getTotal(totalNettFare, input.getTransactionFee(),
						inClientType, getTransactionFeeFlag(merchantFeeObj));
				merchantFee = getMerchantFee(totalPlusTF,
						getMerchantFeePct(merchantFeeObj), scale);
			}

			totalSellingFare = totalNettFare.add(merchantFee);
			
			result.setCommission(commission);
			result.setDiscount(discount);
		}

		result.setMerchantFee(merchantFee);
		result.setNettCostInEO(nettCostInEO);
		result.setTotalSellingFare(totalSellingFare);

		return result;
	}

	private Double getMerchantFeePct(MerchantFee merchantFee) {
		return (merchantFee != null) ? merchantFee.getMerchantFeePct() : 0D;
	}

	private Boolean getTransactionFeeFlag(MerchantFee merchantFee) {
		return (merchantFee != null) ? merchantFee.isIncludeTransactionFee() : Boolean.FALSE;
	}

	private BigDecimal getTotalNettFare(AirFeesInput input, BigDecimal discount,
			BigDecimal totalTax, BigDecimal inNettFare, Boolean isConstTkt) {
		BigDecimal totalNettFare;
		if (isConstTkt) {
			totalNettFare = inNettFare.subtract(discount).add(totalTax);
		}
		else {
			totalNettFare = input.getSellingPrice().subtract(discount).add(totalTax);
		}
		return totalNettFare;
	}

	private BigDecimal getDiscount(AirFeesInput input, int scale, BigDecimal inNettFare,
			BigDecimal inDiscount, String inClientType) {
		BigDecimal discount;
		if (input.isDiscountByPercent()) {
			discount = getDiscountAmt(inNettFare, input.getDiscountPct(),
					inClientType, scale);
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
			commission = round(inNettFare
					.multiply(percentDecimal(input.getCommissionPct())), scale);
		}
		else {
			commission = inCommission;
		}
		return commission;
	}

	private BigDecimal getDiscountAmt(BigDecimal sellFare, Double discountPct,
			String clientType, int scale) {

		BigDecimal discAmt = BigDecimal.ZERO;
		if    (ClientTypes.DU.getCode().equals(clientType)
			|| ClientTypes.DB.getCode().equals(clientType)
			|| ClientTypes.MN.getCode().equals(clientType)
			|| ClientTypes.TF.getCode().equals(clientType)
			|| ClientTypes.TP.getCode().equals(clientType)) {
			discAmt = round(sellFare.multiply(percentDecimal(discountPct)), scale);
		}
		return discAmt;
	}

	private BigDecimal getTotal(BigDecimal totalCharge, BigDecimal transFee,
			String clientType, Boolean incMF) {
		BigDecimal total;

		if (ClientTypes.TF.getCode().equals(clientType) && incMF) {
			total = totalCharge.add(transFee);
		}
		else {
			total = totalCharge;
		}

		return total;
	}

	private BigDecimal getMerchantFee(BigDecimal totalCharge, Double mercFeePct,
			int scale) {

		return round(totalCharge.multiply(percentDecimal(mercFeePct)), scale);
	}
}

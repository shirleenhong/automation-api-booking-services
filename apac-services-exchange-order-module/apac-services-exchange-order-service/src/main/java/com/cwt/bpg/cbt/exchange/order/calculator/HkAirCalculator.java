package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.*;

@Component
public class HkAirCalculator extends CommonCalculator implements Calculator<AirFeesBreakdown, AirFeesInput> {

	@Autowired
	private ScaleConfig scaleConfig;

	private final List<String> clientsWithAdditionalSellPrice = Arrays.asList(ClientTypes.MG.getCode(),
			ClientTypes.DB.getCode(),
			ClientTypes.TF.getCode(),
			ClientTypes.MN.getCode());

	private final List<String> clientsWithPercentageDiscount = Arrays.asList(ClientTypes.DU.getCode(),
			ClientTypes.DB.getCode());

	private final List<String> clientsWithCommissionDiscount = Arrays
			.asList(ClientTypes.MN.getCode(), ClientTypes.TF.getCode(), ClientTypes.TP.getCode());

	private final List<String> clientsWithNoDiscount = Arrays.asList(ClientTypes.MN.getCode(),
			ClientTypes.TF.getCode());

	@Override
	public AirFeesBreakdown calculate(AirFeesInput airFeesInput, MerchantFee merchantFee) {

		AirFeesBreakdown result = new AirFeesBreakdown();

		if (airFeesInput == null) {
			return result;
		}

		HkSgAirFeesInput input = (HkSgAirFeesInput) airFeesInput;
		int scale = scaleConfig.getScale(input.getCountryCode());

		BigDecimal totalSellingFare;
		BigDecimal nettCost;
		BigDecimal sellingPrice;
		BigDecimal merchantFeeAmount = safeValue(input.getMerchantFee());
		BigDecimal commission = safeValue(input.getCommission());
		BigDecimal discount = safeValue(input.getDiscount());
		BigDecimal nettFare = safeValue(input.getNettFare());
		BigDecimal tax1 = safeValue(input.getTax1());
		BigDecimal tax2 = safeValue(input.getTax2());

		if (!input.isApplyFormula()) {
			totalSellingFare = nettFare.add(commission).subtract(discount).add(tax1).add(tax2)
					.add(merchantFeeAmount);
			nettCost = nettFare;
		}
		else {
			if (input.isCommissionByPercent()) {
				if (!ClientTypes.TP.getCode().equals(input.getClientType())) {
					commission = getCommission(input, scale, nettFare);
				}

				sellingPrice = nettFare.divide(
						BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPercent())),
						MathContext.DECIMAL128);

				if (!clientsWithAdditionalSellPrice.contains(input.getClientType())) {
					sellingPrice = sellingPrice.add(BigDecimal.TEN);
				}
			}
			else {
				commission = round(commission, scale);
				sellingPrice = nettFare.add(commission);
			}

			sellingPrice = round(sellingPrice, scale);
			result.setCommission(commission);
			result.setSellingPrice(sellingPrice);

			discount = round(applyDiscount(input, commission, discount, nettFare), scale);
			result.setDiscount(discount);

			nettCost = nettFare;
			nettFare = round(sellingPrice.add(tax1).add(tax2).subtract(discount), scale);
			result.setNettFare(nettFare);

			merchantFeeAmount = applyMerchantFee(merchantFee, input, scale, nettFare, tax1, tax2);
			result.setMerchantFee(merchantFeeAmount);

			totalSellingFare = nettFare.add(safeValue(merchantFeeAmount));
		}

		result.setTotalSellingFare(round(totalSellingFare, scale));
		result.setNettCost(round(nettCost, scale));

		return result;
	}

	private BigDecimal getCommission(HkSgAirFeesInput input, int scale, BigDecimal nettFare) {

		BigDecimal commission = nettFare
				.divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPercent())),
						MathContext.DECIMAL128)
				.subtract(nettFare);

		if (commission.compareTo(BigDecimal.ZERO) > 0
				&& ClientTypes.DU.getCode().equals(input.getClientType())) {
			commission = commission.add(BigDecimal.TEN);
		}
		return round(commission, scale);
	}

	private BigDecimal applyMerchantFee(MerchantFee merchantFee, HkSgAirFeesInput input, int scale,
			BigDecimal nettFare, BigDecimal tax1, BigDecimal tax2) {

		BigDecimal merchantFeeAmount = null;

		if (!input.isCwtAbsorb() && FOPTypes.CWT.getCode().equals(input.getFopType())
				&& !input.isMerchantFeeWaive()) {

			BigDecimal mFTotal;
			BigDecimal transactionFee = safeValue(input.getTransactionFee());

			if (input.isUatp()) {
				if (ClientTypes.TF.getCode().equals(input.getClientType())) {
					mFTotal = transactionFee;
				}
				else {
					mFTotal = nettFare.subtract(input.getNettFare()).subtract(tax1).subtract(tax2);
				}
			}
			else {
				mFTotal = nettFare;
				if (ClientTypes.TF.getCode().equals(input.getClientType())
						&& merchantFee.isIncludeTransactionFee()) {
					mFTotal = mFTotal.add(transactionFee);
				}
			}

			merchantFeeAmount = round(calculatePercentage(mFTotal, merchantFee.getMerchantFeePercent()), scale);
			if (merchantFeeAmount.compareTo(BigDecimal.ZERO) < 0) {
				merchantFeeAmount = BigDecimal.ZERO;
			}
		}
		return merchantFeeAmount;
	}

	private BigDecimal applyDiscount(HkSgAirFeesInput input, BigDecimal commission, BigDecimal discount,
			BigDecimal nettFare) {

		BigDecimal result = discount;

		if (clientsWithNoDiscount.contains(input.getClientType())) {
			result = BigDecimal.ZERO;
		}
		else if (input.isDiscountByPercent()) {
			if (clientsWithPercentageDiscount.contains(input.getClientType())) {
				result = calculatePercentage(commission.add(nettFare), input.getDiscountPercent());
			}
			else if (clientsWithCommissionDiscount.contains(input.getClientType())) {
				result = commission;
			}
		}

		return result;
	}
}

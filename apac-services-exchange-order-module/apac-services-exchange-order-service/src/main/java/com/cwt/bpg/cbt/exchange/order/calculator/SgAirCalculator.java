package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class SgAirCalculator extends CommonCalculator implements Calculator {
	
	@Autowired
	ScaleConfig scaleConfig;
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFeeObj) {
		
		AirFeesBreakdown result = new AirFeesBreakdown();
		
		AirFeesInput input = (AirFeesInput)genericInput;
		
		if(genericInput == null) {
			return result;
		}
			
		int scale = scaleConfig.getScale(input.getCountryCode());
		
		BigDecimal totalSellingFare;
		BigDecimal nettCostInEO = BigDecimal.ZERO;
		
		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal merchantFee = BigDecimal.ZERO;
		
		BigDecimal inTax1 = safeValue(input.getTax1());
		BigDecimal inTax2 = safeValue(input.getTax2());
		BigDecimal totalTax = inTax1.add(inTax2);
		BigDecimal inMerchantFee = safeValue(input.getMerchantFee());
		BigDecimal inNettFare = safeValue(input.getNettFare());
		BigDecimal inDiscount = safeValue(input.getDiscount());
		BigDecimal inCommission = safeValue(input.getCommission());
		Boolean isConstTkt = safeValue(input.getProductType()).equals("CT");
		Boolean isFopTypeCX = safeValue(input.getFopType()).equals("CX");
		String inClientType = safeValue(input.getClientType());
		
		if(!input.isApplyFormula()) {
			
			if(isConstTkt) {
				totalSellingFare = inNettFare.subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}else {
				totalSellingFare = input.getSellingPrice().subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}
			nettCostInEO = inNettFare.subtract(inCommission);
			
		}else {
			
			if (input.isCommissionByPercent()){
				commission = round(input.getNettFare().multiply(percentDecimal(input.getCommissionPct())), scale);
			}else {
				commission = inCommission;
			}
			
			if(input.isDiscountByPercent()) {
				discount = getDiscAmt(input.getNettFare(), input.getDiscountPct(), inClientType, scale);
			}else {
				discount = inDiscount;
			}
			
			nettCostInEO = input.getNettFare().subtract(commission);
			
			BigDecimal totalNettFare;
			if(isConstTkt) {
				totalNettFare = inNettFare.subtract(discount).add(totalTax);
			}else {
				totalNettFare = input.getSellingPrice().subtract(discount).add(totalTax);
			}
			
			BigDecimal totalPlusTF;
			if(!input.isCwtAbsorb() && isFopTypeCX && !input.isMerchantFeeWaive()) {
				totalPlusTF = getTotal(totalNettFare, input.getTransactionFee(), inClientType, merchantFeeObj.isIncludeTransactionFee());
				merchantFee = getMerchantFee(totalPlusTF, merchantFeeObj.getMerchantFeePct(), scale);
			}
			
			totalSellingFare = totalNettFare.add(merchantFee);
		}
		
		result.setMerchantFee(merchantFee);
		result.setCommission(commission);
		result.setDiscount(discount);
		result.setNettCostInEO(nettCostInEO);
		result.setTotalSellingFare(totalSellingFare);
		
		return result;
	}
	
	private BigDecimal getDiscAmt(BigDecimal sellFare, Double discountPct, String clientType, int scale) {
		
		BigDecimal discAmt = BigDecimal.ZERO;
		if(clientType.equals("DU")||clientType.equals("DB")||clientType.equals("MN")||clientType.equals("TF")||clientType.equals("TP")) {
			discAmt = round(sellFare.multiply(percentDecimal(discountPct)), scale);
		}
		return discAmt;
	}
	
	private BigDecimal getTotal(BigDecimal totalCharge, BigDecimal transFee, String clientType, Boolean incMF) {
		BigDecimal total;
		
		if(clientType.equals("TF") && incMF) {
			total = totalCharge.add(transFee);
		}else {
			total = totalCharge;
		}
		
		return total;
	}
	
	private BigDecimal getMerchantFee(BigDecimal totalCharge,Double mercFeePct, int scale) {
		
		return round(totalCharge.multiply(percentDecimal(mercFeePct)), scale);
	}		
}

package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public class SgAirCalculator extends CommonCalculator implements Calculator{
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFeeObj) {
		
		AirFeesBreakdown result = new AirFeesBreakdown();
		
		AirFeesInput input = (AirFeesInput)genericInput;
		
		if(genericInput == null) {
			return result;
		}
		
		BigDecimal totalSellingFare;
		BigDecimal nettCostInEO = BigDecimal.ZERO;
		
		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal merchantFee = BigDecimal.ZERO;
		
		BigDecimal inTax1 = setZeroWhenNull(input.getTax1());
		BigDecimal inTax2 = setZeroWhenNull(input.getTax2());
		BigDecimal totalTax = inTax1.add(inTax2);
		BigDecimal inMerchantFee = setZeroWhenNull(input.getMerchantFee());
		BigDecimal inNettFare = setZeroWhenNull(input.getNettFare());
		BigDecimal inDiscount = setZeroWhenNull(input.getDiscount());
		BigDecimal inCommission = setZeroWhenNull(input.getCommission());
		Boolean isConstTkt = setBlankWhenNull(input.getProductType()).equals("CT");
		Boolean isFopTypeCX = setBlankWhenNull(input.getFopType()).equals("CX");
		String inClientType = setBlankWhenNull(input.getClientType());
		
		if(!input.isApplyFormula()) {
			
			if(isConstTkt) {
				totalSellingFare = inNettFare.subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}else {
				totalSellingFare = input.getSellingPrice().subtract(inDiscount).add(totalTax).add(inMerchantFee);
			}
			nettCostInEO = inNettFare.subtract(inCommission);
			
		}else {
			
			if (input.isCommissionByPercent()){
				commission = format(input.getNettFare().multiply(percentDecimal(input.getCommissionPct())));
			}else {
				commission = inCommission;
			}
			
			if(input.isDiscountByPercent()) {
				discount = getDiscAmt(input.getNettFare(), input.getDiscountPct(), inClientType);
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
				merchantFee = getMerchantFee(totalPlusTF, merchantFeeObj.getMerchantFeePct());
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
	
	private BigDecimal getDiscAmt(BigDecimal sellFare, Double discountPct, String clientType) {
		
		BigDecimal discAmt = BigDecimal.ZERO;
		if(clientType.equals("DU")||clientType.equals("DB")||clientType.equals("MN")||clientType.equals("TF")||clientType.equals("TP")) {
			discAmt = format(sellFare.multiply(percentDecimal(discountPct)));
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
	
	private BigDecimal getMerchantFee(BigDecimal totalCharge,Double mercFeePct) {
		
		return format(totalCharge.multiply(percentDecimal(mercFeePct)));
	}
	
	private BigDecimal format(BigDecimal amount) {
		return round(amount, "SG");
	}
	
	private BigDecimal setZeroWhenNull(BigDecimal amount) {
		
		BigDecimal val = BigDecimal.ZERO;
		
		if(amount !=null) {
			val = amount;
		}
		
		return val;
		
	}
	private String setBlankWhenNull(String value) {
		
		String val = "";
		
		if(value !=null) {
			val = value;
		}
		
		return val;
		
	}
	
}

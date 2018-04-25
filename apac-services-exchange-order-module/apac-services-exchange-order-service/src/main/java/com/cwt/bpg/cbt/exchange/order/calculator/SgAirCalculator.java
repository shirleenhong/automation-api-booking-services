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
		BigDecimal totalTax = input.getTax1().add(input.getTax2());
		BigDecimal totalSellingFare;
		BigDecimal nettCostInEO = BigDecimal.ZERO;
		Boolean isConstTkt = input.getProductType().equals("CT");
		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal merchantFee = BigDecimal.ZERO;
		 
		
		if(!input.isApplyFormula()) {
			
			if(isConstTkt) {
				totalSellingFare = input.getNettFare().subtract(input.getDiscount()).add(totalTax).add(input.getMerchantFee());
			}else {
				totalSellingFare = input.getSellingPrice().subtract(input.getDiscount()).add(totalTax).add(input.getMerchantFee());
			}
			nettCostInEO = input.getNettFare().subtract(input.getCommission());
			
		}else {
			
			if (input.isCommissionByPercent()){
				commission = format(input.getNettFare().multiply(getPercentage(input.getCommissionPct())));
			}
			
			if(input.isDiscountByPercent()) {
				discount = getDiscAmt(input.getNettFare(), input.getDiscountPct(), input.getClientType());
			}
			
			nettCostInEO = input.getNettFare().subtract(input.getCommission());
			
			BigDecimal totalNettFare;
			if(isConstTkt) {
				totalNettFare = input.getNettFare().subtract(discount).add(totalTax);
			}else {
				totalNettFare = input.getSellingPrice().subtract(discount).add(totalTax);
			}
			
			BigDecimal totalPlusTF;
			if(!input.isCwtAbsorb() && input.getFopType().equals("CX") && !input.isMerchantFeeWaive()) {
				totalPlusTF = getTotal(totalNettFare, input.getTransactionFee(), input.getClientType(), merchantFeeObj.isIncludeTransactionFee());
				merchantFee = getMerchantFee(totalPlusTF, merchantFeeObj.getMerchantFeePct());
			}
			
			totalSellingFare = totalNettFare.add(merchantFee);
		}
		return result;
	}
	
	private BigDecimal getDiscAmt(BigDecimal sellFare, Double discountPct, String clientType) {
		
		BigDecimal discAmt = BigDecimal.ZERO;
		if(clientType.equals("DU")||clientType.equals("DB")||clientType.equals("MN")||clientType.equals("TF")||clientType.equals("TP")) {
			discAmt = format(sellFare.multiply(getPercentage(discountPct)));
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
		
		return format(totalCharge.multiply(getPercentage(mercFeePct)));
	}
	
	private BigDecimal format(BigDecimal amount) {
		return round(amount, "SG");
	}
	
	
}

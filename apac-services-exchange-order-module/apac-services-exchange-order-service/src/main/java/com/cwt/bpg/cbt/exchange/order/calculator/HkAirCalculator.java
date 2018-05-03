package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

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


public class HkAirCalculator extends CommonCalculator implements Calculator {
	
	@Autowired
	private ScaleConfig scaleConfig;
	
	private final List<String> clientsWithAdditionalSellPrice = Arrays.asList(ClientTypes.MG.getCode()
			, ClientTypes.DB.getCode()
			, ClientTypes.TF.getCode()
			, ClientTypes.MN.getCode());
	
	private final List<String> clientsWithPercentageDiscount = Arrays.asList(ClientTypes.DU.getCode()
			, ClientTypes.DB.getCode());
	
	private final List<String> clientsWithCommisionDiscount = Arrays.asList(ClientTypes.MN.getCode()
			, ClientTypes.TF.getCode()
			, ClientTypes.TP.getCode());
	
	private final List<String> clientsWithNoDiscount = Arrays.asList(ClientTypes.MN.getCode()
			, ClientTypes.TF.getCode());
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFee) {

		AirFeesBreakdown result = new AirFeesBreakdown();
		AirFeesInput input = (AirFeesInput) genericInput;
		
		if (input == null) {
			return result;
		}
		
		int scale = scaleConfig.getScale(input.getCountryCode());
		
		BigDecimal totalSellingFare = BigDecimal.ZERO;
		BigDecimal nettCostInEO = BigDecimal.ZERO;
		BigDecimal sellingPrice = BigDecimal.ZERO;
		BigDecimal merchantFeeAmount = safeValue(input.getMerchantFee());
		BigDecimal commission = safeValue(input.getCommission());
		BigDecimal discount = safeValue(input.getDiscount());
		BigDecimal nettFare = safeValue(input.getNettFare());
		BigDecimal tax1 = safeValue(input.getTax1());
		BigDecimal tax2 = safeValue(input.getTax2());

		if(!input.isApplyFormula()) {
			totalSellingFare = nettFare.add(commission).subtract(discount).add(tax1).add(tax2).add(merchantFeeAmount);
			nettCostInEO = nettFare;
		}
		else {
			if(input.isCommissionByPercent()) {
				if(!ClientTypes.TP.getCode().equals(input.getClientType())) {
					
					commission = nettFare.divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPct())), 
							MathContext.DECIMAL128).subtract(nettFare);
					
					if(commission.compareTo(BigDecimal.ZERO) > 0 && ClientTypes.DU.getCode().equals(input.getClientType())) {
						commission = commission.add(BigDecimal.TEN);
					}
					commission = round(commission, scale);
				}
				sellingPrice = nettFare.divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPct())), MathContext.DECIMAL128);
				
				if(!clientsWithAdditionalSellPrice.contains(input.getClientType())) {
					sellingPrice = sellingPrice.add(BigDecimal.TEN);
				}
			}
			else {
				commission = round(commission, scale);
				sellingPrice = nettFare.add(commission);
			}
			result.setCommission(commission);
			sellingPrice = round(sellingPrice, scale);
			result.setSellingPrice(sellingPrice);

			if(input.isDiscountByPercent()) {
				if(clientsWithPercentageDiscount.contains(input.getClientType())) {
					discount = nettFare.add(calculatePercentage(commission, input.getDiscountPct()));
				}
				else {
					if(clientsWithCommisionDiscount.contains(input.getClientType())) {
						discount = commission;
					}
				}
			}
			if(clientsWithNoDiscount.contains(input.getClientType())) {
				discount = BigDecimal.ZERO;
			}
			
			discount = round(discount, scale);
			result.setDiscount(discount);
			nettCostInEO = nettFare;
			nettFare = round(sellingPrice.add(tax1).add(tax2).subtract(discount), scale);

			result.setNettFare(nettFare);
		
//			If DiscountByPercent Then
//				IF Client Type IN "DU", "DB"
//						Discount = Round Down( Round Down(Nett Fare + Commission * DiscountPct * 0.01))
//				ELSE IF Client Type IN "MN", "TF", "TP"
//						Discount = Round Down(Commission)
//				ELSE
//					Discount = 0
//				END IF
//			End If
//		
//			If Client Type IN "MN","TF" THEN
//				Set Discount = 0
//			END IF
//							
//			Discount = Round Down(Discount)
//					
//			Nett Cost in EO = Nett Fare
//								
//		   	Nett Fare = (Selling Price + tax-1 + tax-2) - Discount
		
		

			if(!input.isCwtAbsorb() && FOPTypes.CWT.getCode().equals(input.getFopType()) && !input.isMerchantFeeWaive()) {
				BigDecimal mFTotal = BigDecimal.ZERO;
				BigDecimal transactionFee = safeValue(input.getTransactionFee());
				if(input.isUatp()) {
					if(ClientTypes.TF.getCode().equals(input.getClientType())) {
						mFTotal = transactionFee ;
					}else {
						//TotalCharge - NetFare - Tax
						mFTotal = nettFare.subtract(input.getNettFare()).subtract(tax1).subtract(tax2);
					}
				}else {
					mFTotal = nettFare;
					if(ClientTypes.TF.getCode().equals(input.getClientType()) && merchantFee.isIncludeTransactionFee()) {
						mFTotal = mFTotal.add(transactionFee);
					}
				}
				merchantFeeAmount = round(calculatePercentage(mFTotal, merchantFee.getMerchantFeePct()), scale);
				if(merchantFeeAmount.compareTo(BigDecimal.ZERO) < 0) {
					merchantFeeAmount = BigDecimal.ZERO;
				}
			}
			else {
				merchantFeeAmount = BigDecimal.ZERO;
			}
			result.setMerchantFee(merchantFeeAmount);
			totalSellingFare = nettFare.add(merchantFeeAmount);

//			if(!input.isWebFareSelected()) {
//				totalSellingFare = round(totalSellingFare, scale);
//			}		
//			If CWT Absorb is unchecked And FOP Type = "CX" And Waive Merchant Fee is Unchecked
//				If UATP checked Then
//					If Client Type = "TF" Then
//					   MFTotal = Transaction Fee
//					Else
//					   MFTotal = (Selling Price + tax-1 + tax-2) - Discount - Nett Fare (Original Input) - tax-1 + tax-2
//					End If
//				Else
//					MFTotal = Nett Fare
//					If Client Type = "TF" And gobjPNR.CompInfo.TFIncMF Then
//						MFTotal = Nett Fare + Transaction Fee
//					End If
//				End If
//								
//				Merchant Fee = MFTotal * Merchant Fee Percent * 0.01
//			Else
//				Merchant Fee = "0"
//			End If
//			
//			If mbolWebFareSelected = True Then
//				Total Selling Fare = Nett Fare + Merchant Fee
//			Else
//				Total Selling Fare = Round UP(Nett Fare + Merchant Fee, gstrAgcyCurrCode, "UP")
//			End If
			
		}
		
		result.setTotalSellingFare(round(totalSellingFare, scale));
		result.setNettCostInEO(round(nettCostInEO, scale));
		
		return result;
	}
}

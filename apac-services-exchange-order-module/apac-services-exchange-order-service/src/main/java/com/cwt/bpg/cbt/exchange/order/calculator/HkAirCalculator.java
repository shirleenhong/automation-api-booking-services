package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;


public class HkAirCalculator extends CommonCalculator implements Calculator {
	
	@Autowired
	ScaleConfig scaleConfig;
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFee) {

		AirFeesBreakdown result = new AirFeesBreakdown();
		AirFeesInput input = (AirFeesInput)genericInput;
		
		if(input == null) {
			return result;
		}

		int scale = scaleConfig.getScale(input.getCountryCode());
		
		BigDecimal totalSellingFare =  BigDecimal.ZERO;
		BigDecimal nettCostInEO =  BigDecimal.ZERO;
		BigDecimal sellingPrice =  BigDecimal.ZERO;
		BigDecimal merchantFeeAmount = BigDecimal.ZERO;
		BigDecimal commission =  safeValue(input.getCommission());
		BigDecimal discount =  safeValue(input.getDiscount());
		BigDecimal nettFare = safeValue(input.getNettFare());
		BigDecimal tax1 = safeValue(input.getTax1());
		BigDecimal tax2 = safeValue(input.getTax2());

		if(!input.isApplyFormula()) {
			totalSellingFare = nettFare.add(commission).subtract(discount).add(tax1).add(tax2);
			nettCostInEO = nettFare;
//			if(!input.isWebFareSelected()) {
//				totalSellingFare = round(totalSellingFare, scale);
//				nettCostInEO = round(nettCostInEO, scale);
//			}
		}else {
//		If chkformula.value = 0 Then
//			Discount = Round Down(Discount, gstrAgcyCurrCode)
//			If mbolWebFareSelected = True Then
//				Total Selling Fare = Nett Fare + Commission - Discount + tax-1 + tax-2) + Merchant Fee
//				Nett Cost in EO = Nett Fare
//			Else
//				Total Selling Fare = Round UP(Nett Fare + Commission - Discount + tax-1 + tax-2) + Merchant Fee, gstrAgcyCurrCode)
//				Nett Cost in EO = Round UP(Nett Fare, gstrAgcyCurrCode)
//			End If
//		Else
			
			if(input.isCommissionByPercent()) {
				if(!"TP".equals(input.getClientType())) {
					commission = nettFare.divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPct())), MathContext.DECIMAL128).subtract(nettFare);
					if(commission.compareTo(BigDecimal.ZERO) > 0 && "DU".equals(input.getClientType())) {
						commission = commission.add(BigDecimal.TEN);
					}
//					commission = round(commission, scale);
				}
				sellingPrice = nettFare.divide(BigDecimal.ONE.subtract(percentDecimal(input.getCommissionPct())), MathContext.DECIMAL128);
				if(!Arrays.asList(new String[] {"MG","DB","TF","MN"}).contains(input.getClientType())) {
					sellingPrice = sellingPrice.add(BigDecimal.TEN);
				}
//				if(!input.isWebFareSelected()) {
//					sellingPrice = round(sellingPrice, scale);
//				}
			}else {
				sellingPrice = nettFare.add(commission);
			}
//			If CommissionByPercent Then
//				if Client Type = TP
//					Commission = 0
//				else
//					Commission = (Nett Fare / (1 - (Commission Percent  * 0.01))) - Nett Fare;
//					if(Commission > 0 and Client Type = DU)
//						Commission = Commission + 10
//					end if
//					Commission = Round(Commission)
//				end if
//							
//				Selling Price = Nett Fare / (1 - (Commission Percent * 0.01))
//							
//				If Client Type NOT IN "MG","DB","TF","MN"  Then
//					Selling Price = Selling Price + 10
//				End If				
//		
//				If mbolWebFareSelected = False Then
//					Selling Price = Round UP(Selling Price)
//				end if
//					
//		    Else
//				Selling Price = Nett Fare + Commission
//		    End If
		
			if(input.isDiscountByPercent()) {
				if(Arrays.asList(new String[] {"DU", "DB"}).contains(input.getClientType())) {
					discount = nettFare.add(calculatePercentage(commission, input.getDiscountPct()));
				}else if(Arrays.asList(new String[] {"MN", "TF", "TP"}).contains(input.getClientType())) {
					discount = commission;
				}
			}
			if(Arrays.asList(new String[] {"MN", "TF"}).contains(input.getClientType())) {
				discount = BigDecimal.ZERO;
			}
			
//			discount = round(discount, scale);
			nettCostInEO = nettFare;
			nettFare = sellingPrice.add(tax1).add(tax2).subtract(discount);
		
		
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
		
		

			if(!input.isCwtAbsorb() && "CX".equals(input.getFopType()) && input.isMerchantFeeWaive()) {
				BigDecimal mFTotal = BigDecimal.ZERO;
				BigDecimal transactionFee = safeValue(input.getTransactionFee());
				if(input.isUatp()) {
					if("TF".equals(input.getClientType())) {
						mFTotal = transactionFee ;
					}else {
						//TotalCharge - NetFare - Tax
						mFTotal = nettFare.subtract(input.getNettFare()).subtract(tax1).subtract(tax2);
					}
				}else {
					mFTotal = nettFare;
					if("TF".equals(input.getClientType()) && merchantFee.isIncludeTransactionFee()) {
						mFTotal = mFTotal.add(transactionFee);
					}
				}
				merchantFeeAmount = calculatePercentage(mFTotal, merchantFee.getMerchantFeePct());
			}
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
			result.setSellingPrice(round(sellingPrice, scale));
			result.setMerchantFee(round(merchantFeeAmount, scale));
			
			result.setCommission(round(commission, scale));
			result.setDiscount(round(discount, scale));
			result.setNettFare(round(nettFare, scale));
		}
		
		result.setTotalSellingFare(round(totalSellingFare, scale));
		result.setNettCostInEO(round(nettCostInEO, scale));
		
		return result;
	}
}

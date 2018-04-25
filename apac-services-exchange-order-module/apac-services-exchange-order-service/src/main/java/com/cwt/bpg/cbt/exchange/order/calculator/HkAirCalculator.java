package com.cwt.bpg.cbt.exchange.order.calculator;

import java.math.BigDecimal;
import java.util.Arrays;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.AirFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;


public class HkAirCalculator extends CommonCalculator implements Calculator{
	
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput genericInput, MerchantFee merchantFee) {

		AirFeesBreakdown result = new AirFeesBreakdown();
		AirFeesInput input = (AirFeesInput)genericInput;
		if(genericInput == null) {
			return result;
		}

		BigDecimal totalSellingFare =  BigDecimal.ZERO;
		BigDecimal nettCostInEO =  BigDecimal.ZERO;
		BigDecimal sellingPrice =  BigDecimal.ZERO;
		BigDecimal commission =  BigDecimal.ZERO;
		BigDecimal discount =  BigDecimal.ZERO;
		BigDecimal nettFare =  BigDecimal.ZERO;
		BigDecimal merchantFeeAmount = BigDecimal.ZERO;

		if(!input.isApplyFormula()) {
			totalSellingFare.add(input.getNettFare()).add(input.getCommission()).subtract(input.getDiscount()).add(input.getTax1()).add(input.getTax2());
			nettCostInEO.add(input.getNettFare());
			if(input.isWebFareSelected()) {
				totalSellingFare = round(totalSellingFare, input.getCountryCode());
				nettCostInEO = round(nettCostInEO, input.getCountryCode());
			}
		}
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
				commission.add(input.getNettFare().divide(BigDecimal.ONE.subtract(getPercentage(input.getCommissionPct())))).subtract(input.getNettFare());
				if(commission.compareTo(BigDecimal.ZERO) > 1) {
					commission.add(BigDecimal.TEN);
				}
				commission = round(commission, input.getCountryCode());
			}
			sellingPrice.add(input.getNettFare().divide(BigDecimal.ONE.subtract(getPercentage(input.getCommissionPct()))));
			if(!Arrays.asList(new String[] {"MG","DB","TF","MN"}).contains(input.getClientType())) {
				sellingPrice.add(BigDecimal.TEN);
			}
			if(input.isWebFareSelected()) {
				sellingPrice = round(sellingPrice, input.getCountryCode());
			}
		}else {
			sellingPrice.add(input.getNettFare()).add(input.getCommission());
		}
//		If CommissionByPercent Then
//			if Client Type = TP
//				Commission = 0
//			else
//				Commission = (Nett Fare / (1 - (Commission Percent  * 0.01))) - Nett Fare;
//				if(Commission > 0 and Client Type = DU)
//					Commission = Commission + 10
//				end if
//				Commission = Round(Commission)
//			end if
//						
//			Selling Price = Nett Fare / (1 - (Commission Percent * 0.01))
//						
//			If Client Type NOT IN "MG","DB","TF","MN"  Then
//				Selling Price = Selling Price + 10
//			End If				
//	
//			If mbolWebFareSelected = False Then
//				Selling Price = Round UP(Selling Price)
//			end if
//				
//	    Else
//			Selling Price = Nett Fare + Commission
//	    End If
		
		if(input.isDiscountByPercent()) {
			if(Arrays.asList(new String[] {"DU", "DB"}).contains(input.getClientType())) {
				discount.add(input.getNettFare()).add(applyPercentage(commission, input.getDiscountPct()));
			}else if(Arrays.asList(new String[] {"MN", "TF", "TP"}).contains(input.getClientType())) {
				discount.add(commission);
			}
		}
		if(Arrays.asList(new String[] {"MN", "TF"}).contains(input.getClientType())) {
			discount = BigDecimal.ZERO;
		}
		
		//round down
		discount = round(discount, input.getCountryCode());
		nettCostInEO.add(input.getNettFare());
		nettFare.add(sellingPrice).add(input.getTax1()).add(input.getTax2()).subtract(discount);
		
		
//		If DiscountByPercent Then
//			IF Client Type IN "DU", "DB"
//					Discount = Round Down( Round Down(Nett Fare + Commission * DiscountPct * 0.01))
//			ELSE IF Client Type IN "MN", "TF", "TP"
//					Discount = Round Down(Commission)
//			ELSE
//				Discount = 0
//			END IF
//		End If
//	
//		If Client Type IN "MN","TF" THEN
//			Set Discount = 0
//		END IF
//						
//		Discount = Round Down(Discount)
//				
//		Nett Cost in EO = Nett Fare
//							
//	   	Nett Fare = (Selling Price + tax-1 + tax-2) - Discount
		
		
		
		if(input.isCwtAbsorb() && input.getFopType().equals("CX") && input.isMerchantFeeWaive()) {
			BigDecimal mFTotal = BigDecimal.ZERO;
			if(input.isUatp()) {
				if(input.getClientType().equals("TF")) {
					mFTotal.add(input.getTransactionFee());
				}else {
//					mFTotal.add(input.getSellingPrice());
				}
			}else {
				mFTotal = input.getNettFare();
				if(input.getClientType().equals("TF") && true) {
					mFTotal.add(input.getTransactionFee());
				}
			}
			merchantFeeAmount = applyPercentage(mFTotal, merchantFee.getMerchantFeePct());
		}
		totalSellingFare = input.getNettFare().add(merchantFeeAmount);
		if(input.isWebFareSelected()) {
			totalSellingFare = round(totalSellingFare, input.getCountryCode());
		}
//		If CWT Absorb is unchecked And FOP Type = "CX" And Waive Merchant Fee is Unchecked
//			If UATP checked Then
//				If Client Type = "TF" Then
//				   MFTotal = Transaction Fee
//				Else
//				   MFTotal = (Selling Price + tax-1 + tax-2) - Discount - Nett Fare (Original Input) - tax-1 + tax-2
//				End If
//			Else
//				MFTotal = Nett Fare
//				If Client Type = "TF" And gobjPNR.CompInfo.TFIncMF Then
//					MFTotal = Nett Fare + Transaction Fee
//				End If
//			End If
//							
//			Merchant Fee = MFTotal * Merchant Fee Percent * 0.01
//		Else
//			Merchant Fee = "0"
//		End If
//		
//		If mbolWebFareSelected = True Then
//			Total Selling Fare = Nett Fare + Merchant Fee
//		Else
//			Total Selling Fare = Round UP(Nett Fare + Merchant Fee, gstrAgcyCurrCode, "UP")
//		End If
		return result;
	}
}

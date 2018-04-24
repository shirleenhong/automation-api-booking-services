package com.cwt.bpg.cbt.exchange.order.calculator;

import com.cwt.bpg.cbt.calculator.CommonCalculator;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;


public class HkBspAirCalculator extends CommonCalculator implements Calculator{
	@Override
	public FeesBreakdown calculateFee(OtherServiceFeesInput input, Double merchantFeePct) {
		
//		Dim Nett Fare As Single
//		Dim Selling Price As Single
//		Dim Client Type As String
//		Dim Commission As Single
//		Dim Commission Percent As Single
//		Dim sngDiscountPct As Single
//		Dim Discount As Single
//		Dim MFTotal As Single
//
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
		return new FeesBreakdown();
	}
}

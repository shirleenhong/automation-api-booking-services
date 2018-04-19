package com.cwt.bpg.cbt.exchange.order;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.FOPTypes;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

@Service
public class OtherServiceFeesImpl implements OtherServiceFeesApi {

	@Override
	public FeesBreakdown calculateMiscFee(OtherServiceFeesInput input) {
		
		FeesBreakdown result = new FeesBreakdown();
		
		BigDecimal gstAmount = BigDecimal.ZERO;
		BigDecimal nettCostGst =  BigDecimal.ZERO;
		BigDecimal merchantFeeAmount =  BigDecimal.ZERO;
		BigDecimal sellingPriceInDi =  BigDecimal.ZERO;
		BigDecimal commission =  BigDecimal.ZERO;
		
		//TODO From DB
		Double merchantFeePct = getMerchantFeePct();
		BigDecimal nettCost = getNettCost();
		
		if(!input.isGstAbsorb()) {
			gstAmount = input.getSellingPrice()
					.multiply(getPercent(input.getGstPercent()));
			
			nettCostGst = nettCost
					.multiply(getPercent(input.getGstPercent()));
		}
		
		if(!input.isMerchantFeeAbsorb() 
			&& FOPTypes.CX.getCode().equals(input.getFopType())
			&& !input.isMerchantFeeWaive()) 
		{
			//TODO Round logic
			merchantFeeAmount = roundUp(
					input.getSellingPrice()
					.multiply(getValue(1D).add(getPercent(input.getGstPercent())))
					.multiply(getPercent(merchantFeePct)));
		}


		MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
		
		sellingPriceInDi = input.getSellingPrice().add(gstAmount).add(merchantFeeAmount)
								.divide(getValue(1D).add(getPercent(input.getGstPercent())), mc);

		if(sellingPriceInDi.compareTo(nettCost) < 0) {
			commission = sellingPriceInDi.subtract(nettCost);
		}
		
		result.setNettCostGst(nettCostGst);
		result.setCommission(commission);
		result.setGstAmount(gstAmount);
		result.setMerchantFee(merchantFeeAmount);
		result.setSellingPriceInDi(sellingPriceInDi);
		
		return result;
	}

	private BigDecimal getPercent(Double value) {
		return BigDecimal.valueOf(value * 0.01);
	}
	
	private BigDecimal getNettCost() {
		return BigDecimal.valueOf(2);
	}

	private BigDecimal roundUp(BigDecimal input) {

		return input.setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getValue(Double value) {
		return new BigDecimal(value, MathContext.DECIMAL64);
	}

	private Double getMerchantFeePct() {
		
		return 2D;
	}
}

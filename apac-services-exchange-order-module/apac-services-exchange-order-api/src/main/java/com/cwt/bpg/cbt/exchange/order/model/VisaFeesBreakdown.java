package com.cwt.bpg.cbt.exchange.order.model;

import java.math.BigDecimal;

public class VisaFeesBreakdown extends FeesBreakdown {
	private BigDecimal sellingPrice;
	private BigDecimal nettCostMerchantFee;
	private BigDecimal cwtHandlingMerchantFee;

	public VisaFeesBreakdown() {
		this.sellingPrice = BigDecimal.ZERO;
		this.commission = BigDecimal.ZERO;
		this.sellingPriceInDi = BigDecimal.ZERO;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

    public BigDecimal getNettCostMerchantFee()
    {
        return nettCostMerchantFee;
    }

    public void setNettCostMerchantFee(BigDecimal nettCostMerchantFee)
    {
        this.nettCostMerchantFee = nettCostMerchantFee;
    }

    public BigDecimal getCwtHandlingMerchantFee()
    {
        return cwtHandlingMerchantFee;
    }

    public void setCwtHandlingMerchantFee(BigDecimal cwtHandlingMerchantFee)
    {
        this.cwtHandlingMerchantFee = cwtHandlingMerchantFee;
    }
}

package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class MerchantFeePercentOutput implements Serializable {

	private static final long serialVersionUID = -3113449095535702594L;

	@ApiModelProperty(required = true, allowableValues = "CX,CC,INV")
	@NotNull
	private Double merchantFeePercent;

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}
}

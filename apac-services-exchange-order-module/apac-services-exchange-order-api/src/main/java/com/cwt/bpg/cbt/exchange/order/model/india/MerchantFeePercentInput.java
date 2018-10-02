package com.cwt.bpg.cbt.exchange.order.model.india;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.cwt.bpg.cbt.exchange.order.model.FeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FopType;
import io.swagger.annotations.ApiModelProperty;

public class MerchantFeePercentInput extends FeesInput {
	private static final long serialVersionUID = 7271039286429340584L;

	@ApiModelProperty(required = true, allowableValues = "CX,CC,INV")
	@NotNull
	private FopType fopType;

    @ApiModelProperty(required = true)
    @NotNull
	private int fopMode;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String productCode;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String ccType;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String fopNumber;

    public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public int getFopMode() {
		return fopMode;
	}

	public void setFopMode(int fopMode) {
		this.fopMode = fopMode;
	}

	public String getFopNumber() {
		return fopNumber;
	}

	public void setFopNumber(String fopNumber) {
		this.fopNumber = fopNumber;
	}

	public FopType getFopType() {
		return fopType;
	}

	public void setFopType(FopType fopType) {
		this.fopType = fopType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}

package com.cwt.bpg.cbt.exchange.order.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FormOfPayment implements Serializable {

    private static final long serialVersionUID = 1850641277169808339L;

    @NotNull
    @ApiModelProperty(required = true)
	private FopTypes fopType;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String ccType;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String ccNumber;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String expiryDate;

    public FopTypes getFopType() {
        return fopType;
    }

    public void setFopType(FopTypes fopType) {
        this.fopType = fopType;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

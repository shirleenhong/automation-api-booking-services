package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;

public class FeesInput implements Serializable {

    private static final long serialVersionUID = -5237125856544162255L;

    @ApiModelProperty(required = true)
    @NotEmpty
    private String clientAccountNumber;

    @ApiModelProperty
    private int clientId;

    private String vendorCode;

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getVendorCode()
    {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode)
    {
        this.vendorCode = vendorCode;
    }
}

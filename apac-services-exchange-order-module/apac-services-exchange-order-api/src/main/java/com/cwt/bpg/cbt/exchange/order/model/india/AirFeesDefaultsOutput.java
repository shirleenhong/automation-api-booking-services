package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.cwt.bpg.cbt.exchange.order.model.ClientFee;
import io.swagger.annotations.ApiModelProperty;

public class AirFeesDefaultsOutput implements Serializable {

	private static final long serialVersionUID = -3113449095535702594L;

	@ApiModelProperty(required = true, allowableValues = "CX,CC,INV")
	@NotNull
	private Double merchantFeePercent;

	private List<ClientFee> clientFees;

	public Double getMerchantFeePercent() {
		return merchantFeePercent;
	}

	public void setMerchantFeePercent(Double merchantFeePercent) {
		this.merchantFeePercent = merchantFeePercent;
	}

    public List<ClientFee> getClientFees() {
        return clientFees;
    }

    public void setClientFees(List<ClientFee> clientFees) {
        this.clientFees = clientFees;
    }
}

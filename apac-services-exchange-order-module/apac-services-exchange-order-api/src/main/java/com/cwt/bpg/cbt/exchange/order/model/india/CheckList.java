package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class CheckList implements Serializable {

	private static final long serialVersionUID = -3803907240885033917L;

	private Boolean passportCopiesReceived;

	private Boolean refundCopiesReceived;

	private Boolean vtmCardReceived;

	public Boolean isPassportCopiesReceived() {
		return passportCopiesReceived;
	}

	public void setPassportCopiesReceived(Boolean passportCopiesReceived) {
		this.passportCopiesReceived = passportCopiesReceived;
	}

	public Boolean isRefundCopiesReceived() {
		return refundCopiesReceived;
	}

	public void setRefundCopiesReceived(Boolean refundCopiesReceived) {
		this.refundCopiesReceived = refundCopiesReceived;
	}

	public Boolean isVtmCardReceived() {
		return vtmCardReceived;
	}

	public void setVtmCardReceived(Boolean vtmCardReceived) {
		this.vtmCardReceived = vtmCardReceived;
	}

}

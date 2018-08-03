package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class CheckList implements Serializable {

	private static final long serialVersionUID = -3803907240885033917L;

	private boolean passportCopiesReceived;

	private boolean refundCopiesReceived;

	private boolean vtmCardReceived;

	public boolean isPassportCopiesReceived() {
		return passportCopiesReceived;
	}

	public void setPassportCopiesReceived(boolean passportCopiesReceived) {
		this.passportCopiesReceived = passportCopiesReceived;
	}

	public boolean isRefundCopiesReceived() {
		return refundCopiesReceived;
	}

	public void setRefundCopiesReceived(boolean refundCopiesReceived) {
		this.refundCopiesReceived = refundCopiesReceived;
	}

	public boolean isVtmCardReceived() {
		return vtmCardReceived;
	}

	public void setVtmCardReceived(boolean vtmCardReceived) {
		this.vtmCardReceived = vtmCardReceived;
	}

}

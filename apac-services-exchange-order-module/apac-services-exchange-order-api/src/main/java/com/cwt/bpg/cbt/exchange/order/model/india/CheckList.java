package com.cwt.bpg.cbt.exchange.order.model.india;

public class CheckList {

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

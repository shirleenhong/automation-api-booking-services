package com.cwt.bpg.cbt.exchange.order.model;

public class MiscFeesInput extends OtherServiceFeesInput {

	private static final long serialVersionUID = 7271039286429340584L;

	protected boolean isGstAbsorb;

	public boolean isGstAbsorb() {
		return isGstAbsorb;
	}

	public void setGstAbsorb(boolean isGstAbsorb) {
		this.isGstAbsorb = isGstAbsorb;
	}

}

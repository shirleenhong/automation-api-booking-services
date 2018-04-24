package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class MiscFeesInput extends OtherServiceFeesInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7271039286429340584L;

	protected boolean isGstAbsorb;

	public boolean isGstAbsorb() {
		return isGstAbsorb;
	}

	public void setGstAbsorb(boolean isGstAbsorb) {
		this.isGstAbsorb = isGstAbsorb;
	}
	
}

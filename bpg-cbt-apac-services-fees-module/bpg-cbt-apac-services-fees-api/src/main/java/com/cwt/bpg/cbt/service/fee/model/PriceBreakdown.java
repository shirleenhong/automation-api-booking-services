package com.cwt.bpg.cbt.service.fee.model;

import java.io.Serializable;

public class PriceBreakdown implements Serializable {
	
	private static final long serialVersionUID = -8034379136146857674L;

	private int a;
	
	private int b;
	
	public PriceBreakdown() {
	}

	public PriceBreakdown(int i, int j) {
		this.a = i;
		this.b = j;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
	

}

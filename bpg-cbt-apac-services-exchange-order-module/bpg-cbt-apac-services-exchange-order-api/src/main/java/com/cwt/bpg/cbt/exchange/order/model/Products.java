package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String productName;
	
	private List<String> vendors;
	
	public Products() {
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}

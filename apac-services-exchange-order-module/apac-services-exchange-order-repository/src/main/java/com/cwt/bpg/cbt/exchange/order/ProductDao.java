package com.cwt.bpg.cbt.exchange.order;

public interface ProductDao {

	String removeVendor(String countryCode, String vendorCode);

	String removeProduct(String countryCode, String productCode);
}

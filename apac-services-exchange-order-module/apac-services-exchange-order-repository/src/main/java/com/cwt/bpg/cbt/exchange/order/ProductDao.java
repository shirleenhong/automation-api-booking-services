package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public interface ProductDao {

	public String removeVendor(String countryCode, String vendorCode);

	public String removeProduct(String countryCode, String productCode);
	
	public String saveProduct(String countryCode, BaseProduct product, boolean insertFlag);
	
	public String saveVendor(String countryCode, String productCode, Vendor vendor, boolean insertFlag);
}

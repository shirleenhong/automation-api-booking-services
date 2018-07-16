package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;
import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Vendor;

public interface VendorDAO {

	List<Vendor> listVendors();

	List<ContactInfo> listVendorContactInfo();
}

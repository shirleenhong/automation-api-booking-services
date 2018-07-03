package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

public interface ClientMerchantFeeDAO {
	
	List<MerchantFee> listMerchantFees(String countryCode);

}

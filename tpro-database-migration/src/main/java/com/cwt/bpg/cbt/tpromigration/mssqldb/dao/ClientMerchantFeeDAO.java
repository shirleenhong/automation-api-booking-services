package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.util.List;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.ClientMerchantFee;

public interface ClientMerchantFeeDAO {
	
	List<ClientMerchantFee> listMerchantFees();

}

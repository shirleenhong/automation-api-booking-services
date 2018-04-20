package com.cwt.bpg.cbt.tpromigration.mssqldb.dao;

import java.util.List;

import com.cwt.bpg.cbt.tpromigration.mssqldb.model.Currency;

public interface CurrencyDAO {
	
	List<Currency> listCurrencies();

}

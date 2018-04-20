package com.cwt.bpg.cbt.exchange.order;

public interface CurrencyApi<T> {
	
	T getRoundingRule(String currencyCode);

}

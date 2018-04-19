package com.cwt.bpg.cbt.exchange.order;

public interface CurrencyRoundingRuleApi<T> {
	
	T getRoundingRule(String currencyCode);

}

package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;

public interface CurrencyApi {
	
	CurrencyCodeRoundRule getRoundingRule(String currencyCode);

}

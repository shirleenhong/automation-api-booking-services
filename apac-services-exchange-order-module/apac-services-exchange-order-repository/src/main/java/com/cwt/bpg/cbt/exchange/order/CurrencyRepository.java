package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class CurrencyRepository {

	@Autowired
	private MorphiaComponent morphia;
	
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		return morphia.getDatastore().createQuery(CurrencyCodeRoundRule.class)
				.field("currencyCode")
				.equal(currencyCode)
				.get();
	}
}

package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.CurrencyCodeRoundRule;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class CurrencyRepository implements CurrencyApi {

	@Autowired
	private MorphiaComponent morphia;
	
	@Override
	public CurrencyCodeRoundRule getRoundingRule(String currencyCode) {
		List<CurrencyCodeRoundRule> roundingRule = morphia.getDatastore().createQuery(CurrencyCodeRoundRule.class)
				.field("currencyCode")
				.equal(currencyCode)
				.asList();
		return roundingRule.get(0);
	}
}

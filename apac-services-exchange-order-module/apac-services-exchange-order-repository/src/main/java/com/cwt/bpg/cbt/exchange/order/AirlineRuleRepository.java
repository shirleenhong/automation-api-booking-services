package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

@Repository
public class AirlineRuleRepository extends CommonRepository<AirlineRule, String> {

	public static final String KEY_COLUMN = "code";

	public AirlineRuleRepository() {
		super(AirlineRule.class, KEY_COLUMN);
	}
}

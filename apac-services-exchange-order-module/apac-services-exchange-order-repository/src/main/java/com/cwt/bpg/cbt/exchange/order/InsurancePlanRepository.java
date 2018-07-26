package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

@Repository
public class InsurancePlanRepository extends CommonRepository<InsurancePlan, String> {

	public static final String KEY_COLUMN = "type";

	public InsurancePlanRepository() {
		super(InsurancePlan.class, KEY_COLUMN);
	}
}

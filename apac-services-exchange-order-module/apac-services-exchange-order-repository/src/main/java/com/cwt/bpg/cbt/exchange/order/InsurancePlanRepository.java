package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class InsurancePlanRepository extends CommonRepository<InsurancePlan, String> {

	public static final String KEY_COLUMN = "type";

	public InsurancePlanRepository() {
		super(InsurancePlan.class, KEY_COLUMN);
	}
}

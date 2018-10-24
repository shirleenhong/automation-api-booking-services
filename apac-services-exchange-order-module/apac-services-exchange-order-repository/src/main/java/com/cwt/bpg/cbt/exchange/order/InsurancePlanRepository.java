package com.cwt.bpg.cbt.exchange.order;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class InsurancePlanRepository extends CommonRepository<InsurancePlan, ObjectId> {

	public static final String KEY_COLUMN = "id";

	public InsurancePlanRepository() {
		super(InsurancePlan.class, KEY_COLUMN);
	}
}

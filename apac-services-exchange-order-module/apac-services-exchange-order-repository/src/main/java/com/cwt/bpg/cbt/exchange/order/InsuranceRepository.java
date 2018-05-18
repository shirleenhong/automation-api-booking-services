package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;

@Repository
public class InsuranceRepository extends CommonRepository<Insurance> {

	public static final String KEY_COLUMN = "type";

	public InsuranceRepository() {
		super(Insurance.class, KEY_COLUMN);
	}
}

package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class InsuranceRepository {

	@Autowired
	private MorphiaComponent morphia;
	
	public List<Insurance> getAll() {
		return morphia.getDatastore().createQuery(Insurance.class)
				.asList();
	}
}

package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.Sort;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.CarVendor;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class CarVendorRepository extends CommonRepository<CarVendor, String>{
	
	private static final String CODE = "code";
	
	public CarVendorRepository() {
		super(CarVendor.class, CODE);
	}
	
	@Override
	public List<CarVendor> getAll() {
		return morphia.getDatastore().createQuery(CarVendor.class).order(Sort.ascending(CODE))
				.asList();
	}
}

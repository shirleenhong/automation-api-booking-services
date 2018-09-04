package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.Sort;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.VmpdReasonCode;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class VmpdReasonCodesRepository extends CommonRepository<VmpdReasonCode, String>{

	private static final String ID = "code";
	
	public VmpdReasonCodesRepository() {
		super(VmpdReasonCode.class, ID);
	}
	
	@Override
	public List<VmpdReasonCode> getAll() {
		return morphia.getDatastore().createQuery(VmpdReasonCode.class)
				.order(Sort.ascending(ID)).asList();
	}
}

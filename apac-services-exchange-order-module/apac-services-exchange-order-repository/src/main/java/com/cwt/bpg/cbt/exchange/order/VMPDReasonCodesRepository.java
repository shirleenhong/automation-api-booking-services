package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.Sort;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.VMPDReasonCode;

@Repository
public class VMPDReasonCodesRepository extends CommonRepository<VMPDReasonCode, String>{

	private static final String ID = "code";
	
	public VMPDReasonCodesRepository() {
		super(VMPDReasonCode.class, ID);
	}
	
	@Override
	public List<VMPDReasonCode> getAll() {
		return morphia.getDatastore().createQuery(VMPDReasonCode.class)
				.order(Sort.ascending(ID)).asList();
	}
}

package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class SequenceNumberRepository {

	@Autowired
	private MorphiaComponent morphia;

	public SequenceNumber get(String countryCode) {
				
		return morphia.getDatastore()
				.createQuery(SequenceNumber.class)
				.field("countryCode").equal(countryCode).get();
	}

	public Key<SequenceNumber> save(SequenceNumber sequenceNum) {
		return morphia.getDatastore().save(sequenceNum);		
	}

	public List<SequenceNumber> getAll() {		
		return morphia.getDatastore().createQuery(SequenceNumber.class).asList();
	}

	public Iterable<Key<SequenceNumber>> save(List<SequenceNumber> sequenceNums) {
		return morphia.getDatastore().save(sequenceNums);		
	}	
}

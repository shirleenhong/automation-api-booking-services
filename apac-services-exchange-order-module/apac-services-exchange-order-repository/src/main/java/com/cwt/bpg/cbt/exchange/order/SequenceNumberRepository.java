package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;


@Repository
public class SequenceNumberRepository {

	@Autowired
	private MorphiaComponent morphia;

	public List<SequenceNumber> get(String ... countryCodes) {
				
		Query<SequenceNumber> query = morphia.getDatastore()
				.createQuery(SequenceNumber.class);
		
		CriteriaContainer[] criteria = new CriteriaContainerImpl[countryCodes.length];
		
		for(int i=0 ; i<countryCodes.length; i++) {
			criteria[i] = query.criteria("countryCode").equalIgnoreCase(countryCodes[i]);
		}
		
		query.or(criteria);
		
		return query.asList();
	}

	public Key<SequenceNumber> save(SequenceNumber sequenceNum) {
		return morphia.getDatastore().save(sequenceNum);		
	}
	
	public Iterable<Key<SequenceNumber>> save(List<SequenceNumber> sequenceNums) {
		return morphia.getDatastore().save(sequenceNums);		
	}	
}

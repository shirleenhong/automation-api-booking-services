package com.cwt.bpg.cbt.airline;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.airline.model.Airline;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AirlineRepository extends CommonRepository<Airline, String>{

	static final String IATA_CODE = "iataCode";

	public AirlineRepository() {
		super(Airline.class, IATA_CODE);
	}
	
	public List<Airline> getAirlines(String airlineCode) {
		
		final Query<Airline> query = morphia.getDatastore().createQuery(Airline.class);
		
		if(StringUtils.isNotBlank(airlineCode)) {
			query.field(IATA_CODE).equalIgnoreCase(airlineCode);
		}

		return query.asList();	
	}
}

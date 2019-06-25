package com.cwt.bpg.cbt.air.contract;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.air.contract.model.AirContract;

@Service
public class AirContractService {

	@Autowired
	private AirContractRepository repository;

	@Cacheable(cacheNames = "air-contracts", key = "#countryCode + #airlineCode + #clientAccountNumber")
	public AirContract get(String countryCode, String airlineCode, String clientAccountNumber) {
		return repository.get(countryCode, airlineCode, clientAccountNumber);
	}

	public AirContract get(String id) {
		return repository.get(new ObjectId(id));
	}

	public AirContract save(AirContract airContract) {
		return repository.put(airContract);
	}

	public String delete(String id) {
		return repository.remove(new ObjectId(id));
	}
}

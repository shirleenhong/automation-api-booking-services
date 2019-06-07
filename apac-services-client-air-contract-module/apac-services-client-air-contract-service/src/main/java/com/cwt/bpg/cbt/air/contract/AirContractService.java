package com.cwt.bpg.cbt.air.contract;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

	public List<AirContract> save(List<AirContract> airContracts) {
		List<AirContract> savedAirContracts = new ArrayList<>();
		airContracts.forEach(a -> savedAirContracts.add(repository.put(a)));
		return savedAirContracts;
	}

	@CacheEvict(cacheNames = "air-contracts", allEntries = true)
	public String delete(String id) {
		return repository.remove(new ObjectId(id));
	}
}

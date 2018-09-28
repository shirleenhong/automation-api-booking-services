package com.cwt.bpg.cbt.air.contract;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.air.contract.AirContractRepository;
import com.cwt.bpg.cbt.air.contract.model.AirContract;

@Service
public class AirContractService {

	@Autowired
	private AirContractRepository repository;

	@Cacheable(cacheNames = "air-contract", key = "#countryCode + #airlineCode + #clientAccountNumber")
	public AirContract getAirContract(String countryCode, String airlineCode, String clientAccountNumber) {
		return repository.get(countryCode, airlineCode, clientAccountNumber);
	}

	@CachePut(cacheNames = "air-contract", key = "#airContract.countryCode + #airContract.airlineCode + #airContract.clientAccountNumber")
	public AirContract save(AirContract airContract) {
		return repository.put(airContract);
	}

	@CacheEvict(cacheNames = "air-contract", allEntries = true)
	public String delete(String id) {
		return repository.remove(new ObjectId(id));
	}
}

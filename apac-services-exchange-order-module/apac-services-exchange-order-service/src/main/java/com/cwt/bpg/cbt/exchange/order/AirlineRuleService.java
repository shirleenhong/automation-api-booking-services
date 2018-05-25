package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

@Service
public class AirlineRuleService {
	
	@Autowired
	private AirlineRuleRepository repository;

	@Cacheable(cacheNames = "airline-rules", key = "{airline-#root.methodName}")
	public List<AirlineRule> getAll() {
		return repository.getAll();
	}

	public AirlineRule save(AirlineRule airlineRule) {
		return repository.put(airlineRule);
	}

	@CacheEvict(cacheNames = "airline-rules", allEntries = true)
	public String delete(String airlineCode) {
		return repository.remove(airlineCode);
	}
}

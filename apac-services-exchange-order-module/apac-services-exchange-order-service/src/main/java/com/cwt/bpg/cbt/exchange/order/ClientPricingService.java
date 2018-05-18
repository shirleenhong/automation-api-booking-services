package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;
import com.cwt.bpg.cbt.exchange.order.model.Client;

@Service
public class ClientPricingService {

	@Cacheable(cacheNames = "client-pricing", key = "{#root.methodName}")
	public List<AirlineRule> getAll() {
		return null;
	}

	public Client save(Client client) {
		return null;
	}

	@CacheEvict(cacheNames = "client-pricing", allEntries = true)
	public String delete(String airlineCode) {
		return airlineCode;
	}

}

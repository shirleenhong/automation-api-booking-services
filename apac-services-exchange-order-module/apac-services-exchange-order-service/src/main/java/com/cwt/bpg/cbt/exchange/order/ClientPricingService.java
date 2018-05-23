package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Service
public class ClientPricingService {
	
	@Autowired
	private ClientPricingRepository clientPricingRepository;

	@Cacheable(cacheNames = "client-pricing", key = "{#root.methodName}")
	public List<Client> getAll() {
		return clientPricingRepository.getAll();
	}

	public Client save(Client client) {
		return clientPricingRepository.put(client);
	}

	@CacheEvict(cacheNames = "client-pricing", allEntries = true)
	public String delete(int keyValue) {
		return clientPricingRepository.remove(keyValue);
	}

	public Client getClient(int id) {
		return clientPricingRepository.getClient(id);
	}

}

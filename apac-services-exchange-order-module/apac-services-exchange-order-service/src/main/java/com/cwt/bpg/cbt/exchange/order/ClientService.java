package com.cwt.bpg.cbt.exchange.order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	public static final String KEY = "getAll";
	
	@Cacheable(cacheNames = "clients", key = "#root.methodName")
	public List<Client> getAll() {
		return clientRepository.getAll();
	}

	@Caching(
	    put = {@CachePut(cacheNames = "clients", key = "#client.clientId")},
	    evict={@CacheEvict(cacheNames = "clients", key = "#root.target.KEY")
	 })
	public Client save(Client client) {
		return clientRepository.put(client);
	}

	@CacheEvict(cacheNames = "clients", key = "#root.target.KEY")
	public String delete(String keyValue) {
		return clientRepository.remove(keyValue);
	}

	@Cacheable(cacheNames = "clients", key = "#id")
	public Client getClient(int id) {
		return clientRepository.get(id);
	}

	@Cacheable(cacheNames="clients", key="#clientAccountNumber", condition="#clientAccountNumber != null")
	public Client getClient(String clientAccountNumber) {
		return clientRepository.getClient(clientAccountNumber);
	}
	
	public List<ClientPricing> getClientPricings(String clientAccountNumber,
			String tripType) {

		Client client = getClient(clientAccountNumber);

		List<ClientPricing> clientPricings = Optional
				.ofNullable(client.getClientPricings()).orElse(Collections.emptyList());

		return clientPricings.stream().filter(
				pricing -> tripType != null && pricing.getTripType().equals(tripType))
				.collect(Collectors.toList());
	}

	public Client getDefaultClient() {
		return getClient(-1);		
	}

}

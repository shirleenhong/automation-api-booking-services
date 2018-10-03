package com.cwt.bpg.cbt.exchange.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Cacheable(cacheNames = "clients", key = "#root.methodName")
	public List<Client> getAll() {
		return clientRepository.getAll();
	}

	@CachePut(cacheNames = "clients", key = "#client.clientId")
	public Client save(Client client) {
		return clientRepository.put(client);
	}

	@CacheEvict(cacheNames = "clients", allEntries = true)
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
	
	public List<ClientPricing> getClientPricings(String clientAccountNumber, String tripType){
		List<ClientPricing> clientPricings = new ArrayList<>();
		
		Client client = getClient(clientAccountNumber);
		
		client.getClientPricings().stream()
				.filter(pricing -> pricing.getTripType().equals(tripType))
				.forEach(pricing -> {
					pricing.setFeeOption(null);
					pricing.setTransactionFees(null);
					clientPricings.add(pricing);

				});

		return clientPricings;
	}

	public Client getDefaultClient() {
		return getClient(-1);		
	}

}

package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Client;

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
	public String delete(int keyValue) {
		return clientRepository.remove(keyValue);
	}

	@Cacheable(cacheNames = "clients", key = "#id")
	public Client getClient(int id) {
		return clientRepository.getClient(id);
	}

	@Cacheable(cacheNames="clients", key="#profileName")
	public Client getClient(String profileName) {
		return clientRepository.getClient(profileName);
	}

	public Client getDefaultClient() {
		return getClient(-1);		
	}

}

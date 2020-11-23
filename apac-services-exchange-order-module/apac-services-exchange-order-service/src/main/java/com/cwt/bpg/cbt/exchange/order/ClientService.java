package com.cwt.bpg.cbt.exchange.order;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.ClientPricing;
import com.cwt.bpg.cbt.exchange.order.model.FeesInput;
import com.cwt.bpg.cbt.exchange.order.model.india.AirFeesDefaultsInput;

@Service
public class ClientService
{

    @Autowired
    private ClientRepository clientRepository;

    @Cacheable(cacheNames = "clients", key = "#root.methodName")
    public List<Client> getAll()
    {
        return clientRepository.getAll();
    }

    public List<Client> save(List<Client> clients)
    {
        return clients.stream()
                .map(client -> clientRepository.put(client))
                .collect(Collectors.toList());
    }

    public String delete(String keyValue)
    {
        return clientRepository.remove(keyValue);
    }

    @Cacheable(cacheNames = "clients", key = "#clientAccountNumber", condition = "#clientAccountNumber != null")
    public Client getClient(String clientAccountNumber)
    {
        return clientRepository.getClient(clientAccountNumber);
    }

    @Cacheable(cacheNames = "clients", key = "#clientId", condition = "#clientId != null")
    public Client getClient(Integer clientId)
    {
        return clientRepository.get(clientId);
    }

    public List<ClientPricing> getClientPricings(AirFeesDefaultsInput input)
    {
        Client client = getClient(input);

        List<ClientPricing> clientPricings = Optional.ofNullable(client.getClientPricings())
                .orElse(Collections.emptyList());

        return clientPricings.stream().filter(pricing -> pricing.getTripType().equals(input.getTripType()))
                .collect(Collectors.toList());
    }

    public Client getDefaultClient()
    {
        return getClient(-1);
    }

    public Client getClient(FeesInput input)
    {
        if (input.getClientId() != 0)
        {
            return getClient(input.getClientId());
        }
        else
        {
            return getClient(input.getClientAccountNumber());
        }
    }

}

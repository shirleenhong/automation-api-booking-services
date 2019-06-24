package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Airport;

@Service
public class AirportService {
    @Autowired
    private AirportRepository repository;

    @Cacheable(cacheNames = "airports", key = "#root.methodName")
    public List<Airport> getAll() {
        return repository.getAll();
    }

    @Cacheable(cacheNames = "airports", key = "#airportCode", condition="#airportCode != null")
    public Airport getAirport(String airportCode) {
        return repository.get(airportCode);
    }

    public Airport save(Airport airport) {
        return repository.put(airport);
    }

    public String delete(String airportCode) {
        return repository.remove(airportCode);
    }
}

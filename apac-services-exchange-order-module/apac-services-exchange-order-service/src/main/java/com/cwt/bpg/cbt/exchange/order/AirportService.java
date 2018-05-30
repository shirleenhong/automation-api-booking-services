package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    @Autowired
    private AirportRepository repository;

    @Cacheable(cacheNames = "airports", key = "#root.methodName")
    public List<Airport> getAll() {
        return repository.getAll();
    }

    @Cacheable(cacheNames = "airports", key = "#airportCode")
    public Airport getAirport(String airportCode) {
        return repository.get(airportCode);
    }

    @CachePut(cacheNames = "airports", key = "#airport.code")
    public Airport save(Airport airport) {
        return repository.put(airport);
    }

    @CacheEvict(cacheNames = "airports", allEntries = true)
    public String delete(String airportCode) {
        return repository.remove(airportCode);
    }

}

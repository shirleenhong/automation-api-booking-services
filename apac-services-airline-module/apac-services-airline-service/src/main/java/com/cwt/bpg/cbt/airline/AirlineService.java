package com.cwt.bpg.cbt.airline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.airline.model.Airline;

@Service
public class AirlineService
{

    @Autowired
    private AirlineRepository airlineRepository;

    @Cacheable(cacheNames = "airlines", key = "#airlineCode", condition = "#airlineCode != null")
    public Airline getAirline(String airlineCode)
    {
        List<Airline> airlines = airlineRepository.getAirlines(airlineCode);
        return !airlines.isEmpty() ? airlines.get(0) : null;
    }

    public List<Airline> getAirlines()
    {
        return airlineRepository.getAirlines(null);
    }

    @CachePut(cacheNames = "airlines", key = "#airline.iataCode")
    public Airline save(Airline airline)
    {
        return airlineRepository.put(airline);
    }

    @CacheEvict(cacheNames = "airlines", key = "#airlineCode")
    public String delete(String airlineCode)
    {
        return airlineRepository.remove(airlineCode);
    }

    public List<Airline> getAirlinesByTicketingCode(String ticketingCode)
    {
        return airlineRepository.getAirlinesByTicketingCode(ticketingCode);
    }

}

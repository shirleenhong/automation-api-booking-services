package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository repository;

    @Cacheable(cacheNames = "cities", key = "{#root.methodName}")
    public List<City> getAllCities() {
        return repository.getAll();
    }

    public List<City> getCities(String cityCode) {
        return repository.getCities(cityCode);
    }

    public City save(City city) {
        return repository.put(city);
    }

    @CacheEvict(cacheNames = "cities", allEntries = true)
    public String delete(City city) {
        return repository.remove(city);
    }

}

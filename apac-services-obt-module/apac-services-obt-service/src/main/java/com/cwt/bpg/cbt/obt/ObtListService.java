package com.cwt.bpg.cbt.obt;

import com.cwt.bpg.cbt.obt.model.ObtList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ObtListService {

    @Autowired
    private ObtListRepository repository;

    @Cacheable(cacheNames = "obt-list", key = "#countryCode")
    public ObtList get(String countryCode) {
        return repository.get(countryCode);
    }

    @CachePut(cacheNames = "obt-list", key = "#obtList.countryCode")
    public ObtList save(ObtList obtList) {
        return repository.put(obtList);
    }

    @CacheEvict(cacheNames = "obt-list", allEntries = true)
    public String delete(String countryCode) {
        return repository.remove(countryCode);
    }

}

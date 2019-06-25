package com.cwt.bpg.cbt.obt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.obt.model.ObtList;

@Service
public class ObtListService {

    @Autowired
    private ObtListRepository repository;

    @Cacheable(cacheNames = "obt-list", key = "#countryCode")
    public ObtList get(String countryCode) {
        return repository.get(countryCode);
    }

    public ObtList save(ObtList obtList) {
        return repository.put(obtList);
    }

    public String delete(String countryCode) {
        return repository.remove(countryCode);
    }
}

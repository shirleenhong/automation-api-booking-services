package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Remark;

@Service
public class RemarkService {

	@Autowired
	private RemarkRepository repository;

	@Cacheable(cacheNames = "remarks", key = "#root.methodName")
	public List<Remark> getAll() {
		return repository.getAll();
	}

	@CachePut(cacheNames = "remarks", key = "#remark.id")
	public Remark save(Remark remark) {
		System.out.println(">>> " + remark.getId().toString());
		return repository.put(remark);
	}

	@CacheEvict(cacheNames = "remarks", allEntries = true)
	public String delete(String id) {
		return repository.remove(new ObjectId(id));
	}

	@Cacheable(cacheNames = "remarks", key = "#id", condition = "#id != null")
	public Remark getRemark(String id) {
		return repository.get(new ObjectId(id));
	}
	
	@Cacheable(cacheNames = "remarks", key="{#countryCode, #productType, #remarkType}")
	public List<Remark> getRemarks(String countryCode, String productType, String remarkType) {
		return repository.getRemarks(countryCode, productType, remarkType);
	}
}

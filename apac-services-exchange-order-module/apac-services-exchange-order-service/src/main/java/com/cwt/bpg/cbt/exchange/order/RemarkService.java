package com.cwt.bpg.cbt.exchange.order;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Remark save(Remark remark) {
		return repository.put(remark);
	}

	public String delete(String id) {
		return repository.remove(new ObjectId(id));
	}

	@Cacheable(cacheNames = "remarks", key = "#id", condition = "#id != null")
	public Remark getRemark(String id) {
		return repository.get(new ObjectId(id));
	}
	
	@Cacheable(cacheNames = "remarks", key="{#countryCode, #productType, #remarkType}")
	public List<String> getRemarks(String countryCode, String productType, String remarkType) {
		List<Remark> remarkList = repository.getRemarks(countryCode, productType, remarkType);
		return remarkList.stream().map(Remark::getText).collect(Collectors.toList());
	}
}

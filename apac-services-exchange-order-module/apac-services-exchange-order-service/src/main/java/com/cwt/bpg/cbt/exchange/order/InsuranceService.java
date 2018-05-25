package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;

@Service
public class InsuranceService {

	@Autowired
	private InsuranceRepository insuranceRepository;

	@Cacheable(cacheNames = "insurance-types", key="{#root.methodName}")
	public List<Insurance> getAll() {
		return insuranceRepository.getAll();
	}

	public Insurance putInsurance(Insurance insurance) {
		return insuranceRepository.put(insurance);
	}

	@CacheEvict(cacheNames = "insurance-types", allEntries = true)
	public String remove(String type) {
		return insuranceRepository.remove(type);
	}
}

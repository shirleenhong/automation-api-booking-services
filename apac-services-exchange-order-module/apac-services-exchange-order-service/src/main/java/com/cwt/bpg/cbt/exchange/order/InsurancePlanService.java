package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

@Service
public class InsurancePlanService {

	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Cacheable(cacheNames = "insurance-types", key="#root.methodName")
	public List<InsurancePlan> getAll() {
		return insurancePlanRepository.getAll();
	}

	public InsurancePlan putInsurancePlan(InsurancePlan insurancePlan) {
		return insurancePlanRepository.put(insurancePlan);
	}

	@CacheEvict(cacheNames = "insurance-types", allEntries = true)
	public String remove(String type) {
		return insurancePlanRepository.remove(type);
	}
}
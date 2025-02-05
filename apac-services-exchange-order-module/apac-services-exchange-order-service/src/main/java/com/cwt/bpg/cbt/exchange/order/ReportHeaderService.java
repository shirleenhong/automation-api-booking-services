package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;

@Service
public class ReportHeaderService {

	@Autowired
	private ReportHeaderRepository repository;

	@Cacheable(cacheNames = "report-headers", key = "#countryCode", condition="#countryCode != null")
	public ReportHeader getHeaderReport(String countryCode) {
		return repository.get(countryCode);
	}

	public ReportHeader save(ReportHeader reportHeader) {
		return repository.put(reportHeader);
	}

	public String delete(String countryCode) {
		return repository.remove(countryCode);
	}
}

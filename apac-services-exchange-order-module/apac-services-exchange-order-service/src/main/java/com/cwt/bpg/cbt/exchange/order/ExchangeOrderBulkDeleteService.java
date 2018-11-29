package com.cwt.bpg.cbt.exchange.order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

@Service
@EnableScheduling
public class ExchangeOrderBulkDeleteService {

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;

	private static final Logger logger = LoggerFactory
			.getLogger(ExchangeOrderBulkDeleteService.class);

	@Scheduled(cron = "${exchange.order.purge.schedule}")
	public void bulkDeleteExchangeOrder() {

		List<ExchangeOrder> eoNoRecordLocatorList = Optional
				.ofNullable(exchangeOrderRepo.getExchangeOrderNoRecordLocator())
				.orElse(Collections.emptyList());

		logger.info("Size of exchangeOrderTransactions to delete: {}",
				eoNoRecordLocatorList.size());

		eoNoRecordLocatorList.forEach(eo -> {
			String deletedEo = exchangeOrderRepo.remove(eo);
			logger.info("Deleted eoNumber: {}", deletedEo);
		});

		logger.info("Finished scheduled deletion of exchange order. Deleted records: {}",
				eoNoRecordLocatorList.size());
	}
}

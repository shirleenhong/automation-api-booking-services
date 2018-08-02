package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;

@Service
public class ExchangeOrderService {

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;

	@Autowired
	private ExchangeOrderInsertService eoInsertService;

	@Autowired
	private ExchangeOrderUpdateService eoUpdateService;

	@CachePut(cacheNames = "exchange-orders", key = "#exchangeOrder.eoNumber")
	BaseExchangeOrder saveExchangeOrder(BaseExchangeOrder exchangeOrder)
			throws ExchangeOrderNoContentException {

		String eoNumber = exchangeOrder.getEoNumber();
		if (eoNumber == null) {
			return eoInsertService.insert(exchangeOrder);
		}
		else {
			return eoUpdateService.update(exchangeOrder);
		}
	}

	@Cacheable(cacheNames = "exchange-orders", key = "#eoNumber")
	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(eoNumber);
	}

	@Cacheable(cacheNames = "exchange-orders", key = "#recordLocator")
	List<ExchangeOrder> getExchangeOrderByRecordLocator(String recordLocator) {
		return exchangeOrderRepo.getByRecordLocator(recordLocator);
	}

	List<IndiaExchangeOrder> getIndiaExchangeOrderByRecordLocator(String recordLocator) {
		return exchangeOrderRepo.getIndiaExchangeOrderByRecordLocator(recordLocator);
	}

	List<ExchangeOrder> search(final ExchangeOrderSearchParam param) {
		return exchangeOrderRepo.search(param);
	}

	boolean update(ExchangeOrder param) {
		return exchangeOrderRepo.updateFinance(param);
	}

	public BaseExchangeOrder getExchangeOrder(String countryCode, String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(countryCode, eoNumber);
	}

}

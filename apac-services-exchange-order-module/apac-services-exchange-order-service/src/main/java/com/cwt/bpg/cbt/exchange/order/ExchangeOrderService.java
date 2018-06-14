package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

@Service
public class ExchangeOrderService {

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;
	
	public ExchangeOrder saveExchangeOrder(ExchangeOrder exchangeOrder) {
		
		exchangeOrder.setEoNumber(setEoNumber(exchangeOrder.getCountryCode()));
		
		ExchangeOrder result = new ExchangeOrder();
		result.setEoNumber(exchangeOrderRepo.saveOrUpdate(exchangeOrder));
		
		return result;
	}

	private String setEoNumber(String countryCode) {

		return Country.getCountry(countryCode).getId()
				.concat(String.valueOf(System.currentTimeMillis()));
	}
	
}

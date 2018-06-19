package com.cwt.bpg.cbt.exchange.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

@Service
public class ExchangeOrderService {

	private SimpleDateFormat sdf = new SimpleDateFormat("yymm");
	
	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;
	
	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;
	
	public ExchangeOrder saveExchangeOrder(ExchangeOrder exchangeOrder) {
		
		exchangeOrder.setEoNumber(setEoNumber(exchangeOrder.getCountryCode()));
		
		ExchangeOrder result = new ExchangeOrder();
		result.setEoNumber(exchangeOrderRepo.saveOrUpdate(exchangeOrder));
		
		return result;
	}

	private String setEoNumber(String countryCode) {

		return sdf.format((new Date()))
				.concat(Country.getCountry(countryCode).getId())
				.concat(String.valueOf(sequenceNumberRepo.getSequenceNumber()));
	}

    public ExchangeOrder getExchangeOrder(String eoNumber) {
        return exchangeOrderRepo.getExchangeOrder(eoNumber);
    }
}

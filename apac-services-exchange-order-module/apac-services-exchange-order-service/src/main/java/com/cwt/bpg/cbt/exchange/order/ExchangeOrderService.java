package com.cwt.bpg.cbt.exchange.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;

@Service
@EnableScheduling
public class ExchangeOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);
	
	@Value("${exchange.order.max.retry.count}")
	private int maxRetryCount;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM");
	
	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;
	
	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;
	
	public ExchangeOrder saveExchangeOrder(ExchangeOrder exchangeOrder) {
		
		if(exchangeOrder.getEoNumber() == null) {
			exchangeOrder.setEoNumber(getEoNumber(exchangeOrder.getCountryCode()));
		}
		
		ExchangeOrder result = new ExchangeOrder();
		result.setEoNumber(exchangeOrderRepo.saveOrUpdate(exchangeOrder));
		
		return result;
	}

	private String getEoNumber(String countryCode) {
		 
		return LocalDate.now().format(formatter)
				.concat(Country.getCountry(countryCode).getId())
				.concat(String.format("%05d", getSequenceNumber(countryCode)));
	}

	public int getSequenceNumber(String countryCode) {
		
		int newSequenceNum = -1;		
		int retryCount = 0;
				
		do {
			
			try {		
				SequenceNumber sequenceNumber = getSequenceNum(sequenceNumberRepo.get(countryCode));
				
				if(sequenceNumber != null) {
					newSequenceNum = sequenceNumber.getValue() + 1;						
				}
				else {
					newSequenceNum = 1;
					sequenceNumber = new SequenceNumber();										
					sequenceNumber.setCountryCode(countryCode);					
				}
				
				sequenceNumber.setValue(newSequenceNum);
								
				sequenceNumberRepo.save(sequenceNumber);
			}
			catch(ConcurrentModificationException e) {
				LOGGER.error("Exception encountered while saving sequence number", e);
				LOGGER.info("Retrying {}", retryCount++);
				newSequenceNum = -1;
			}		
		}
		while(retryCount < maxRetryCount && newSequenceNum == -1);
		
		return newSequenceNum;
	}
	
	private SequenceNumber getSequenceNum(List<SequenceNumber> list) {
		
		return !list.isEmpty() ? list.get(0) : null;
	}

	@Scheduled(cron = "${exchange.order.reset.schedule.india}")
	protected void resetIndiaSequenceNumber() {
		
		List<SequenceNumber> sequenceNums = sequenceNumberRepo.get(Country.INDIA.getCode());
		
		Iterable<Key<SequenceNumber>> result = reset(sequenceNums);
		
		LOGGER.info("Reset India sequence numbers {}", result);
	}

	private Iterable<Key<SequenceNumber>> reset(List<SequenceNumber> sequenceNums) {
		
		for(SequenceNumber sn : sequenceNums) {
			sn.setValue(0);			
		}	
		
		return sequenceNumberRepo.save(sequenceNums);
	}
	
	@Scheduled(cron = "${exchange.order.reset.schedule.hk.sg}")
	protected void resetHkSgSequenceNumber() {
		
		List<SequenceNumber> sequenceNums = sequenceNumberRepo.get(
				Country.SINGAPORE.getCode(), 
				Country.HONG_KONG.getCode());
		
		Iterable<Key<SequenceNumber>> result = reset(sequenceNums);
		
		LOGGER.info("Reset HK and SG sequence numbers {}", result);
	}

	@Cacheable(cacheNames = "exchange-orders", key = "#eoNumber")
    public ExchangeOrder getExchangeOrder(String eoNumber) {
        return exchangeOrderRepo.getExchangeOrder(eoNumber);
    }
}

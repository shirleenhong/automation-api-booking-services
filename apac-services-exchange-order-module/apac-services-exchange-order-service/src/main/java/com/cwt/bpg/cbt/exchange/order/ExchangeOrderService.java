package com.cwt.bpg.cbt.exchange.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;

@Service
@EnableScheduling
public class ExchangeOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);
	
	private static final int MAX_RETRY_COUNT = 3;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM");
	
	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;
	
	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;
	
	public ExchangeOrder saveExchangeOrder(ExchangeOrder exchangeOrder) {
		
		exchangeOrder.setEoNumber(getEoNumber(exchangeOrder.getCountryCode()));
		
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
				SequenceNumber sequenceNum = sequenceNumberRepo.get(countryCode);
					
				if(sequenceNum != null) {
					newSequenceNum = sequenceNum.getValue() + 1;						
				}
				else {
					newSequenceNum = 1;
					sequenceNum = new SequenceNumber();										
					sequenceNum.setCountryCode(countryCode);					
				}
				
				sequenceNum.setValue(newSequenceNum);
								
				sequenceNumberRepo.save(sequenceNum);
			}
			catch(Exception e) {
				LOGGER.error("Exception encountered while saving sequence number", e);
				LOGGER.debug("Retrying {}", retryCount++);
				newSequenceNum = -1;
			}		
		}
		while(retryCount < MAX_RETRY_COUNT && newSequenceNum == -1);
		
		return newSequenceNum;
	}
	
	@Scheduled(cron = "0 0 12 1 * ?")
	protected void resetSequenceNumber() {
		
		List<SequenceNumber> sequenceNums = sequenceNumberRepo.getAll();
		
		for(SequenceNumber sn : sequenceNums) {
			sn.setValue(0);			
		}	
		
		Iterable<Key<SequenceNumber>> result = sequenceNumberRepo.save(sequenceNums);
		
		LOGGER.info("Reset sequence numbers {}", result);
	}
}

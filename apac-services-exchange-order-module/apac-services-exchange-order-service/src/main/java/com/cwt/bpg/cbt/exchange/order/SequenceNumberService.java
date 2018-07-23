package com.cwt.bpg.cbt.exchange.order;

import java.util.ConcurrentModificationException;
import java.util.List;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;

@Service
@EnableScheduling
class SequenceNumberService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceNumberService.class);

	@Value("${exchange.order.max.retry.count}")
	private int maxRetryCount;

	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;

	int getSequenceNumber(String countryCode) {

		int newSequenceNum;
		int retryCount = 0;

		do {
			try {
				SequenceNumber sequenceNumber = getSequenceNum(sequenceNumberRepo.get(countryCode));

				if (sequenceNumber != null) {
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
			catch (ConcurrentModificationException e) {
				LOGGER.error("Exception encountered while saving sequence number", e);
				LOGGER.info("Retrying {}", retryCount++);
				newSequenceNum = -1;
			}
		}
		while (retryCount < maxRetryCount && newSequenceNum == -1);

		return newSequenceNum;
	}

	SequenceNumber getSequenceNum(List<SequenceNumber> list) {

		return !list.isEmpty() ? list.get(0) : null;
	}

	@Scheduled(cron = "${exchange.order.reset.schedule.in}")
	void resetIndiaSequenceNumber() {

		List<SequenceNumber> sequenceNumbers = sequenceNumberRepo.get(Country.INDIA.getCode());

		Iterable<Key<SequenceNumber>> result = reset(sequenceNumbers);

		LOGGER.info("Reset India sequence numbers {}", result);
	}

	@Scheduled(cron = "${exchange.order.reset.schedule.hk.sg}")
	void resetHkSgSequenceNumber() {

		List<SequenceNumber> sequenceNumbers = sequenceNumberRepo.get(Country.SINGAPORE.getCode(),
				Country.HONG_KONG.getCode());

		Iterable<Key<SequenceNumber>> result = reset(sequenceNumbers);

		LOGGER.info("Reset HK and SG sequence numbers {}", result);
	}

	private Iterable<Key<SequenceNumber>> reset(List<SequenceNumber> sequenceNumbers) {

		for (SequenceNumber sn : sequenceNumbers) {
			sn.setValue(0);
		}

		return sequenceNumberRepo.save(sequenceNumbers);
	}
}

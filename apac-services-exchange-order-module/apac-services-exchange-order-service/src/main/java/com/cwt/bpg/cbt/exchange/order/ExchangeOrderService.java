package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.calculator.CalculatorUtils.scale;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

import org.mongodb.morphia.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import com.cwt.bpg.cbt.utils.ServiceUtils;

@Service
@EnableScheduling
public class ExchangeOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);

	@Value("${exchange.order.max.retry.count}")
	private int maxRetryCount;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM")
			.withZone(ZoneId.of("UTC"));

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;

	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;
	
	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ServiceUtils serviceUtils;
	
	@CachePut(cacheNames = "exchange-orders", key = "#exchangeOrder.eoNumber")
	public ExchangeOrder saveExchangeOrder(ExchangeOrder exchangeOrder)
			throws ExchangeOrderNoContentException {

		ExchangeOrder result = new ExchangeOrder();
		setScale(exchangeOrder);
		
		final String eoNumber = exchangeOrder.getEoNumber();
		if (eoNumber == null) {
			exchangeOrder.setCreateDateTime(Instant.now());
			exchangeOrder.setEoNumber(getEoNumber(exchangeOrder.getCountryCode()));
			

			Optional<BaseProduct> isProductExist = Optional.ofNullable(
			        productService.getProductByCode(exchangeOrder.getCountryCode(),exchangeOrder.getProductCode()));

			BaseProduct product = isProductExist
					.orElseThrow(() -> new IllegalArgumentException(
							"Product [ " + exchangeOrder.getProductCode() + " ] not found."));

			Optional<Vendor> isVendorExist = product.getVendors().stream()
					.filter(i -> i.getCode().equals(exchangeOrder.getVendor().getCode()))
					.findFirst();

			if(!isVendorExist.isPresent()) {
					throw new IllegalArgumentException(
							"Vendor [ " + exchangeOrder.getVendor().getCode()
	                                + " ] not found in Product [ " + exchangeOrder.getProductCode() + " ] ");
			}
			
			result.setEoNumber(exchangeOrderRepo.save(exchangeOrder));
		}
		else {
			Optional<ExchangeOrder> isEoExist = Optional.ofNullable(getExchangeOrder(eoNumber));

			ExchangeOrder existingExchangeOrder = isEoExist
					.orElseThrow(() -> new ExchangeOrderNoContentException(
							"Exchange order number not found: [ " + eoNumber + " ]"));

			LOGGER.info("Existing Exchange order number: {} with country code {}",
					existingExchangeOrder.getEoNumber(),
					existingExchangeOrder.getCountryCode());
			
			exchangeOrder.setUpdateDateTime(Instant.now());
			
			serviceUtils.modifyTargetObject(exchangeOrder, existingExchangeOrder);
			result = exchangeOrderRepo.update(existingExchangeOrder);
		}

		return result;
	}

	private void setScale(ExchangeOrder exchangeOrder) {
		
		int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());
		
		exchangeOrder.setCommission(scale(exchangeOrder.getCommission(), scale));
		exchangeOrder.setMerchantFee(scale(exchangeOrder.getMerchantFee(), scale));
		exchangeOrder.setNettCost(scale(exchangeOrder.getNettCost(), scale));
		exchangeOrder.setGstAmount(scale(exchangeOrder.getGstAmount(), scale));
		exchangeOrder.setTax1(scale(exchangeOrder.getTax1(), scale));
		exchangeOrder.setTax2(scale(exchangeOrder.getTax2(), scale));
		exchangeOrder.setTotal(scale(exchangeOrder.getTotal(), scale));
		exchangeOrder.setSellingPrice(scale(exchangeOrder.getSellingPrice(), scale));
		exchangeOrder.setTotalSellingPrice(scale(exchangeOrder.getTotalSellingPrice(), scale));
	}

	private String getEoNumber(String countryCode) {

		return LocalDate.now().format(formatter)
                .concat(Country.getCountry(countryCode).getId())
				.concat(String.format("%05d", getSequenceNumber(countryCode)));
	}

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

	private SequenceNumber getSequenceNum(List<SequenceNumber> list) {

		return !list.isEmpty() ? list.get(0) : null;
	}

	@Scheduled(cron = "${exchange.order.reset.schedule.in}")
	void resetIndiaSequenceNumber() {

		List<SequenceNumber> sequenceNumbers = sequenceNumberRepo.get(Country.INDIA.getCode());

		Iterable<Key<SequenceNumber>> result = reset(sequenceNumbers);

		LOGGER.info("Reset India sequence numbers {}", result);
	}

	private Iterable<Key<SequenceNumber>> reset(List<SequenceNumber> sequenceNumbers) {

		for (SequenceNumber sn : sequenceNumbers) {
			sn.setValue(0);
		}

		return sequenceNumberRepo.save(sequenceNumbers);
	}

	@Scheduled(cron = "${exchange.order.reset.schedule.hk.sg}")
	void resetHkSgSequenceNumber() {

		List<SequenceNumber> sequenceNumbers = sequenceNumberRepo.get(Country.SINGAPORE.getCode(),
				Country.HONG_KONG.getCode());

		Iterable<Key<SequenceNumber>> result = reset(sequenceNumbers);

		LOGGER.info("Reset HK and SG sequence numbers {}", result);
	}

	@Cacheable(cacheNames = "exchange-orders", key = "#eoNumber")
	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderRepo.getExchangeOrder(eoNumber);
	}

	@Cacheable(cacheNames = "exchange-orders", key = "#recordLocator")
	public List<ExchangeOrder> getExchangeOrderByRecordLocator(String recordLocator) {
		return exchangeOrderRepo.getByRecordLocator(recordLocator);
	}
}
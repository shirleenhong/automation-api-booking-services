package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

@Service
public class ExchangeOrderInsertService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM")
			.withZone(ZoneId.of("UTC"));

	@Autowired
	private ProductService productService;

	@Autowired
	private SequenceNumberService sequenceNumberService;

	@Autowired
	private ExchangeOrderRepository exchangeOrderRepo;

	BaseExchangeOrder insert(BaseExchangeOrder exchangeOrder) {
		exchangeOrder.setCreateDateTime(Instant.now());
		exchangeOrder.setEoNumber(constructEoNumber(exchangeOrder.getCountryCode()));
		
		if(EoStatus.COMPLETED.equals(exchangeOrder.getStatus())) {
			exchangeOrder.setCompleteDateTime(Instant.now());
		}

		Optional<BaseProduct> isProductExist = Optional.ofNullable(
				productService.getProductByCode(exchangeOrder.getCountryCode(),
						exchangeOrder.getProductCode()));

		BaseProduct product = isProductExist
				.orElseThrow(() -> new IllegalArgumentException(
						"Product [ " + exchangeOrder.getProductCode() + " ] not found."));

		Optional<Vendor> isVendorExist = product.getVendors().stream()
				.filter(i -> i.getCode().equals(exchangeOrder.getVendor().getCode()))
				.findFirst();

		if (!isVendorExist.isPresent()) {
			throw new IllegalArgumentException("Vendor [ " + exchangeOrder.getVendor().getCode()
					+ " ] not found in Product [ " + exchangeOrder.getProductCode()
					+ " ] ");
		}

		String savedEoNumber = exchangeOrderRepo.save(exchangeOrder);

		return exchangeOrderRepo.getExchangeOrder(exchangeOrder.getCountryCode(),
				savedEoNumber);
	}

	private String constructEoNumber(String countryCode) {

		return LocalDate.now().format(formatter)
				.concat(Country.getCountry(countryCode).getId()).concat(String.format(
						"%05d", sequenceNumberService.getSequenceNumber(countryCode)));
	}
}

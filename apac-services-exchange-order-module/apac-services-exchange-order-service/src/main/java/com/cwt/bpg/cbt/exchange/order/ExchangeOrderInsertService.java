package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
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

	@Autowired
	private ExchangeOrderAmountScaler exchangeOrderAmountScaler;

	ExchangeOrder insert(ExchangeOrder exchangeOrder) {
		exchangeOrder.setCreateDateTime(Instant.now());
		exchangeOrder.setEoNumber(constructEoNumber(exchangeOrder.getCountryCode()));

		Optional<BaseProduct> isProductExist = Optional.ofNullable(productService
				.getProductByCode(exchangeOrder.getCountryCode(), exchangeOrder.getProductCode()));

		BaseProduct product = isProductExist.orElseThrow(() -> new IllegalArgumentException(
				"Product [ " + exchangeOrder.getProductCode() + " ] not found."));

		Optional<Vendor> isVendorExist = product.getVendors().stream()
				.filter(i -> i.getCode().equals(exchangeOrder.getVendor().getCode())).findFirst();

		if (!isVendorExist.isPresent()) {
			throw new IllegalArgumentException("Vendor [ " + exchangeOrder.getVendor().getCode()
					+ " ] not found in Product [ " + exchangeOrder.getProductCode() + " ] ");
		}

		if (exchangeOrder.getCreditCard() != null) {
			Set<ConstraintViolation<CreditCard>> ccErrors = Validation.buildDefaultValidatorFactory()
					.getValidator().validate((exchangeOrder.getCreditCard()));
			if (!ccErrors.isEmpty())
				throw new IllegalArgumentException("Credit Card incomplete or invalid");
		}

		if (exchangeOrder.getVendor() != null) {
			Set<ConstraintViolation<Vendor>> vendorErrors = Validation.buildDefaultValidatorFactory()
					.getValidator().validate((exchangeOrder.getVendor()));
			if (!vendorErrors.isEmpty())
				throw new IllegalArgumentException("Vendor incomplete or invalid");
		}

		if (exchangeOrder.getHeader() != null) {
			Set<ConstraintViolation<Header>> headerErrors = Validation.buildDefaultValidatorFactory()
					.getValidator().validate((exchangeOrder.getHeader()));
			if (!headerErrors.isEmpty())
				throw new IllegalArgumentException("Header incomplete or invalid");
		}

		exchangeOrderAmountScaler.scale(exchangeOrder);

		return exchangeOrderRepo.save(exchangeOrder);
	}

	private String constructEoNumber(String countryCode) {

		return LocalDate.now().format(formatter).concat(Country.getCountry(countryCode).getId())
				.concat(String.format("%05d", sequenceNumberService.getSequenceNumber(countryCode)));
	}

}

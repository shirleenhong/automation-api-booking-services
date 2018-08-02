package com.cwt.bpg.cbt.exchange.order;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.india.BaseVendor;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
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

	BaseExchangeOrder insert(BaseExchangeOrder exchangeOrder) {
		exchangeOrder.setCreateDateTime(Instant.now());
		exchangeOrder.setEoNumber(constructEoNumber(exchangeOrder.getCountryCode()));

		Optional<BaseProduct> isProductExist = Optional.ofNullable(
				productService.getProductByCode(exchangeOrder.getCountryCode(),
						exchangeOrder.getProductCode()));

		BaseProduct product = isProductExist
				.orElseThrow(() -> new IllegalArgumentException(
						"Product [ " + exchangeOrder.getProductCode() + " ] not found."));

		Optional<Vendor> isVendorExist = product.getVendors().stream()
				.filter(i -> i.getCode().equals(getVendorCode(exchangeOrder)))
				.findFirst();

		if (!isVendorExist.isPresent()) {
			throw new IllegalArgumentException("Vendor [ " + getVendorCode(exchangeOrder)
					+ " ] not found in Product [ " + exchangeOrder.getProductCode()
					+ " ] ");
		}

		scale(exchangeOrder);
		String savedEoNumber = exchangeOrderRepo.save(exchangeOrder);

		return exchangeOrderRepo.getExchangeOrder(exchangeOrder.getCountryCode(),
				savedEoNumber);
	}

	private String constructEoNumber(String countryCode) {

		return LocalDate.now().format(formatter)
				.concat(Country.getCountry(countryCode).getId()).concat(String.format(
						"%05d", sequenceNumberService.getSequenceNumber(countryCode)));
	}

	private String getVendorCode(BaseExchangeOrder eo) {

		try {
			if (eo.getCountryCode().equals("IN")) {
				return ((BaseVendor) PropertyUtils.getProperty(eo, "vendor")).getCode();
			}
			else {
				return ((Vendor) PropertyUtils.getProperty(eo, "vendor")).getCode();
			}
		}
		catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	private void scale(BaseExchangeOrder exchangeOrder) {
		if (exchangeOrder.getCountryCode().equals("IN")) {
			exchangeOrderAmountScaler.scale((IndiaExchangeOrder) exchangeOrder);
		}
		else {
			exchangeOrderAmountScaler.scale((ExchangeOrder) exchangeOrder);
		}
	}

}

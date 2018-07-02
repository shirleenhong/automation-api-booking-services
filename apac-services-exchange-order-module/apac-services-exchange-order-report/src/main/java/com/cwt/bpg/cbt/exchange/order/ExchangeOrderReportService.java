package com.cwt.bpg.cbt.exchange.order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@Service
public class ExchangeOrderReportService {
	
	@Autowired
	private ExchangeOrderService exchangeOrderService;
	
	@Autowired
	private ProductService productService;


	private static final String pdfName = "exchange-order.jasper";

	
	public byte[] generatePdf(String eoNumber) throws ExchangeOrderException, Exception {

		Optional<ExchangeOrder> isEoExist = Optional.ofNullable(
				getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = isEoExist.orElseThrow(() -> {
			return new ExchangeOrderException(
					"Exchange order number not found: [ " + eoNumber + " ]");
		});
		
		Optional<Vendor> isVendorExists = Optional
				.ofNullable(getVendor(exchangeOrder.getCountryCode(),
						exchangeOrder.getProductCode(), exchangeOrder.getVendorCode()));

		Vendor vendor = isVendorExists.orElseThrow(() -> {
			return new ExchangeOrderException(
					"Vendor not found for exchange order number: [ " + eoNumber + " ]");
		});
		exchangeOrder.setVendor(vendor);
		
		
		Map<String, Object> parameters = formReportHeaders(exchangeOrder);
		final ClassPathResource resource = new ClassPathResource(pdfName);
		byte[] report = null;

		final JasperPrint jasperPrint = JasperFillManager.fillReport(
				resource.getInputStream(), parameters,
				new JRBeanArrayDataSource(new Object[] { exchangeOrder }));

		report = JasperExportManager.exportReportToPdf(jasperPrint);
		
		return report;
	}

	private Map<String, Object> formReportHeaders(ExchangeOrder order) {
		Map<String, Object> parameters = new HashMap<>();
		
		String headerAddress = order.getHeader().getAddress();
		String headerTelephone = order.getHeader().getPhoneNumber();
		String headerFax = order.getHeader().getFaxNumber();

		parameters.put("headerAddress", headerAddress);
		parameters.put("headerTelephone", headerTelephone);
		parameters.put("headerFax", headerFax);
		
		return parameters;

	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}
	
	private Vendor getVendor(String countryCode, String productCode, String vendorCode) {
		return productService.getVendor(countryCode, productCode, vendorCode);
	}

}

	
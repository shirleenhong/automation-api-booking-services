package com.cwt.bpg.cbt.exchange.order;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.calculator.model.Country;
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
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderReportService.class);

	private static final String pdfName = "exchange-order.jasper";
	
	
	
	public byte[] generateReport(String eoNumber) {
		
		Vendor vendor = new Vendor();
		Map<String, Object> parameters = new HashMap<>();
		ExchangeOrder order = getExchangeOrder(eoNumber);
		
		if (order == null) {
			return null;
		}

		String[] countryCodes = {Country.HONG_KONG.getCode(), Country.SINGAPORE.getCode()};
		
		vendor = getVendor(countryCodes, order.getProductCode(), order.getVendorCode());
		
		formReportHeaders(order, parameters);
		
		final ClassPathResource resource = new ClassPathResource(pdfName);
		byte []report = null;
		
		try {
			final JasperPrint jasperPrint = JasperFillManager.fillReport(
					resource.getInputStream(), parameters,
					new JRBeanArrayDataSource(new Object[] { order, vendor }));
			
			report = JasperExportManager.exportReportToPdf(jasperPrint);
		
		}
		catch (Exception e) {
			LOGGER.error("Exception encountered while generating report", e);
		}
		
		return report;
	}

	private void formReportHeaders(ExchangeOrder order, Map<String, Object> parameters) {
		
		parameters.put("headerAddress", order.getHeader().getAddress());
		parameters.put("headerTelephone", order.getHeader().getPhoneNumber());
		parameters.put("headerFax", order.getHeader().getFaxNumber());
		
	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}
	
	private Vendor getVendor(String[] countryCodes, String productCode, String vendorCode) {
		return productService.getVendorByVendorCode(countryCodes, productCode, vendorCode);
	}

}

	
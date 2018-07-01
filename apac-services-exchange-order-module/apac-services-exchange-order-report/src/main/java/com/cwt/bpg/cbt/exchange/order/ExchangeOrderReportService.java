package com.cwt.bpg.cbt.exchange.order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

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

	
	public InputStream generatePdf(String eoNumber) {

		ExchangeOrder exchangeOrder = getExchangeOrder(eoNumber);

		if (exchangeOrder == null) {
			return null;
		}

		Vendor vendor = getVendor(exchangeOrder.getProductCode(),
				exchangeOrder.getVendorCode());

		Map<String, Object> parameters = formReportHeaders(exchangeOrder);

		final ClassPathResource resource = new ClassPathResource(pdfName);
		byte[] report = null;
		InputStream pdfInputStream = null;

		try {
			final JasperPrint jasperPrint = JasperFillManager.fillReport(
					resource.getInputStream(), parameters,
					new JRBeanArrayDataSource(new Object[] { exchangeOrder, vendor }));

			report = JasperExportManager.exportReportToPdf(jasperPrint);
			pdfInputStream = new ByteArrayInputStream(report);
			
		}
		catch (Exception e) {
			LOGGER.error("Exception encountered while generating report", e);
		}
		finally {
			if(pdfInputStream != null) {
				try {
					pdfInputStream.close();
				}
				catch (IOException e) {
					LOGGER.error("Exception encountered while generating report", e);
				}
			}
		}
		
		return pdfInputStream;
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
	
	private Vendor getVendor(String productCode, String vendorCode) {
		return productService.getVendor(productCode, vendorCode);
	}

}

	
package com.cwt.bpg.cbt.exchange.order;

import java.io.IOException;
import java.util.Optional;

import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

import net.sf.jasperreports.engine.JRException;
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

	private static final String TEMPLATE = "exchange-order.jasper";
	
	public byte[] generatePdf(String eoNumber) throws ExchangeOrderException, JRException, IOException {

		Optional<ExchangeOrder> eoExists = Optional.ofNullable(
				getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists.orElseThrow(() -> 
			new ExchangeOrderException(
					"Exchange order number not found: [ " + eoNumber + " ]")
		);
		
		Optional<Vendor> vendorExists = Optional
				.ofNullable(getVendor(exchangeOrder.getCountryCode(),
						exchangeOrder.getProductCode(), exchangeOrder.getVendor().getCode()));

		Vendor vendor = vendorExists.orElseThrow(() -> 
			new ExchangeOrderException(
					"Vendor not found for exchange order number: [ " + eoNumber + " ]")
		);
		
		exchangeOrder.setVendor(vendor);		
		
		final ClassPathResource resource = new ClassPathResource(TEMPLATE);

		final JasperPrint jasperPrint = JasperFillManager.fillReport(
				resource.getInputStream(), null, 
				new JRBeanArrayDataSource(new Object[] { exchangeOrder }));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}
	
	private Vendor getVendor(String countryCode, String productCode, String vendorCode) {
		return productService.getVendor(countryCode, productCode, vendorCode);
	}

	public EmailResponse emailPdf(ExchangeOrder order){
		//TODO IMPLEMENT THIS
		return new EmailResponse();
	}

}

	
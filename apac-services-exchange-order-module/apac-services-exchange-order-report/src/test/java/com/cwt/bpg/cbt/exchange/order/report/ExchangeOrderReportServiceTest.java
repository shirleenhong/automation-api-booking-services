package com.cwt.bpg.cbt.exchange.order.report;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Header;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import com.cwt.bpg.cbt.exchange.order.report.ExchangeOrderReportService;
  
public class ExchangeOrderReportServiceTest {

	@Mock
	private ProductService productService;
	
	@Mock
	private ExchangeOrderService eoService;

	@InjectMocks
	private ExchangeOrderReportService eoReportService;
	
	private String eoNumber;
	private String countryCode;
	private String productCode;
	private String vendorCode;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		eoNumber = "1806100005";
		countryCode = "HK";
		productCode = "Product1";
		vendorCode = "Vendor1";
	}

	@Test
	public void shouldGeneratePdf() throws Exception {
	
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode);
		
		Vendor vendor = createVendor();
		
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		
		byte[] jasperPrint = eoReportService.generatePdf(eoNumber);
		assertNotNull(jasperPrint);
	}

	@Test (expected = ExchangeOrderNoContentException.class)
	public void shouldGeneratePdfNullEO() throws Exception {
			
		Vendor vendor = createVendor();
		
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(null);
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		
		byte[] jasperPrint = eoReportService.generatePdf(eoNumber);
		assertNotNull(jasperPrint);
	}

	@Test (expected = ApiServiceException.class)
	public void shouldGeneratePdfNullVendor() throws Exception {
		
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode);
	
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(null);
		
		byte[] jasperPrint = eoReportService.generatePdf(eoNumber);
		assertNotNull(jasperPrint);
	}

	private ExchangeOrder createExchangeOrder(String countryCode, String productCode,
			String vendorCode) {
		
		ExchangeOrder exchangeOrder = new ExchangeOrder();
		
		exchangeOrder.setCountryCode(countryCode);
		exchangeOrder.setProductCode(productCode);
		
		Vendor vendor = new Vendor();
		vendor.setCode(vendorCode);
				
		exchangeOrder.setVendor(vendor);
		
		Header header = new Header();
		header.setAddress("Address1");
		header.setPhoneNumber("1234");
		header.setFaxNumber("1234");
		
		exchangeOrder.setHeader(header);
		
		return exchangeOrder;
	}
	
	private Vendor createVendor() {
		
		Vendor vendor = new Vendor();
		vendor.setAddress1("Address1");
		
		return vendor;
	}
}

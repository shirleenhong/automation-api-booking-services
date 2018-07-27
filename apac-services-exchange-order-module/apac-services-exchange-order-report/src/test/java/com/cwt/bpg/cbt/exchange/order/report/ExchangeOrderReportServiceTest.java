package com.cwt.bpg.cbt.exchange.order.report;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.ReportHeaderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import static org.hamcrest.CoreMatchers.*;

@Ignore
public class ExchangeOrderReportServiceTest {

	@Mock
	private ProductService productService;
	
	@Mock
	private ExchangeOrderService eoService;

	@Mock
	private ScaleConfig scaleConfig;
	
	@Mock
	private JavaMailSender mailSender;
	
	@Mock
	private EmailContentProcessor emailContentProcessor;
	
	@Mock
	private ReportHeaderService reportHeaderService;

	@InjectMocks
	private ExchangeOrderReportService eoReportService;
	
	private String eoNumber;
	private String countryCode;
	private String productCode;
	private String vendorCode;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		ReflectionTestUtils.setField(eoReportService, "eoMailSender", "testEmail@email.com");
		
        when(scaleConfig.getScale(eq("SG"))).thenReturn(2);
        when(scaleConfig.getScale(eq("HK"))).thenReturn(0);
		
		eoNumber = "1806100005";
		countryCode = "HK";
		productCode = "Product1";
		vendorCode = "Vendor1";
	}

	@Test
	public void shouldGeneratePdf() throws Exception {
	
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode, true, true, true, true);
		
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		byte[] jasperPrint = eoReportService.generatePdf(eoNumber);
		assertNotNull(jasperPrint);
	}

	@Test (expected = ExchangeOrderNoContentException.class)
	public void shouldGeneratePdfNullEO() throws Exception {
			
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(null);
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		byte[] jasperPrint = eoReportService.generatePdf(eoNumber);
		assertNotNull(jasperPrint);
	}
	
	@Test
	public void shouldEmailPdfSuccess_Preferred() throws Exception {
		
		ReflectionTestUtils.setField(eoReportService, "eoMailSender", "testEmail@email.com");
		
		EmailResponse response = new EmailResponse();
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		Session session = Session.getDefaultInstance(new Properties());
		InputStream stubInputStream = 
			     IOUtils.toInputStream("some test data for my input stream", "UTF-8");
		
		MimeMessage message = new MimeMessage(session, stubInputStream);
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode, true, true, false, false);
	
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(mailSender.createMimeMessage()).thenReturn(message);
		when(emailContentProcessor.getEmailSubject(exchangeOrder)).thenReturn(eoNumber);
		when(emailContentProcessor.getEmailBody(exchangeOrder)).thenReturn(eoNumber);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		response = eoReportService.emailPdf(eoNumber);
		assertNotNull(response);
		assertThat(response.isSuccess(), is(true));
		
	}
	
	@Test
	public void shouldEmailPdfSuccess_NonPreferred() throws Exception {
		
		ReflectionTestUtils.setField(eoReportService, "eoMailRecipient", "testEmail@email.com");
		
		EmailResponse response = new EmailResponse();
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		Session session = Session.getDefaultInstance(new Properties());
		InputStream stubInputStream = 
			     IOUtils.toInputStream("some test data for my input stream", "UTF-8");
		
		MimeMessage message = new MimeMessage(session, stubInputStream);
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode, false, true, false, false);
	
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(mailSender.createMimeMessage()).thenReturn(message);
		when(emailContentProcessor.getEmailSubject(exchangeOrder)).thenReturn(eoNumber);
		when(emailContentProcessor.getEmailBody(exchangeOrder)).thenReturn(eoNumber);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		response = eoReportService.emailPdf(eoNumber);
		assertNotNull(response);
		assertThat(response.isSuccess(), is(true));
		
	}
	
	@Test
	public void shouldEmailPdf_emptyRecipient() throws Exception {
		
		EmailResponse response = new EmailResponse();
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		Session session = Session.getDefaultInstance(new Properties());
		InputStream stubInputStream = 
			     IOUtils.toInputStream("some test data for my input stream", "UTF-8");
		
		MimeMessage message = new MimeMessage(session, stubInputStream);
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode, false, false, false, false);

	
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(mailSender.createMimeMessage()).thenReturn(message);
		when(emailContentProcessor.getEmailSubject(exchangeOrder)).thenReturn(eoNumber);
		when(emailContentProcessor.getEmailBody(exchangeOrder)).thenReturn(eoNumber);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		response = eoReportService.emailPdf(eoNumber);
		assertNotNull(response);
		assertThat(response.isSuccess(), is(false));
		
	}
	
	@Test (expected = ApiServiceException.class)
	public void shouldEmailPdfException() throws Exception {
		
		EmailResponse response = new EmailResponse();
		Vendor vendor = createVendor();
		ReportHeader reportHeader = createReportHeader();
		Session session = Session.getDefaultInstance(new Properties());
		InputStream stubInputStream = 
			     IOUtils.toInputStream("some test data for my input stream", "UTF-8");
		
		MimeMessage message = new MimeMessage(session, stubInputStream);
		ExchangeOrder exchangeOrder = createExchangeOrder(countryCode, productCode,
				vendorCode, false, false, false, false);
		
		ReflectionTestUtils.setField(eoReportService, "eoMailRecipient", "testEmail@email.com");
	
		when(productService.getVendor(countryCode, productCode, vendorCode)).thenReturn(vendor);
		when(eoService.getExchangeOrder(eoNumber)).thenReturn(exchangeOrder);
		when(mailSender.createMimeMessage()).thenReturn(message);
		when(emailContentProcessor.getEmailSubject(exchangeOrder)).thenReturn(eoNumber);
		when(reportHeaderService.getHeaderReport(countryCode)).thenReturn(reportHeader);
		
		response = eoReportService.emailPdf(eoNumber);
		assertNotNull(response);
		
	}

	private ExchangeOrder createExchangeOrder(String countryCode, String productCode,
			String vendorCode, boolean isPreferred, boolean hasEmail, boolean hasPhone,
			boolean hasFax) {

		ExchangeOrder exchangeOrder = new ExchangeOrder();

		exchangeOrder.setCountryCode(countryCode);
		exchangeOrder.setProductCode(productCode);
		exchangeOrder.setAdditionalInfoDate(Instant.now());
		exchangeOrder.getServiceInfo().setTax1(new BigDecimal(100));

		List<ContactInfo> contactInfoList = new ArrayList<>();
		if (hasEmail) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setType(ContactInfoType.EMAIL);
			contactInfo.setDetail("sampleEmail@email.com");
			contactInfo.setPreferred(isPreferred);
			contactInfoList.add(contactInfo);
		}
		if (hasPhone) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setType(ContactInfoType.PHONE);
			contactInfo.setDetail("12345");
			contactInfo.setPreferred(isPreferred);
			contactInfoList.add(contactInfo);
		}
		if (hasFax) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setType(ContactInfoType.FAX);
			contactInfo.setDetail("6789");
			contactInfo.setPreferred(isPreferred);
			contactInfoList.add(contactInfo);
		}

		Vendor vendor = new Vendor();
		vendor.setCode(vendorCode);
		vendor.setContactInfo(contactInfoList);
				
		exchangeOrder.setVendor(vendor);
		
		return exchangeOrder;
	}
	
	private Vendor createVendor() {
		
		Vendor vendor = new Vendor();
		vendor.setAddress1("Address1");
		
		return vendor;
	}
	

	private ReportHeader createReportHeader() {
		
		ReportHeader reportHeader = new ReportHeader();
		
		reportHeader.setAddress("Test Address");
		reportHeader.setCompanyName("Test Company Name");
		reportHeader.setFaxNumber("12345");
		reportHeader.setPhoneNumber("67890");
		reportHeader.setCountryCode(countryCode);
		
		return reportHeader;
	}

}

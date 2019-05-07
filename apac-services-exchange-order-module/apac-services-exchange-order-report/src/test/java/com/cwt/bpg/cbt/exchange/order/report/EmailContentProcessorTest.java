package com.cwt.bpg.cbt.exchange.order.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.ReportHeaderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import freemarker.core.ParseException;
import freemarker.template.*;

public class EmailContentProcessorTest {

	@Mock
	private Configuration config;
	
	@Mock
	private Template template;
	
	@Mock
	private ReportHeaderService reportHeaderService;
	
	@InjectMocks
	private EmailContentProcessor processor;
	
	@Before
	public void setUp() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		MockitoAnnotations.initMocks(this);
		when(config.getTemplate(anyString())).thenReturn(template);
	}

	@Test
	public void testGetEmailBody()
			throws IOException, TemplateException, ExchangeOrderNoContentException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setAgentName("agentName");
		eo.setVendor(new Vendor());
		eo.setCountryCode("SG");

		ReportHeader reportHeader = createReportHeader();
		when(reportHeaderService.getHeaderReport(anyString())).thenReturn(reportHeader);
		
		processor.getEmailBody(eo);

		Map<String, Object> input = new HashMap<>();
        
        input.put("vendorSupportEmail", "-");
        input.put("agentName", eo.getAgentName());
        input.put("headerPhoneNumber", "-");
        input.put("headerFaxNumber", "-");        		
        
		verify(template, times(1)).process(eq(input), anyObject());
	}
	
	@Test
	public void testGetEmailSubject() {
		
		ExchangeOrder eo = new ExchangeOrder();
		eo.setPassengerName("Passenger Name");
		eo.setRecordLocator("ABC123");
		eo.setEoNumber("1807200009");
		
		String result = processor.getEmailSubject(eo);
		assertEquals("CWT Exchange Order 1807200009: ABC123 - (Passenger Name)", result);
	}
	
	private ReportHeader createReportHeader() {

		ReportHeader reportHeader = new ReportHeader();

		reportHeader.setAddress("Test Address");
		reportHeader.setCompanyName("Test Company Name");
		reportHeader.setFaxNumber("-");
		reportHeader.setPhoneNumber("-");
		reportHeader.setCountryCode("HK");

		return reportHeader;
	}

}

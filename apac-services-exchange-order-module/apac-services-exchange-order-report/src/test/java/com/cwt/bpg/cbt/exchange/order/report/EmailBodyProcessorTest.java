package com.cwt.bpg.cbt.exchange.order.report;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.cwt.bpg.cbt.exchange.order.model.*;

import freemarker.core.ParseException;
import freemarker.template.*;

public class EmailBodyProcessorTest {

	@Mock
	private Configuration config;
	
	@Mock
	private Template template;
	
	@InjectMocks
	private EmailContentProcessor processor;
	
	@Before
	public void setUp() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		MockitoAnnotations.initMocks(this);
		when(config.getTemplate(anyString())).thenReturn(template);
	}

	@Test
	public void testGetEmailBody() throws IOException, TemplateException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setAgentName("agentName");
		eo.setVendor(new Vendor());
		eo.setHeader(new Header());
		processor.getEmailBody(eo);
		
		Map<String, Object> input = new HashMap<>();
        
        input.put("vendorEmail", "-");
        input.put("agentName", eo.getAgentName());
        input.put("headerPhoneNumber", "-");
        input.put("headerFaxNumber", "-");        		
        
		verify(template, times(1)).process(eq(input), anyObject());
	}

}

package com.cwt.bpg.cbt.exchange.order.report;

public class EmailContentProcessorTest {

	/*@Mock
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
	
	@Test
	public void testGetEmailSubject() {
		
		ExchangeOrder eo = new ExchangeOrder();
		eo.setPassengerName("Passenger Name");
		eo.setRecordLocator("ABC123");
		eo.setEoNumber("1807200009");
		
		String result = processor.getEmailSubject(eo);
		assertEquals("CWT Exchange Order 1807200009: ABC123 - (Passenger Name)", result);
	}*/

}

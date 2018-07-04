package com.cwt.bpg.cbt.exchange.order.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

import freemarker.template.*;

public class EmailBodyProcessor {

	@Qualifier("freemarkerConfig")
	@Autowired
	private Configuration templateConfig;
	
	private static final String DASH = "-";	
	
	public String getEmailBody(ExchangeOrder eo) throws IOException, TemplateException
    {
        StringWriter writer = new StringWriter();
        Template template = templateConfig.getTemplate("eo-email.ftl");
        Map<String, Object> input = new HashMap<>();
        
        input.put("vendorEmail", getValue(eo.getVendor().getEmail()));
        input.put("agentName", eo.getAgentName());
        input.put("headerPhoneNumber", 
        		getValue(eo.getHeader().getPhoneNumber()));
        input.put("headerFaxNumber", 
        		getValue(eo.getHeader().getFaxNumber()));        		

        template.process(input, writer);

        return writer.toString();
    }

	private String getValue(String value) {
		return value != null 
        		? value 
        		: DASH;
	}
}

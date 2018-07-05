package com.cwt.bpg.cbt.exchange.order.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

import freemarker.template.*;

@Service
public class EmailContentProcessor {

	private static final String SUBJECT_PREFIX = "CWT Exchange Order";

	private static final String EO_EMAIL_BODY_FTL = "eo-email-body.ftl";

	@Qualifier("freemarkerConfig")
	@Autowired
	private Configuration templateConfig;
	
	private static final String DASH = "-";	
	
	public String getEmailBody(ExchangeOrder eo) throws IOException, TemplateException
    {
        StringWriter writer = new StringWriter();
        Template template = templateConfig.getTemplate(EO_EMAIL_BODY_FTL);
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
	
	public String getEmailSubject(ExchangeOrder eo) {

		StringBuilder sb = new StringBuilder(SUBJECT_PREFIX);
		sb.append(" ");
		sb.append(eo.getEoNumber());
		sb.append(": ");
		sb.append(eo.getRecordLocator());
		sb.append(" - (");
		sb.append(eo.getPassengerName());
		sb.append(")");

		return sb.toString();
	}

	private String getValue(String value) {
		return value != null 
        		? value 
        		: DASH;
	}
}

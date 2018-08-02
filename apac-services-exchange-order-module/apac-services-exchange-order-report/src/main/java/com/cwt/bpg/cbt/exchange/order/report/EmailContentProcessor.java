package com.cwt.bpg.cbt.exchange.order.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.exchange.order.ReportHeaderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class EmailContentProcessor {

	private static final String SUBJECT_PREFIX = "CWT Exchange Order";

	private static final String EO_EMAIL_BODY_FTL = "eo-email-body.ftl";
	
	private static final String DASH = "-";	
	
	@Qualifier("freemarkerConfig")
	@Autowired
	private Configuration templateConfig;
	
	@Autowired
	private ReportHeaderService reportHeaderService;
	
	
	public String getEmailBody(ExchangeOrder eo)
			throws IOException, TemplateException, ExchangeOrderNoContentException {
		
		StringWriter writer = new StringWriter();
		Template template = templateConfig.getTemplate(EO_EMAIL_BODY_FTL);
		Map<String, Object> input = new HashMap<>();

		ReportHeader reportHeader = getReportHeaderInfo(eo.getCountryCode());

		input.put("vendorSupportEmail", getValue(eo.getVendor().getSupportEmail()));
		input.put("agentName", eo.getAgentName());
		input.put("headerPhoneNumber", getValue(reportHeader.getPhoneNumber()));
		input.put("headerFaxNumber", getValue(reportHeader.getFaxNumber()));

		template.process(input, writer);

		return writer.toString();
	}
	
	private ReportHeader getReportHeaderInfo(String countryCode)
			throws ExchangeOrderNoContentException {
		Optional<ReportHeader> reportHeaderExists = Optional
				.ofNullable(getReportHeader(countryCode));

		ReportHeader reportHeader = reportHeaderExists
				.orElseThrow(() -> new ExchangeOrderNoContentException(
						"Report header not found for country: [ " + countryCode
								+ " ]"));
		return reportHeader;
	}
	
	private ReportHeader getReportHeader(String countryCode) {
		return reportHeaderService.getHeaderReport(countryCode);
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

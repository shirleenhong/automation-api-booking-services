package com.cwt.bpg.cbt.exchange.order.report;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cwt.bpg.cbt.exchange.order.model.ContactInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@Service
public class ExchangeOrderReportService {

	private static final String EMAIL_ERROR_MESSAGE = "Email cannot be empty.";

	private static final String ERROR_MESSAGE = "Error encountered while sending email.";

	private static final String DATE_PATTERN = "dd-MMM-yyyy";

	private static final String PHONE = "PHONE";

	private static final String FAX = "FAX";

	private static final String EMAIL = "EMAIL";

	@Autowired
	private ExchangeOrderService exchangeOrderService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private EmailContentProcessor emailContentProcessor;

	@Value("${exchange.order.mail.sender}")
	private String eoMailSender;

	@Value("${exchange.order.email.test.recipient}")
	private String eoMailRecipient;

	private static final String TEMPLATE = "jasper/exchange-order.jasper";
	private static final String IMAGE_PATH = "jasper/cwt-logo.png";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExchangeOrderReportService.class);

	public byte[] generatePdf(String eoNumber)
			throws ExchangeOrderNoContentException, ApiServiceException {

		Optional<ExchangeOrder> eoExists = Optional
				.ofNullable(getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists
				.orElseThrow(() -> new ExchangeOrderNoContentException(
						"Exchange order number not found: [ " + eoNumber + " ]"));

		final ClassPathResource resource = new ClassPathResource(TEMPLATE);

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					resource.getInputStream(), prepareParameters(exchangeOrder),
					new JRBeanArrayDataSource(new Object[] { exchangeOrder }));
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException | IOException e) {
			throw new ApiServiceException(e.getMessage());
		}
	}

	private Map<String, Object> prepareParameters(final ExchangeOrder exchangeOrder) throws IOException {
		final ClassPathResource resourceLogo = new ClassPathResource(IMAGE_PATH);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("CWT_LOGO", ImageIO.read(resourceLogo.getInputStream()));
		parameters.put("DATE", getDate(exchangeOrder));
		parameters.put("ADDITIONAL_INFO_DATE", formatDate(exchangeOrder.getAdditionalInfoDate()));
		parameters.put("NETT_COST", formatAmount(exchangeOrder.getCountryCode(), exchangeOrder.getNettCost()));
		parameters.put("TAX2", formatAmount(exchangeOrder.getCountryCode(), exchangeOrder.getTax2()));
		parameters.put("TOTAL", formatAmount(exchangeOrder.getCountryCode(), exchangeOrder.getTotal()));

		BigDecimal gstAmount = exchangeOrder.getGstAmount();
        boolean displayGst = gstAmount != null && gstAmount.doubleValue() > 0d;
        parameters.put("GST_AMOUNT_TAX1_LABEL", displayGst ? "GST" : "Tax");
        parameters.put("GST_AMOUNT_TAX1", formatAmount(exchangeOrder.getCountryCode(),
                displayGst ? gstAmount : exchangeOrder.getTax1()));
        putContactInfoParameters(exchangeOrder.getVendor().getContactInfo(), parameters);

		return parameters;
	}

	private void putContactInfoParameters(List<ContactInfo> contactInfoList, Map<String, Object> parameters) {

		boolean isCurrPhonePref = false;
		boolean isCurrFaxPref = false;
		boolean isCurrEmailPref = false;

		for (ContactInfo contactInfo : contactInfoList) {

			if (contactInfo.getType() != null
					&& contactInfo.getType() == ContactInfoType.PHONE
					&& (!parameters.containsKey(PHONE)
							|| (contactInfo.isPreferred() && !isCurrPhonePref))) {
				parameters.put(PHONE, contactInfo.getDetail());
				isCurrPhonePref = contactInfo.isPreferred() && !isCurrPhonePref;
			}
			else if (contactInfo.getType() != null
					&& contactInfo.getType() == ContactInfoType.Fax
					&& (!parameters.containsKey(FAX)
							|| (contactInfo.isPreferred() && !isCurrFaxPref))) {
				parameters.put(FAX, contactInfo.getDetail());
				isCurrFaxPref = contactInfo.isPreferred() && !isCurrFaxPref;
			}
			else if (contactInfo.getType() != null
					&& contactInfo.getType() == ContactInfoType.EMAIL
					&& (!parameters.containsKey(EMAIL)
							|| (contactInfo.isPreferred() && !isCurrEmailPref))) {
				parameters.put(EMAIL, contactInfo.getDetail());
				isCurrEmailPref = contactInfo.isPreferred() && !isCurrEmailPref;
			}
		}
	}

	private String getDate(ExchangeOrder exchangeOrder) {
		Instant updateDateTime = exchangeOrder.getUpdateDateTime();
		return formatDate(updateDateTime != null ? updateDateTime
				: exchangeOrder.getCreateDateTime());
	}

	private String formatDate(Instant instant) {
		return instant == null ? "" : DateTimeFormatter.ofPattern(DATE_PATTERN)
				.format(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
	}

	private String formatAmount(String countryCode, BigDecimal amount) {
		if (amount != null) {
			int scale = scaleConfig.getScale(countryCode);
			DecimalFormat formatter = new DecimalFormat("$#,##0.00");
			formatter.setMinimumFractionDigits(scale);
			formatter.setMaximumFractionDigits(scale);
			formatter.setRoundingMode(RoundingMode.DOWN);

			return formatter.format(amount);
		}
		else {
			return "";
		}

	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}

	public EmailResponse emailPdf(String eoNumber) throws ApiServiceException {

		EmailResponse response = new EmailResponse();

		try {

			ExchangeOrder exchangeOrder = getExchangeOrder(eoNumber);
			List<ContactInfo> contactInfoList  = exchangeOrder.getVendor().getContactInfo();
			String email = setEmailRecipient(contactInfoList);

			String emailRecipient = getEmail(email);
			if (StringUtils.isEmpty(emailRecipient)) {
				LOGGER.error(EMAIL_ERROR_MESSAGE);
				response.setMessage(EMAIL_ERROR_MESSAGE);
				response.setSuccess(false);

				return response;
			}

			byte[] pdf = generatePdf(eoNumber);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, (pdf != null),
					StandardCharsets.UTF_8.name());

			helper.setTo(InternetAddress.parse(emailRecipient));
			helper.setFrom(eoMailSender);
			helper.setSubject(emailContentProcessor.getEmailSubject(exchangeOrder));
			helper.setText(emailContentProcessor.getEmailBody(exchangeOrder), true);
			helper.addAttachment(eoNumber + ".pdf", new ByteArrayResource(pdf));
			mailSender.send(message);

			response.setSuccess(true);
		}
		catch (Exception e) {
			LOGGER.error(ERROR_MESSAGE, e);
			throw new ApiServiceException(ERROR_MESSAGE);
		}

        return response;
	}

	private String setEmailRecipient(List<ContactInfo> contactInfoList) {
		StringBuilder sbEmail = new StringBuilder();
		StringBuilder sbEmailNotPreferred = new StringBuilder();
		List<String> emailListNotPreferred = new ArrayList<>();
		int count = 0;

		for (ContactInfo ci : contactInfoList) {
			if (ci.getType() != null && ci.getType().equalsIgnoreCase("Email")) {
				if (ci.isPreferred()) {
					sbEmail.append(ci.getDetail());
					sbEmail.append(",");
				}
				else {
					emailListNotPreferred.add(ci.getDetail());

					sbEmailNotPreferred.append(ci.getDetail());
					sbEmailNotPreferred.append(",");
				}
				count++;
			}
		}

		String email = sbEmail.toString();
		if (count == emailListNotPreferred.size()) {
			email = sbEmailNotPreferred.toString();
		}
		
		return email;
	}

	private String getEmail(String email) {
		return !StringUtils.isEmpty(eoMailRecipient)
				? eoMailRecipient
				: email;
	}

}

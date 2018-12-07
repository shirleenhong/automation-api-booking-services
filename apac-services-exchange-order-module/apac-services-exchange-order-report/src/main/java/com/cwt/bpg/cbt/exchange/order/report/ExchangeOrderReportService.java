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
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.ReportHeaderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;

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
	private JavaMailSender mailSender;

	@Autowired
	private ScaleConfig scaleConfig;

	@Autowired
	private EmailContentProcessor emailContentProcessor;
	
	@Autowired
	private ReportHeaderService reportHeaderService;

	@Value("${exchange.order.mail.sender}")
	private String eoMailSender;

	@Value("${exchange.order.email.test.recipient}")
	private String eoMailRecipient;

	private static final String TEMPLATE = "jasper/exchange-order.jasper";
	
	private static final String IMAGE_PATH = "jasper/cwt-logo.png";
	
	private static final String EMAIL_ERROR_MESSAGE = "Email cannot be empty.";
	
	private static final String EMAIL_ERROR_MESSAGE_INDIA = "Email not supported in India.";

	private static final String ERROR_MESSAGE = "Error encountered while sending email.";

	private static final String DATE_PATTERN = "dd-MMM-yyyy";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExchangeOrderReportService.class);

	public byte[] generatePdf(String eoNumber)
			throws ExchangeOrderNoContentException, ApiServiceException {

		Optional<ExchangeOrder> eoExists = Optional
				.ofNullable(getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists
				.orElseThrow(() -> new ExchangeOrderNoContentException(
						"Exchange order number not found: [ " + eoNumber + " ]"));
		checkCountryCode(eoNumber, exchangeOrder.getCountryCode());
		
		ReportHeader reportHeader = getReportHeaderInfo(exchangeOrder.getCountryCode());

		final ClassPathResource resource = new ClassPathResource(TEMPLATE);

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					resource.getInputStream(), prepareParameters(exchangeOrder, reportHeader),
					new JRBeanArrayDataSource(new Object[] { exchangeOrder }));
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException | IOException e) {
			throw new ApiServiceException(e.getMessage());
		}
	}

	private void checkCountryCode(String eoNumber, String countryCode)
			throws ExchangeOrderNoContentException {
		if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
			throw new ExchangeOrderNoContentException(
					"Generate pdf for India Exchange order number: [ " + eoNumber
							+ " ] not supported.");
		}
	}

	private EmailResponse checkEmailCountryCode(String countryCode) {

		EmailResponse response = new EmailResponse();
		response.setSuccess(true);
		if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
			LOGGER.error(EMAIL_ERROR_MESSAGE_INDIA);
			response.setMessage(EMAIL_ERROR_MESSAGE_INDIA);
			response.setSuccess(false);
		}
		return response;
	}

	private Map<String, Object> prepareParameters(final ExchangeOrder exchangeOrder,
			final ReportHeader reportHeader) throws IOException {
		final ClassPathResource resourceLogo = new ClassPathResource(IMAGE_PATH);
		Map<String, Object> parameters = new HashMap<>();
		
		boolean displayServiceInfo = false;

		BigDecimal gstAmount = null;
		BigDecimal tax1 = null;
		BigDecimal vendorHandling = null;
		String taxCode1 = null;
		String taxCode2 = null;
		String productCode = exchangeOrder.getProductCode();
		String countryCode = exchangeOrder.getCountryCode();
		
		if (exchangeOrder.getServiceInfo() != null) {
			gstAmount = exchangeOrder.getServiceInfo().getGst();
			tax1 = exchangeOrder.getServiceInfo().getTax1();
			taxCode1 = exchangeOrder.getServiceInfo().getTaxCode1();
			taxCode2 = exchangeOrder.getServiceInfo().getTaxCode2();
			vendorHandling = exchangeOrder.getServiceInfo().getVendorHandling();
			displayServiceInfo = true;
		}

		parameters.put("CWT_LOGO", ImageIO.read(resourceLogo.getInputStream()));
		parameters.put("DATE", getDate(exchangeOrder));
		parameters.put("TOTAL",
				formatAmount(countryCode, exchangeOrder.getTotal()));

		boolean displayGst = gstAmount != null && gstAmount.doubleValue() > 0d;
		parameters.put("GST_AMOUNT_TAX1_LABEL", displayGst ? "GST" : "Tax");
		parameters.put("GST_AMOUNT_TAX1", formatAmount(countryCode,
				displayGst ? gstAmount : tax1));

		List<ContactInfo> contactInfo = exchangeOrder.getVendor().getContactInfo();
		List<ContactInfo> contactInfoList = checkNullContactInfoList(contactInfo);
		putContactInfoParameters(contactInfoList, parameters);

		parameters.put("HEADER_ADDRESS", reportHeader.getAddress());
		parameters.put("HEADER_FAX", reportHeader.getFaxNumber());
		parameters.put("HEADER_PHONE", reportHeader.getPhoneNumber());
		parameters.put("HEADER_COMPANY_NAME", reportHeader.getCompanyName());
		
		parameters.put("TAX2",
				displayServiceInfo ? formatAmount(countryCode,
						exchangeOrder.getServiceInfo().getTax2()) : null);
		parameters
				.put("NETT_COST",
						displayServiceInfo
								? formatAmount(countryCode,
										exchangeOrder.getServiceInfo().getNettCost())
								: null);
		
		
		if (Country.HONG_KONG.getCode().equalsIgnoreCase(countryCode)) {
			formatAdditionalHkContent(parameters, vendorHandling, productCode,
					countryCode);
		}
		else {
			formatAdditionalSgContent(parameters, taxCode1, taxCode2, productCode,
					displayGst);
		}
		
		return parameters;
	}

	private void formatAdditionalSgContent(Map<String, Object> parameters,
			String taxCode1, String taxCode2, String productCode, boolean displayGst) {
		
		if (ProductEnum.CONSOLIDATOR_TICKET.getCode().equals(productCode)
				|| ProductEnum.CLIENT_CARD.getCode().equals(productCode)) {
			parameters.put("TAX_CODE_1", displayGst ? null : taxCode1);
			parameters.put("TAX_CODE_2", taxCode2);
		}
		if (ProductEnum.TRAIN.getCode().equals(productCode)) {
			parameters.put("REMARKS_BAND", false);
		}
	}

	private void formatAdditionalHkContent(Map<String, Object> parameters,
			BigDecimal vendorHandling, String productCode, String countryCode) {

		if (ProductEnum.TRANSACTION_FEE.getCode().equals(productCode)) {
			parameters.put("REMARKS_BAND", false);
		}
		if (ProductEnum.VISA_PROCESSING.getCode().equals(productCode)) {
			parameters.put("VENDOR_HANDLING_BAND", true);
			parameters.put("VENDOR_HANDLING_FEES",
					formatAmount(countryCode, vendorHandling));
		}
	}
	
	
	private ReportHeader getReportHeaderInfo(String countryCode)
			throws ExchangeOrderNoContentException {
		Optional<ReportHeader> reportHeaderExists = Optional
				.ofNullable(getReportHeader(countryCode));

		return reportHeaderExists
				.orElseThrow(() -> new ExchangeOrderNoContentException(
						"Report header not found for country: [ " + countryCode
								+ " ]"));
	}

	private List<ContactInfo> checkNullContactInfoList(
			List<ContactInfo> contactInfoList) {
		
		Optional<List<ContactInfo>> contactExists = Optional
				.ofNullable(contactInfoList);
        
		return contactExists
				.orElse(new ArrayList<>());
	}

	private void putContactInfoParameters(List<ContactInfo> contactInfoList, Map<String, Object> parameters) {

		EnumMap<ContactInfoType, Boolean> prefFoundMap = new EnumMap<>(ContactInfoType.class);
		prefFoundMap.put(ContactInfoType.PHONE, Boolean.FALSE);
		prefFoundMap.put(ContactInfoType.FAX, Boolean.FALSE);
		prefFoundMap.put(ContactInfoType.EMAIL, Boolean.FALSE);

		for (ContactInfo contactInfo : contactInfoList) {
			if (contactInfo.getType() != null) {
                prefFoundMap.keySet().forEach(contactInfoType -> {
                    if (contactInfo.getType() == contactInfoType
                            && (!parameters.containsKey(contactInfoType.toString())
                            || (contactInfo.isPreferred() && !prefFoundMap.get(contactInfoType)))) {
                        parameters.put(contactInfoType.toString(), contactInfo.getDetail());
                        prefFoundMap.put(contactInfoType, contactInfo.isPreferred() && !prefFoundMap.get(contactInfoType));
                    }
                });
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

		if (amount == null) {
			return "";
		}
		
		int scale = scaleConfig.getScale(countryCode);
		DecimalFormat formatter = new DecimalFormat("$#,##0.00");
		formatter.setMinimumFractionDigits(scale);
		formatter.setMaximumFractionDigits(scale);
		formatter.setRoundingMode(RoundingMode.DOWN);
		
		return formatter.format(amount);
	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.get(eoNumber);
	}
	
	private ReportHeader getReportHeader(String countryCode) {
		return reportHeaderService.getHeaderReport(countryCode.toUpperCase());
	}

	public EmailResponse emailPdf(String eoNumber) throws ApiServiceException {

		EmailResponse response = new EmailResponse();

		try {

			ExchangeOrder exchangeOrder = getExchangeOrder(eoNumber);
			EmailResponse indiaResponse = checkEmailCountryCode(exchangeOrder.getCountryCode());
			if(!indiaResponse.isSuccess()) {
				return indiaResponse;
			}
			List<ContactInfo> contactInfo = exchangeOrder.getVendor().getContactInfo();
			List<ContactInfo> contactInfoList = checkNullContactInfoList(contactInfo);
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
			if (ci.getType() == ContactInfoType.EMAIL) {
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

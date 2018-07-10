package com.cwt.bpg.cbt.exchange.order.report;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.*;
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

import com.cwt.bpg.cbt.exceptions.ApiServiceException;
import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@Service
public class ExchangeOrderReportService {

	private static final String EMAIL_ERROR_MESSAGE = "Email cannot be empty.";

    private static final String ERROR_MESSAGE = "Error encountered while sending email.";

    private static final String DATE_PATTERN = "dd-MMM-yyyy";

    @Autowired
	private ExchangeOrderService exchangeOrderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
    private EmailContentProcessor emailContentProcessor;

	@Value("${exchange.order.mail.sender}")
	private String eoMailSender;

    @Value("${exchange.order.email.test.recipient}")
	private String eoMailRecipient;

	private static final String TEMPLATE = "jasper/exchange-order.jasper";
	private static final String IMAGE_PATH = "jasper/cwt-logo.png";

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderReportService.class);
	
	public byte[] generatePdf(String eoNumber)
			throws ExchangeOrderNoContentException, ApiServiceException {
    
		Optional<ExchangeOrder> eoExists = Optional.ofNullable(getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists.orElseThrow(() -> new ExchangeOrderNoContentException(
				"Exchange order number not found: [ " + eoNumber + " ]"));

		Optional<Vendor> vendorExists = Optional.ofNullable(getVendor(exchangeOrder.getCountryCode(),
				exchangeOrder.getProductCode(),
				exchangeOrder.getVendor().getCode()));

		Vendor vendor = vendorExists.orElseThrow(() -> new ApiServiceException(
				"Vendor not found for exchange order number: [ " + eoNumber + " ]"));

		vendor.setContactPerson(exchangeOrder.getVendor().getContactPerson());
		vendor.setSupportEmail(exchangeOrder.getVendor().getSupportEmail());

		exchangeOrder.setVendor(vendor);
		
		Map<String, Object> parameters = new HashMap<>();
		
		final ClassPathResource resource = new ClassPathResource(TEMPLATE);
		final ClassPathResource resourceLogo = new ClassPathResource(IMAGE_PATH);

		try {
            BufferedImage image = ImageIO.read(resourceLogo.getInputStream());
            
			parameters.put("cwtLogo", image);
			parameters.put("date", getDate(exchangeOrder));
			parameters.put("additionalInfoDate", formatDate(exchangeOrder.getAdditionalInfoDate()));
			parameters.put("nettCost", formatAmount(exchangeOrder.getNettCost()));
			parameters.put("tax2", formatAmount(exchangeOrder.getTax2()));
			parameters.put("total", formatAmount(exchangeOrder.getTotal()));
			parameters.put("gstAmountTax1", formatGstAmountTax1(exchangeOrder.getGstAmount(), exchangeOrder.getTax1()));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(resource.getInputStream(),
					parameters,
					new JRBeanArrayDataSource(new Object[] { exchangeOrder }));
			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException | IOException e) {
			throw new ApiServiceException(e.getMessage());
		}
	}

	private String getDate(ExchangeOrder exchangeOrder) {
		Instant updateDateTime = exchangeOrder.getUpdateDateTime();
		return formatDate(updateDateTime != null ? updateDateTime : exchangeOrder.getCreateDateTime());
	}

	private String formatDate(Instant instant) {
		return instant == null ? "" : DateTimeFormatter.ofPattern(DATE_PATTERN)
				.format(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
	}
	
	private String formatAmount(BigDecimal amount) {

		if (amount != null) {
			if (amount.toString().contains(".00")) {
				return "$#,##0.00";
			}
			return amount.doubleValue() % 1 == 0 ? "$#,##0" : "$#,##0.00";
		}
		return "";
	}

	private String formatGstAmountTax1(BigDecimal gstAmount, BigDecimal tax1) {
		return gstAmount != null ? formatAmount(gstAmount) : formatAmount(tax1);
	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}

	private Vendor getVendor(String countryCode, String productCode, String vendorCode) {
		return productService.getVendor(countryCode, productCode, vendorCode);
	}

	public EmailResponse emailPdf(String eoNumber) throws ApiServiceException {

		EmailResponse response = new EmailResponse();

		try {

			ExchangeOrder exchangeOrder = getExchangeOrder(eoNumber);

			String emailRecipient = getEmail(exchangeOrder.getVendor().getEmail());
			if(StringUtils.isEmpty(emailRecipient)) {
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

	private String getEmail(String email) {
		return !StringUtils.isEmpty(eoMailRecipient)
				? eoMailRecipient
				: email;
	}

}

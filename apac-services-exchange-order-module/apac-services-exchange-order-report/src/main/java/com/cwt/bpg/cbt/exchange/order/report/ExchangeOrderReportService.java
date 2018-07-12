package com.cwt.bpg.cbt.exchange.order.report;

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

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderReportService.class);
	
	public byte[] generatePdf(String eoNumber)
			throws ExchangeOrderNoContentException, ApiServiceException {
    
		Optional<ExchangeOrder> eoExists = Optional.ofNullable(getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists.orElseThrow(() -> new ExchangeOrderNoContentException(
				"Exchange order number not found: [ " + eoNumber + " ]"));

		final ClassPathResource resource = new ClassPathResource(TEMPLATE);

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(resource.getInputStream(),
					prepareParameters(exchangeOrder),
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

		parameters.put("cwtLogo", ImageIO.read(resourceLogo.getInputStream()));
		parameters.put("date", getDate(exchangeOrder));
		parameters.put("additionalInfoDate", formatDate(exchangeOrder.getAdditionalInfoDate()));
		parameters.put("nettCost", formatAmount(exchangeOrder.getNettCost()));
		parameters.put("tax2", formatAmount(exchangeOrder.getTax2()));
		parameters.put("total", formatAmount(exchangeOrder.getTotal()));
        Optional<BigDecimal> tax1 = Optional.ofNullable(exchangeOrder.getGstAmount());
        parameters.put("gstAmountTax1", formatAmount(exchangeOrder.getCountryCode(), tax1.orElse(exchangeOrder.getTax1())));

		return parameters;
	}

	private String getDate(ExchangeOrder exchangeOrder) {
		Instant updateDateTime = exchangeOrder.getUpdateDateTime();
		return formatDate(updateDateTime != null ? updateDateTime : exchangeOrder.getCreateDateTime());
	}

	private String formatDate(Instant instant) {
		return instant == null ? "" : DateTimeFormatter.ofPattern(DATE_PATTERN)
				.format(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
	}
	
	private String formatAmount(String countryCode, BigDecimal amount) {

	    int scale = scaleConfig.getScale(countryCode);
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        formatter.setMinimumFractionDigits(scale);
        formatter.setMaximumFractionDigits(scale);
        formatter.setRoundingMode(RoundingMode.DOWN);

        return formatter.format(amount);

	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
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

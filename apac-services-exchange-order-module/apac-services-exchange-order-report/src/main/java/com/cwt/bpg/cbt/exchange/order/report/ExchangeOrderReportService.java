package com.cwt.bpg.cbt.exchange.order.report;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.ExchangeOrderService;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.model.EmailResponse;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

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
	private ProductService productService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	EmailContentProcessor emailContentProcessor;

	@Value("${exchange.order.mail.sender}")
	private String eoMailSender;

	private static final String TEMPLATE = "jasper/exchange-order.jasper";

	public byte[] generatePdf(String eoNumber) throws ExchangeOrderException, JRException, IOException {

		Optional<ExchangeOrder> eoExists = Optional.ofNullable(getExchangeOrder(eoNumber));

		ExchangeOrder exchangeOrder = eoExists.orElseThrow(
				() -> new ExchangeOrderException("Exchange order number not found: [ " + eoNumber + " ]"));

		Optional<Vendor> vendorExists = Optional.ofNullable(getVendor(exchangeOrder.getCountryCode(),
				exchangeOrder.getProductCode(),
				exchangeOrder.getVendor().getCode()));

		Vendor vendor = vendorExists.orElseThrow(() -> new ExchangeOrderException(
				"Vendor not found for exchange order number: [ " + eoNumber + " ]"));

		vendor.setContactPerson(exchangeOrder.getVendor().getContactPerson());
		vendor.setSupportEmail(exchangeOrder.getVendor().getSupportEmail());

		exchangeOrder.setVendor(vendor);

		final ClassPathResource resource = new ClassPathResource(TEMPLATE);

		final JasperPrint jasperPrint = JasperFillManager.fillReport(resource.getInputStream(),
				null,
				new JRBeanArrayDataSource(new Object[] { exchangeOrder }));

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	private ExchangeOrder getExchangeOrder(String eoNumber) {
		return exchangeOrderService.getExchangeOrder(eoNumber);
	}

	private Vendor getVendor(String countryCode, String productCode, String vendorCode) {
		return productService.getVendor(countryCode, productCode, vendorCode);
	}

	public EmailResponse emailPdf(String eoNumber) throws Exception {
		String email = "mosesbrian.calma@carlsonwagonlit.com";// should be vendor email

		byte[] pdf = generatePdf(eoNumber);
		ExchangeOrder exchangeOrder = getExchangeOrder(eoNumber);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, (pdf != null),
				StandardCharsets.UTF_8.name());

		helper.setTo(email);
		helper.setFrom(eoMailSender);
		helper.setSubject(emailContentProcessor.getEmailSubject(exchangeOrder));
		helper.setText(emailContentProcessor.getEmailBody(exchangeOrder), true);
		helper.addAttachment(eoNumber + ".pdf", new ByteArrayResource(Objects.requireNonNull(pdf)));
		mailSender.send(message);

		return new EmailResponse();
	}

}

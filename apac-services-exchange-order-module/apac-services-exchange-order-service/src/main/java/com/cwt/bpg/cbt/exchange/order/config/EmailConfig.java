package com.cwt.bpg.cbt.exchange.order.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration("com.cwt.bpg.cbt.email.config")
public class EmailConfig {
	
	@Value("${exchange.order.mail.host}")
	private String host;
	
	@Value("${exchange.order.mail.port}")
	private int port;
	
	@Value("${exchange.order.mail.transport.protocol}")
	private String transportProtocol;
	
	@Value("${exchange.order.mail.smtp.auth}")
	private String smtpAuth;
	
	@Value("${exchange.order.mail.smtp.starttls.enable}")
	private String enableStarttls;
	
	@Value("${exchange.order.mail.debug}")
	private String mailDebug;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(host);
	    mailSender.setPort(port);
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", transportProtocol);
	    props.put("mail.smtp.auth", smtpAuth);
	    props.put("mail.smtp.starttls.enable", enableStarttls);
	    props.put("mail.debug",mailDebug);
	     
	    return mailSender;
	}
	
}

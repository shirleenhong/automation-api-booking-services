package com.cwt.bpg.cbt.mongodb.config.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;

@Component
public class MongoSSLCertificateUtility {

	private static TrustManager[] passThroughTrustManager() {
		return new TrustManager[] { new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// Do nothing

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// Do nothing
			} 
		} };

	}
	
	public static SocketFactory mongoDbSocketFactory() throws NoSuchAlgorithmException, KeyManagementException{
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, passThroughTrustManager(), new java.security.SecureRandom());
		return sc.getSocketFactory();
	}
	

}
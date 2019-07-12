/*
 * (c) Copyright 2019 sothawo
 */
package com.sothawo.springdataelastictest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.http.HttpHeaders;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@Profile("rest")
public class RestClientConfig extends AbstractElasticsearchConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(RestClientConfig.class);

	private static final String CERT_PASSWORD = "topsecret";
	public static final String USER_NAME = "elastic";
	public static final String USER_PASS = "citsale";

	@Bean
	public RestClient restClient(RestHighLevelClient client) {
		return client.getLowLevelClient();
	}

	@Override
	@Bean(name = { "restHighLevelClient" })
	public RestHighLevelClient elasticsearchClient() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(USER_NAME, USER_PASS);

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:443")
				.usingSsl(createSSLContext()).withDefaultHeaders(headers).build();

		return RestClients.create(clientConfiguration).rest();
	}

	private SSLContext createSSLContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			KeyManager[] keyManagers = getKeyManagers();
			TrustManager[] trustManagers = getTrustManagers();

			sslContext.init(keyManagers, trustManagers, null);

			return sslContext;
		} catch (Exception e) {
			LOG.error("cannot create SSLContext", e);
		}
		return null;
	}

	private KeyManager[] getKeyManagers()
			throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("client.combined.p12")) {
			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
			clientKeyStore.load(inputStream, CERT_PASSWORD.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(clientKeyStore, CERT_PASSWORD.toCharArray());
			return kmf.getKeyManagers();
		}
	}

	private TrustManager[] getTrustManagers() {
		return new TrustManager[] { new X509ExtendedTrustManager() {
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {}

			public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {}

			public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {}

			public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {}

			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} };
	}

	@Bean
	@Primary
	public ElasticsearchOperations elasticsearchTemplate() {
		return elasticsearchOperations();
	}

	// use the ElasticsearchEntityMapper
	@Bean
	@Override
	public EntityMapper entityMapper() {
		ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
				new DefaultConversionService());
		entityMapper.setConversions(elasticsearchCustomConversions());

		return entityMapper;
	}
}

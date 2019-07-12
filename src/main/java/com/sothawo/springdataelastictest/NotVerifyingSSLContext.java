/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sothawo.springdataelastictest;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

/**
 * Utility class providing a SSLContext that does not verify the certificates.
 *
 * @author Peter-Josef Meisch
 */
public final class NotVerifyingSSLContext {

	private static SSLContext sslContext;

	static {
		try {
			sslContext = createSSLContext();
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SSLContext getSslContext() {
		return sslContext;
	}

	private NotVerifyingSSLContext() {}

	private static SSLContext createSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		TrustManager[] trustManagers = new TrustManager[] { new X509ExtendedTrustManager() {
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

		sslContext.init(null, trustManagers, null);

		return sslContext;
	}
}

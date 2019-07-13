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
package com.sothawo.springdataelastictest

import java.net.Socket
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import javax.net.ssl.TrustManager
import javax.net.ssl.X509ExtendedTrustManager

/**
 * Utility class providing a SSLContext that does not verify the certificates.
 *
 * @author Peter-Josef Meisch
 */
object NotVerifyingSSLContext {

    var sslContext: SSLContext? = null
        private set

    init {
        try {
            sslContext = createSSLContext()
        } catch (e: NoSuchAlgorithmException) {
            throw ExceptionInInitializerError(e)
        } catch (e: KeyManagementException) {
            throw ExceptionInInitializerError(e)
        }

    }

    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    private fun createSSLContext(): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")

        val trustManagers = arrayOf<TrustManager>(object : X509ExtendedTrustManager() {
            override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String, socket: Socket) {}

            override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String, socket: Socket) {}

            override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String, sslEngine: SSLEngine) {}

            override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String, sslEngine: SSLEngine) {}

            override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {}

            override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate?> = arrayOfNulls(0)
        })

        sslContext.init(null, trustManagers, null)

        return sslContext
    }
}

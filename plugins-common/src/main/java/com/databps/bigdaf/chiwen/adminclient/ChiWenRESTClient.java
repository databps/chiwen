/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.databps.bigdaf.chiwen.adminclient;

import com.databps.bigdaf.chiwen.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;


public class ChiWenRESTClient {

	private static final Log LOG = LogFactory.getLog(ChiWenRESTClient.class);

	public static final String RANGER_SSL_CONTEXT_ALGO_TYPE					     = "SSL";

	private String  mUrl;
	private String  mUsername;
	private String  mPassword;
	private boolean mIsSSL;



	private Gson gsonBuilder;
	private volatile Client client;

	private int  mRestClientConnTimeOutMs;
	private int  mRestClientReadTimeOutMs;

	public ChiWenRESTClient() {

	}

	public ChiWenRESTClient(String url) {
		mUrl               = url;
		init();
	}



	public Client getClient() {
		// result saves on access time when client is built at the time of the call
		Client result = client;
		if(result == null) {
			synchronized(this) {
				result = client;
				if(result == null) {
					client = result = buildClient();
				}
			}
		}

		return result;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public String getUsername() {
		return mUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public int getRestClientConnTimeOutMs() {
		return mRestClientConnTimeOutMs;
	}

	public void setRestClientConnTimeOutMs(int mRestClientConnTimeOutMs) {
		this.mRestClientConnTimeOutMs = mRestClientConnTimeOutMs;
	}

	public int getRestClientReadTimeOutMs() {
		return mRestClientReadTimeOutMs;
	}

	public void setRestClientReadTimeOutMs(int mRestClientReadTimeOutMs) {
		this.mRestClientReadTimeOutMs = mRestClientReadTimeOutMs;
	}

	public void setBasicAuthInfo(String username, String password) {
		mUsername = username;
		mPassword = password;
	}

	public WebResource getResource(String relativeUrl) {
		WebResource ret = getClient().resource(getUrl() + relativeUrl);
		
		return ret;
	}

	public String toJson(Object obj) {
		return gsonBuilder.toJson(obj);		
	}
	
	public <T> T fromJson(String json, Class<T> cls) {
		return gsonBuilder.fromJson(json, cls);
	}


	private Client buildClient() {
		X509TrustManager x509mgr = new X509TrustManager() {

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		Client client = null;

		if (mIsSSL) {
			KeyManager[]   kmList     = null;
			TrustManager[] tmList     = new TrustManager[] {x509mgr};
			SSLContext     sslContext = getSSLContext(kmList, tmList);
			ClientConfig   config     = new DefaultClientConfig();

			config.getClasses().add(JacksonJsonProvider.class); // to handle List<> unmarshalling

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
//					if (urlHostName.equals("localhost")) {
//						return true;
//					}
//					return session.getPeerHost().equals(urlHostName);

					return true;
				}
			};

			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(hv, sslContext));

			client = Client.create(config);
		}

		if(client == null) {
			ClientConfig config = new DefaultClientConfig();

			config.getClasses().add(JacksonJsonProvider.class); // to handle List<> unmarshalling

			client = Client.create(config);
		}

		if(!StringUtils.isEmpty(mUsername) && !StringUtils.isEmpty(mPassword)) {
			client.addFilter(new HTTPBasicAuthFilter(mUsername, mPassword));
		}

		// Set Connection Timeout and ReadTime for the PolicyRefresh
		client.setConnectTimeout(mRestClientConnTimeOutMs);
		client.setReadTimeout(mRestClientReadTimeOutMs);

		return client;
	}

	private void init() {
		mIsSSL = StringUtil.containsIgnoreCase(mUrl, "https");
		try {
			gsonBuilder = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting().create();
		} catch(Throwable excp) {
			LOG.fatal("ChiWenRESTClient.init(): failed to create GsonBuilder object", excp);
		}



	}


	private SSLContext getSSLContext(KeyManager[] kmList, TrustManager[] tmList) {
	        Validate.notNull(tmList, "TrustManager is not specified");
		try {
			SSLContext sslContext = SSLContext.getInstance(RANGER_SSL_CONTEXT_ALGO_TYPE);

			sslContext.init(kmList, tmList, new SecureRandom());

			return sslContext;
		} catch (NoSuchAlgorithmException e) {
			LOG.error("SSL algorithm is not available in the environment", e);
			throw new IllegalStateException("SSL algorithm is not available in the environment: " + e.getMessage(), e);
		} catch (KeyManagementException e) {
			LOG.error("Unable to initials the SSLContext", e);
			throw new IllegalStateException("Unable to initials the SSLContex: " + e.getMessage(), e);
		}
	}



	private void close(InputStream str, String filename) {
		if (str != null) {
			try {
				str.close();
			} catch (IOException excp) {
				LOG.error("Error while closing file: [" + filename + "]", excp);
			}
		}
	}
}

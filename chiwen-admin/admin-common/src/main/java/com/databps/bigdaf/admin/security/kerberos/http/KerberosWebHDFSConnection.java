package com.databps.bigdaf.admin.security.kerberos.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL.Token;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 
 * ===== HTTP GET <br/>
 * <li>OPEN (see FileSystem.open)
 * <li>GETFILESTATUS (see FileSystem.getFileStatus)
 * <li>LISTSTATUS (see FileSystem.listStatus)
 * <li>GETCONTENTSUMMARY (see FileSystem.getContentSummary)
 * <li>GETFILECHECKSUM (see FileSystem.getFileChecksum)
 * <li>GETHOMEDIRECTORY (see FileSystem.getHomeDirectory)
 * <li>GETDELEGATIONTOKEN (see FileSystem.getDelegationToken)
 * <li>GETDELEGATIONTOKENS (see FileSystem.getDelegationTokens) <br/>
 * ===== HTTP PUT <br/>
 * <li>CREATE (see FileSystem.create)
 * <li>MKDIRS (see FileSystem.mkdirs)
 * <li>CREATESYMLINK (see FileContext.createSymlink)
 * <li>RENAME (see FileSystem.rename)
 * <li>SETREPLICATION (see FileSystem.setReplication)
 * <li>SETOWNER (see FileSystem.setOwner)
 * <li>SETPERMISSION (see FileSystem.setPermission)
 * <li>SETTIMES (see FileSystem.setTimes)
 * <li>RENEWDELEGATIONTOKEN (see FileSystem.renewDelegationToken)
 * <li>CANCELDELEGATIONTOKEN (see FileSystem.cancelDelegationToken) <br/>
 * ===== HTTP POST <br/>
 * APPEND (see FileSystem.append) <br/>
 * ===== HTTP DELETE <br/>
 * DELETE (see FileSystem.delete)
 * 
 */
public class KerberosWebHDFSConnection implements WebHDFSConnection {

	protected static final Logger logger = LoggerFactory.getLogger(KerberosWebHDFSConnection.class);

	private String httpfsUrl = WebHDFSConnectionFactory.DEFAULT_PROTOCOL + WebHDFSConnectionFactory.DEFAULT_HOST + ":"
			+ WebHDFSConnectionFactory.DEFAULT_PORT;
	private String principal = WebHDFSConnectionFactory.DEFAULT_USERNAME;
	private String password = WebHDFSConnectionFactory.DEFAULT_PASSWORD;

	private Token token = new Token();
	private AuthenticatedURL authenticatedURL = new AuthenticatedURL(new KerberosAuthenticator2(principal, password));

	public KerberosWebHDFSConnection() {
	}

	public KerberosWebHDFSConnection(String httpfsUrl, String principal, String password) {
		this.httpfsUrl = httpfsUrl;
		this.principal = principal;
		this.password = password;
		this.authenticatedURL = new AuthenticatedURL(new KerberosAuthenticator2(principal, password));
	}

	public static synchronized Token generateToken(String srvUrl, String princ, String passwd) {
		Token newToken = new Token();
		try {
			HttpURLConnection conn = new AuthenticatedURL(new KerberosAuthenticator2(princ, passwd))
					.openConnection(new URL(new URL(srvUrl), "/webhdfs/v1/?op=GETHOMEDIRECTORY"), newToken);
			conn.connect();

			conn.disconnect();

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error("[" + princ + ":" + passwd + "]@" + srvUrl, ex);
			// WARN
			// throws MalformedURLException, IOException,
			// AuthenticationException, InterruptedException
		}
		return newToken;
	}

	protected static long copy(InputStream input, OutputStream result) throws IOException {
		byte[] buffer = new byte[12288]; // 8K=8192 12K=12288 64K=
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			result.write(buffer, 0, n);
			count += n;
			result.flush();
		}
		result.flush();
		return count;
	}

	public void ensureValidToken() {
		if (!token.isSet()) { // if token is null
			token = generateToken(httpfsUrl, principal, password);
		} else {
			long currentTime = new Date().getTime();
			long tokenExpired = Long.parseLong(token.toString().split("&")[3].split("=")[1]);
			logger.info("[currentTime vs. tokenExpired] " + currentTime + " " + tokenExpired);

			if (currentTime > tokenExpired) { // if the token is expired
				token = generateToken(httpfsUrl, principal, password);
			}
		}

	}

	/*
	 * ========================================================================
	 * GET
	 * ========================================================================
	 */

	/**
	 * 
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws AuthenticationException
	 */
	public String getResponseString(String urlStr)
			throws IOException, AuthenticationException {
		ensureValidToken();
		URL url = new URL(urlStr);
		HttpURLConnection conn = authenticatedURL.openConnection(url, token);
		conn.setRequestMethod("GET");
		conn.connect();
		String resp = getResult(conn);
		conn.disconnect();
		return resp;
	}

	private String getResult(HttpURLConnection conn) throws IOException {
		InputStream is = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		is.close();

		return sb.toString();
	}


	// End Getter & Setter
}

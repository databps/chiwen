package com.databps.bigdaf.core.util;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

/**
 * @author shibingxin
 * @create 2017-07-31 下午7:47
 */

public class HttpsUtils {

  private static final String HTTP = "http";
  private static final String HTTPS = "https";
  private static SSLConnectionSocketFactory sslsf = null;
  private static PoolingHttpClientConnectionManager cm = null;
  private static SSLContextBuilder builder = null;
  private static CloseableHttpClient httpClient = null;

  static {
    try {
      builder = new SSLContextBuilder();
      builder.loadTrustMaterial(null, new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s)
            throws CertificateException {
          return true;
        }
      });
      builder = new SSLContextBuilder();
      // 全部信任 不做身份鉴定
      builder.loadTrustMaterial(null, new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s)
            throws CertificateException {
          return true;
        }
      });
      sslsf = new SSLConnectionSocketFactory(builder.build(),
          new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null,
          NoopHostnameVerifier.INSTANCE);
      Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
          .register(HTTP, new PlainConnectionSocketFactory())
          .register(HTTPS, sslsf)
          .build();
      cm = new PoolingHttpClientConnectionManager(registry);
      cm.setMaxTotal(200);//max connection
      httpClient = getHttpClient();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String get(String url) {
    return get(url, null);
  }

  public static String get(String url, Map<String, String> param) {
    return get(url, param, null);
  }

  public static String get(String url, Map<String, String> param, Map<String, String> header) {
    String result = "fail";
    try {
      HttpGet httpGet = new HttpGet(url);
      if (header != null) {
        for (Map.Entry<String, String> entry : header.entrySet()) {
          httpGet.addHeader(entry.getKey(), entry.getValue());
        }
      }
      HttpResponse httpResponse = httpClient.execute(httpGet);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode == HttpStatus.SC_OK) {
        HttpEntity resEntity = httpResponse.getEntity();
        result = EntityUtils.toString(resEntity);
      } else if(statusCode == HttpStatus.SC_FORBIDDEN) {
        HttpEntity resEntity = httpResponse.getEntity();
        result = EntityUtils.toString(resEntity);
      }
      else {
        readHttpResponse(httpResponse);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static String post(String url) throws Exception {
    return post(url, null);
  }

  public static String post(String url, Map<String, String> param) throws Exception {
    return post(url, param, null);
  }

  public static String post(String url, Map<String, String> param, HttpEntity entity)
      throws Exception {
    return post(url, param, entity, null);
  }

  public static String post(String url, Map<String, String> param, HttpEntity entity,
      Map<String, String> header) {
    String result = "fail";
    try {
      HttpPost httpPost = new HttpPost(url);
      if (header != null) {
        for (Map.Entry<String, String> entry : header.entrySet()) {
          httpPost.addHeader(entry.getKey(), entry.getValue());
        }
      }
      if (param != null) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
          formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
            Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
      }
      if (entity != null) {
        httpPost.setEntity(entity);
      }
      HttpResponse httpResponse = httpClient.execute(httpPost);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode == HttpStatus.SC_OK) {
        HttpEntity resEntity = httpResponse.getEntity();
        result = EntityUtils.toString(resEntity);
      } else if(statusCode == HttpStatus.SC_FORBIDDEN) {
        HttpEntity resEntity = httpResponse.getEntity();
        result = EntityUtils.toString(resEntity);
      }else {
        readHttpResponse(httpResponse);
      }
    } catch (Exception e) {
    } finally {
      try {
        httpClient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static CloseableHttpClient getHttpClient() throws Exception {
    RequestConfig defaultRequestConfig = RequestConfig.custom()
        .setSocketTimeout(3000)
        .setConnectTimeout(3000)
        .setConnectionRequestTimeout(3000)
        .build();
    CloseableHttpClient httpClient = HttpClients.custom()
        .setSSLSocketFactory(sslsf)
        .setConnectionManager(cm)
        .setConnectionManagerShared(true)
        .setDefaultRequestConfig(defaultRequestConfig)
        .build();
    return httpClient;
  }

  public static String readHttpResponse(HttpResponse httpResponse)
      throws ParseException, IOException {
    StringBuilder builder = new StringBuilder();
    HttpEntity entity = httpResponse.getEntity();
    builder.append("status:" + httpResponse.getStatusLine());
    builder.append("headers:");
    HeaderIterator iterator = httpResponse.headerIterator();
    while (iterator.hasNext()) {
      builder.append("\t" + iterator.next());
    }
    if (entity != null) {
      String responseString = EntityUtils.toString(entity);
      builder.append("response length:" + responseString.length());
      builder.append("response content:" + responseString.replace("\r\n", ""));
    }
    return builder.toString();
  }

//  public static void main(String args[]) throws Exception {
////    String resString = HttpsUtils.post("https://kyfw.12306.cn/otn/login/init");
//    String resString = HttpsUtils.get("http://192.168.1.131:50070/jmx");
//    System.out.println(resString);
//  }
}
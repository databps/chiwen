package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.admin.domain.model.Access;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.exception.StandbyException;
import com.databps.bigdaf.core.exception.SafeModeException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
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
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author haipeng
 * @create 17-9-28 下午2:51
 */
@Component
public class HAHttpClient implements HAHttpClientFactory {

  private final Logger log = LoggerFactory.getLogger(HAHttpClient.class);

  private AtomicInteger counter;
  private final int RETRY_COUNTER_ATTRIBUTE = 3;

  private static final String HTTP = "http";
  private static final String HTTPS = "https";
  private static SSLConnectionSocketFactory sslsf = null;
  private static PoolingHttpClientConnectionManager cm = null;
  private static SSLContextBuilder builder = null;
  private static CloseableHttpClient httpClient = null;
  private ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<String>();

  private String httpUrls;
  private String accessType;
  private String paths;
  private static HttpResponse returnResponse;
  private static String returnUrl;

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
      cm.setMaxTotal(300);//max connection
      httpClient = getHttpClient();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initParam(String httpUrls, String accessType, String paths) {
    counter = new AtomicInteger(0);
    this.httpUrls = httpUrls;
    if (accessType != null) {
      this.accessType = accessType;
    }
    this.paths = paths;
    createUrlQueue();
  }

  private String doGetHa() throws StandbyException, IOException {
    if (!queue.isEmpty()) {
      String top = queue.element();
     return  get(top);
    }
    return null;
  }

  private void doPostHa() throws StandbyException, IOException {
    if (!queue.isEmpty()) {
      String top = queue.element();
      post(top);
    }
  }

  @Override
  public HttpResponse doGetHaProvider(String httpUrls, String accessType, String paths)
      throws StandbyException, IOException {
    initParam(httpUrls, accessType, paths);
    doGetHa();
//    log.debug("No Active URL was found for service: {0}");
    return returnResponse;
  }

  @Override
  public HttpResponse doPostHaProvider(String httpUrls, String accessType, String paths)
      throws StandbyException, IOException {
    initParam(httpUrls, accessType, paths);
    doPostHa();
//    log.debug("No Active URL was found for service: {0}");

    return returnResponse;
  }

  @Override
  public String doGetHaProvider(String httpUrls, String paths)
      throws StandbyException, IOException {
    initParam(httpUrls, null, paths);
//    log.debug("No Active URL was found for service: {0}");
    return doGetHa();
  }

  @Override
  public HttpResponse doPostHaProvider(String httpUrls, String paths)
      throws StandbyException, IOException {
    initParam(httpUrls, null, paths);
    doPostHa();
//    log.debug("No Active URL was found for service: {0}");

    return returnResponse;
  }

  @Override
  public String doGetHaUrl(String httpUrls, String paths) throws StandbyException, IOException {
    initParam(httpUrls, null, paths);
    doGetHa();
//    log.debug("No Active URL was found for service: {0}");
    if(queue.size() >0)
      return queue.element();
    return null;
  }

  @Override
  public String doPostHaUrl(String httpUrls, String paths) throws StandbyException, IOException {
    initParam(httpUrls, null, paths);
    doPostHa();
//    log.debug("No Active URL was found for service: {0}");
    if(queue.size() >0)
      return queue.element();
    return null;
  }

  private void createUrlQueue() {
    queue.clear();
    if(StringUtils.isBlank(httpUrls))
      return ;
    String[] hdfsHttpUrl = httpUrls.split(",");
    for (String url : hdfsHttpUrl) {
      queue.offer(getUrl(url));
    }
  }

  /**
   * TODO：根据类型添加不同返回url字符串
   */
  private String getUrl(String activeUrl) {
    if (accessType == null) {
      return activeUrl + paths;
    }
    return null;
  }

  private String failoverRequest(String requestType) throws IOException {
    String result =null;
    String failed = queue.poll();// 取出第一个
    queue.offer(failed);// 把第一个放在末尾
    String top = queue.element();
    if (requestType.equals(RequestTypeEnum.GET.getName())) {
      result = get(top);
    } else if (requestType.equals(RequestTypeEnum.POST.getName())) {
      result = post(top);
    }
    return result;
  }


  private String get(String url) throws IOException {
    return get(url, null);
  }

  private String get(String url, Map<String, String> param) throws IOException {
    return get(url, param, null);
  }


  private String get(String url, Map<String, String> param, Map<String, String> header)
      throws IOException {
    HttpGet httpGet = new HttpGet(url);
    returnUrl = url;
   return GetRequstEntity(httpGet, header, RequestTypeEnum.GET.getName());
  }

  private String GetRequstEntity(HttpRequestBase mothod, Map<String, String> header,
      String requestType) throws IOException {
    String result = null;
    CloseableHttpResponse httpResponse = null;
    try {
      if (counter.incrementAndGet() <= RETRY_COUNTER_ATTRIBUTE) {
        if (header != null) {
          for (Map.Entry<String, String> entry : header.entrySet()) {
            mothod.addHeader(entry.getKey(), entry.getValue());
          }
        }
        httpResponse = httpClient.execute(mothod);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        try {
          if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
              result = EntityUtils.toString(entity, "UTF-8");
            }
          } else if (statusCode == HttpStatus.SC_FORBIDDEN ) {
            HttpEntity resEntity = httpResponse.getEntity();
            throwException(EntityUtils.toString(resEntity));
          }
        } finally {
          httpResponse.close();
        }
      }
    } catch (StandbyException e) {
//      log.debug("Received an error from a node in Standby: {}", e);
      result = failoverRequest(requestType);
    } catch (SafeModeException e) {
//      log.debug("Received an error from a node in SafeMode: {}", e);
      result = GetRequstEntity(mothod, header, requestType);
    } catch (IOException e) {
      if (httpResponse != null) {
        EntityUtils.consume(httpResponse.getEntity());
      }
//      log.debug("Received an error from a node in IOException: {}", e);
      result = failoverRequest(requestType);
    } finally {
      try {
        if(httpResponse!=null)
          httpResponse.close();
      } catch (IOException e) {
      }
      try {
        httpClient.close();
      } catch (IOException e) {
      }
    }
    return result;
  }

  private void throwException(String httpBody) {
    if (httpBody.contains("StandbyException")) {
      throw new StandbyException("Method throw a StandbyException", HttpStatus.SC_FORBIDDEN);
    } else if (httpBody.contains("SafeModeException") || httpBody.contains("RetriableException")) {
      throw new SafeModeException("Method throw a SafeModeException", HttpStatus.SC_FORBIDDEN);
    }
  }

  private String post(String url) throws IOException {
   return post(url, null);
  }

  private String post(String url, Map<String, String> param) throws IOException {
    return post(url, param, null);
  }

  private String post(String url, Map<String, String> param, HttpEntity entity) throws IOException {
   return post(url, param, entity, null);
  }

  private String  post(String url, Map<String, String> param, HttpEntity entity,
      Map<String, String> header) throws IOException {
    HttpPost httpPost = new HttpPost(url);
    if (param != null) {
      List<NameValuePair> formparams = new ArrayList<NameValuePair>();
      for (Map.Entry<String, String> entry : param.entrySet()) {
        formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
      }
      UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
          Consts.UTF_8);
      httpPost.setEntity(urlEncodedFormEntity);
    }
    returnUrl = url;
    return GetRequstEntity(httpPost, header, RequestTypeEnum.POST.getName());
  }

  private static CloseableHttpClient getHttpClient() throws IOException {
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
}

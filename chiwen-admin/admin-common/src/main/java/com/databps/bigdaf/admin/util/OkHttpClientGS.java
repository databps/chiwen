package com.databps.bigdaf.admin.util;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class OkHttpClientGS {

  private static final OkHttpClient okHttpClient = new OkHttpClient();

  private static OkHttpClient okHttpsClient;

  static {

    try {
      TrustManagerFactory trustManagerFactory = TrustManagerFactory
          .getInstance(TrustManagerFactory.getDefaultAlgorithm());
      trustManagerFactory.init((KeyStore) null);
      TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
      if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
        throw new IllegalStateException(
            "Unexpected default trust managers:" + Arrays.toString(trustManagers));
      }
      X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

      SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, new TrustManager[]{createTrustManager()}, null);
      SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      okHttpsClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager)
          .hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
              return true;
            }
          }).build();
      okHttpsClient.dispatcher().setMaxRequests(1000);

      okHttpsClient.newBuilder().connectTimeout(2000, TimeUnit.MILLISECONDS).readTimeout(3000,
          TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    }
    okHttpClient.dispatcher().setMaxRequests(1000);
    okHttpClient.newBuilder().connectTimeout(2000, TimeUnit.MILLISECONDS)
        .readTimeout(3000, TimeUnit.MILLISECONDS);
  }

  public static String get(String url) {
    Request request = new Request.Builder().url(url).get().build();
    Call call = null;
    URI uri = URI.create(url);
    if (uri.getScheme().equalsIgnoreCase("http")) {
      call = okHttpClient.newCall(request);
    } else {
      call = okHttpsClient.newCall(request);
    }
    Response response = null;
    try {
      response = call.execute();
      String result = "";
      if (response.isSuccessful()) {
        result = response.body().string();
      } else {
        result = "connect failed:" + response.message();
      }
      return result;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      call.cancel();
    }
    return null;
  }


  private static TrustManager createTrustManager() {

    return new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
          throws CertificateException {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
          throws CertificateException {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }
    };
  }

}

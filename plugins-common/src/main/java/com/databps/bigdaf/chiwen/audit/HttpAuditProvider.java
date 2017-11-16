package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.adminclient.ChiWenRESTClient;
import com.databps.bigdaf.chiwen.config.ChiWenConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * HttpAuditProvider connect admin to push audit message
 *
 * @author lgc
 * @create 2017-07-25 下午4:32
 */
public class HttpAuditProvider extends BaseAuditProvider {

  private static final Log LOG = LogFactory.getLog(HttpAuditProvider.class);


  public final String REST_URL_ATTIBUTE = "chiwen.plugin.hbase.policy.rest.url";
  public static String REST_URL;//"http://localhost:8080/api/logaudit/log";//"http://www.ChiWenAdmin.com/";
  public final String URL_CODING = "utf-8";//"http://www.ChiWenAdmin.com/";
  private ChiWenRESTClient restClient;
  private Gson gson = null;

  public HttpAuditProvider() {
    int restClientConnTimeOutMs = 120 * 1000;
    int restClientReadTimeOutMs = 30 * 1000;
    REST_URL = System.getenv("ADMIN_URL");
    if (REST_URL == null) {
      REST_URL = "https://localhost:8085";
    }
    gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting().create();
    restClient = new ChiWenRESTClient(REST_URL);
    restClient.setRestClientConnTimeOutMs(restClientConnTimeOutMs);
    restClient.setRestClientReadTimeOutMs(restClientReadTimeOutMs);
  }

  public boolean log(AuthzAuditEvent event) {
    boolean isSuccess=true;

    try {
      String eventStr = gson.toJson(event);
      WebResource resource = restClient.getResource("/api/v2/audits");

      ClientResponse response = resource.entity(eventStr).type(MediaType.APPLICATION_JSON_TYPE)
          .post(ClientResponse.class);//queryParam只能传key-value都是string类型
      if(response==null){
        isSuccess=false;
      }else{
        if(response.getStatus() != HttpServletResponse.SC_OK){
          isSuccess=false;
        }
      }

    } catch (Exception e) {
      LOG.info("==> HttpAuditProvider.log>>>>>>>>>>>>>>>>>>>>error:" + e.getMessage());
      isSuccess=false;
    }
    return isSuccess;
  }
//    CloseableHttpClient httpclient = HttpClients.createDefault();
//    HttpPost httpPost = new HttpPost(REST_URL);
//
//    StringEntity entity = new StringEntity(gson.toJson(event), "utf-8");
//    entity.setContentEncoding("UTF-8");
//    entity.setContentType("application/json");
//    httpPost.setEntity(entity);
//    HttpResponse resp = null;
//    try {
//      resp = httpclient.execute(httpPost);
//    } catch (IOException e) {
//      e.printStackTrace();
//      logFailedEvent(
//          event);//if log event failure ,then use logFailedEvent method to print event to local
//    } finally {
//      try {
//        httpclient.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void flush() {

  }
}
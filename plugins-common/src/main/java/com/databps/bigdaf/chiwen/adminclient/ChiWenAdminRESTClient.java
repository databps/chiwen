package com.databps.bigdaf.chiwen.adminclient;

import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.model.ResponseJson;
import com.databps.bigdaf.chiwen.util.ChiWenServiceNotFoundException;
import com.databps.bigdaf.chiwen.util.GrantRevokeRequest;
import com.databps.bigdaf.chiwen.util.MiscUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivilegedAction;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * Created by lgc on 17-7-18.
 */
public class ChiWenAdminRESTClient implements ChiWenAdminClient {

  private static final Log LOG = LogFactory.getLog(ChiWenAdminRESTClient.class);
  public final String REST_URL_POLICY_GET_FOR_SERVICE_IF_UPDATED = "/service/plugins/policies/download/";
  public static final String REST_MIME_TYPE_JSON = "application/json";
  public String REST_URL;//"http://localhost:8080/";//"http://www.ChiWenAdmin.com/";
  public final String REST_URL_ATTIBUTE = "chiwen.plugin.hbase.policy.rest.url";
  private ChiWenRESTClient restClient;
  private String  serviceName;

  @Override
  public void init(String serviceName, String appId) {
    int restClientConnTimeOutMs = 120 * 1000;
    int restClientReadTimeOutMs = 30 * 1000;
    String REST_URL = System.getenv("ADMIN_URL");
    if (REST_URL == null) {
      REST_URL = "https://localhost:8085";
    }

    restClient = new ChiWenRESTClient(REST_URL);
    restClient.setRestClientConnTimeOutMs(restClientConnTimeOutMs);
    restClient.setRestClientReadTimeOutMs(restClientReadTimeOutMs);


  }
  public static final String REST_URL_SECURE_SERVICE_REVOKE_ACCESS             = "/service/plugins/secure/services/revoke/";

  @Override
  public void revokeAccess(final GrantRevokeRequest request) throws Exception {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenAdminRESTClient.revokeAccess(" + request + ")");
    }

    ClientResponse response = null;
    UserGroupInformation user = MiscUtil.getUGILoginUser();
    boolean isSecureMode = user != null && UserGroupInformation.isSecurityEnabled();
    String ht=restClient.toJson(request);
    System.out.println("revokeAccess="+ht);
//    WebResource webResource = createWebResource(REST_URL_SECURE_SERVICE_REVOKE_ACCESS + serviceId)
//        .queryParam(RangerRESTUtils.REST_PARAM_PLUGIN_ID, pluginId);
//    response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, restClient.toJson(request));

//   if(response != null && response.getStatus() != 200) {
//     ResponseJson resp = ResponseJson.fromClientResponse(response);
//      LOG.error("revokeAccess() failed: HTTP status=" + response.getStatus() + ", message=" + resp.getMessage() + ", isSecure=" + isSecureMode + (isSecureMode ? (", user=" + user) : ""));
//
//    if(response.getStatus() == 401) {
//        throw new AccessControlException();
//      }
//
//      throw new Exception("HTTP " + response.getStatus() + " Error: " + resp.getMessage());
//    } else if(response == null) {
//     throw new Exception("unknown error. revokeAccess(). serviceName=" + serviceName);
//    }
//
//   if(LOG.isDebugEnabled()) {
//      LOG.debug("<== ChiWenAdminRESTClient.revokeAccess(" + request + ")");
//    }
  }

  private WebResource createWebResource(String url) {
    WebResource ret = restClient.getResource(url);

    return ret;
  }

  @Override
  public ChiWenPolicyPluginVo getHdfsServicePoliciesIfUpdated(String serviceName, String chiWenUUID)
      throws Exception {

    ClientResponse response = null;

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenAdminRESTClient.getHdfsServicePoliciesIfUpdated(" + chiWenUUID + ": "
          + chiWenUUID + ")");
    }
    ChiWenPolicyPluginVo ret = null;
    String urlString = "/api/v2/policy/pull/hdfs?chiWenUUID="+chiWenUUID;

    WebResource resource = restClient.getResource(urlString);
    response = resource.accept(MediaType.APPLICATION_JSON_TYPE)
        .get(ClientResponse.class);//queryParam只能传key-value都是string类型
    if (response == null || response.getStatus() == HttpServletResponse.SC_NOT_MODIFIED) {
      if (response == null) {
        LOG.error("Error getting policies; Received NULL response!!. ");
      } else {
        ResponseJson resp = ResponseJson.fromClientResponse(response);
        if (LOG.isDebugEnabled()) {
          LOG.debug(
              "No change in policies. " + "response=" + resp.getMsg() + ", serviceName="
                  + serviceName);
        }
      }
      ret = null;
    } else if (response.getStatus() == HttpServletResponse.SC_OK) {

      ret = response.getEntity(ChiWenPolicyPluginVo.class);

      //ResponseJson json = response.getEntity(ChiWenPolicy.class);
      //ret = json == null ? null : json.getData();

    } else if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
      LOG.error(
          "Error getting policies; service not found. "
              + ", response=" + response.getStatus() + ", serviceName=" + serviceName);
      String exceptionMsg = response.hasEntity() ? response.getEntity(String.class) : null;

      ChiWenServiceNotFoundException.throwExceptionIfServiceNotFound(serviceName, exceptionMsg);

      LOG.warn("Received 404 error code with body:[" + exceptionMsg + "], Ignoring");
    } else {
      ResponseJson resp = ResponseJson.fromClientResponse(response);
      LOG.warn(
          "Error getting policies. response="
              + resp.getMsg() + ", serviceName=" + serviceName);
      ret = null;
    }

    return ret;
  }

  @Override
  public ChiWenPolicyHbaseVo getHbaseServicePoliciesIfUpdated(String serviceName, String chiWenUUID)
      throws Exception {

    ClientResponse response = null;

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenAdminRESTClient.getHbaseServicePoliciesIfUpdated(" + chiWenUUID + ": "
          + chiWenUUID + ")");
    }
    ChiWenPolicyHbaseVo ret = null;
    String urlString = "/api/v2/policy/pull/hbase?chiWenUUID="+chiWenUUID;

    WebResource resource = restClient.getResource(urlString);
    response = resource.accept(MediaType.APPLICATION_JSON_TYPE)
        .get(ClientResponse.class);//queryParam只能传key-value都是string类型
    if (response == null || response.getStatus() == HttpServletResponse.SC_NOT_MODIFIED) {
      if (response == null) {
        LOG.error("Error getting policies; Received NULL response!!. ");
      } else {
        ResponseJson resp = ResponseJson.fromClientResponse(response);
        if (LOG.isDebugEnabled()) {
          LOG.debug(
              "No change in policies. " + "response=" + resp.getMsg() + ", serviceName="
                  + serviceName);
        }
      }
      ret = null;
    } else if (response.getStatus() == HttpServletResponse.SC_OK) {

      ret = response.getEntity(ChiWenPolicyHbaseVo.class);

      //ResponseJson json = response.getEntity(ChiWenPolicy.class);
      //ret = json == null ? null : json.getData();

    } else if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
      LOG.error(
          "Error getting policies; service not found. "
              + ", response=" + response.getStatus() + ", serviceName=" + serviceName);
      String exceptionMsg = response.hasEntity() ? response.getEntity(String.class) : null;

      ChiWenServiceNotFoundException.throwExceptionIfServiceNotFound(serviceName, exceptionMsg);

      LOG.warn("Received 404 error code with body:[" + exceptionMsg + "], Ignoring");
    } else {
      ResponseJson resp = ResponseJson.fromClientResponse(response);
      LOG.warn(
          "Error getting policies. response="
              + resp.getMsg() + ", serviceName=" + serviceName);
      ret = null;
    }


    return ret;
  }

  @Override
  public void grantAccess(GrantRevokeRequest request) throws Exception {
    System.out.println("grantAccess");
  }

  //  private WebResource createWebResource(String url) {
//    ClientConfig cc = new DefaultClientConfig();
//    Client client = Client.create(cc);
//    WebResource ret = client.resource(url);
//
//    return ret;
//  }
  public String ConvertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      System.out.println("Error=" + e.toString());
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        System.out.println("Error=" + e.toString());
      }
    }
    return sb.toString();

  }
}

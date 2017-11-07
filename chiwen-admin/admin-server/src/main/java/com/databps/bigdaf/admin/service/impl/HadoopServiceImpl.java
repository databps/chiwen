package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.security.kerberos.KerberosHttpClient;
import com.databps.bigdaf.admin.service.HadoopService;
import com.databps.bigdaf.admin.util.BytUtils;
import com.databps.bigdaf.admin.util.HAHttpClientFactory;
import com.databps.bigdaf.admin.util.HadoopJMXType;
import com.databps.bigdaf.admin.vo.BrowseVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-08-11 上午9:59
 */
@Component
public class HadoopServiceImpl implements HadoopService {

  @Autowired
  private ServicesDao servicesDao;
  @Autowired
  private HAHttpClientFactory hAHttpClientFactory;

  public List<BrowseVo> getHadoopFileList(String cmpyId, String paths) throws IOException {
    if (StringUtils.isBlank(paths)) {
      paths = "/";
    }
    getHttpUrls(cmpyId);
    JsonElement element = null;
    String httpUrls = getHttpUrls(cmpyId);
    if (StringUtils.isBlank(httpUrls)) {
      throw new IOException();
    }
    String responseString = hAHttpClientFactory.doGetHaProvider(httpUrls, getHDFSUrl(paths));
    if(responseString==null)
      throw new IOException();
    element = new JsonParser().parse(responseString).getAsJsonObject();
    return setBrowseVo(element);
  }

  @Override
  public List<BrowseVo> getHadoopFileList(String cmpyId, String paths, String hdfsPrincipal,
      String hdfsKeytabPath,
      String krb5confPath) throws IOException {
    if (StringUtils.isBlank(paths)) {
      paths = "/";
    }
    String url =  getHdfsHost(cmpyId) +getHDFSUrl(paths);
    KerberosHttpClient client = new KerberosHttpClient(hdfsPrincipal, hdfsKeytabPath, krb5confPath,
        false);
    HttpResponse responses = client.callRestUrl(url, hdfsPrincipal);
    if(responses ==null)
      return null;
    HttpEntity resEntity = responses.getEntity();
    String result = EntityUtils.toString(resEntity);
    JsonElement element = null;
    element = new JsonParser().parse(result).getAsJsonObject();
    return setBrowseVo(element);
  }

  List<BrowseVo> setBrowseVo(JsonElement jsonElement) {
    if (jsonElement == null) {
      return null;
    }
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    if (jsonObject.get("FileStatuses") == null) {
      return null;
    }
    JsonObject objFiles = jsonObject.get("FileStatuses").getAsJsonObject();
    JsonArray array = objFiles.get("FileStatus").getAsJsonArray();
    Gson gson = new Gson();
    List<BrowseVo> browseVos = gson.fromJson(array, new TypeToken<List<BrowseVo>>() {
    }.getType());
    for (BrowseVo browseVo : browseVos) {
      transformVo(browseVo);
    }
    return browseVos;
  }

  private void transformVo(BrowseVo browseVo) {
    String modificationTime = browseVo.getModificationTime();
    String formaDate = DateFormatUtils
        .format(Long.parseLong(modificationTime), DateUtils.YYYYMMDDHHMMSSSSS);
    browseVo.setModificationTime(formaDate);
  }

  public JsonElement getHadoopJmx(String cmpyId) throws IOException {
    getHttpUrls(cmpyId);
    JsonElement element = getHadoopJMX(cmpyId);
    return element;
  }

  public Services getServiceConfig(String cmpyId, String name) {
    return servicesDao.getServiceConfig(cmpyId, name);
  }

  private String getHttpUrls(String cmpyId) {
    Services service = getServiceConfig(cmpyId, AuditType.HDFS.getName());
    Map<String, String> hdfsConfig = service.getServiceConfig();
    if (MapUtils.isEmpty(hdfsConfig)) {
      return null;
    }
    String hdfsHttpUrls = hdfsConfig.get("hdfs_http_url");
    return hdfsHttpUrls;
  }

  private JsonElement getHadoopJMX(String cmpyId) throws IOException {
    JsonElement element = null;
    element = getHttpJMX(cmpyId);
    if (element == null) {
      throw new IOException();
    }
    return element;
  }

  private JsonElement getHttpJMX(String cmpyId) throws IOException {
    String httpUrls = getHttpUrls(cmpyId);
    if (StringUtils.isBlank(httpUrls)) {
      throw new IOException();
    }
    String jmxUrl = "/jmx?";
    JsonObject jsonOut = new JsonObject();
    for (HadoopJMXType type : HadoopJMXType.values()) {
      String url = jmxUrl + type.getParam();
      String responseString = hAHttpClientFactory.doGetHaProvider(httpUrls, url);
      JsonObject jmxJson = new JsonParser().parse(responseString).getAsJsonObject();
      getJMX(jmxJson, type, jsonOut);
    }
    return jsonOut;
  }

  public void getJMX(JsonElement jmxJson, HadoopJMXType type, JsonObject jsonOut) {
    JsonArray beanJsonArray = jmxJson.getAsJsonObject().get("beans").getAsJsonArray();
    if (beanJsonArray != null && beanJsonArray.size() > 0) {
      if (HadoopJMXType.NameNode.equals(type)) {
        getdataNodeInfo(beanJsonArray, jsonOut);
      } else if (HadoopJMXType.Runtime.equals(type)) {
        getHadoopUptime(beanJsonArray, jsonOut);
      } else if (HadoopJMXType.Memory.equals(type)) {
        getHadoopMemory(beanJsonArray, jsonOut);
      }
    }
  }

  /**
   * 获取dataNode数量信息
   */
  private void getdataNodeInfo(JsonArray beanJsonArray, JsonObject jsonOut) {
    jsonOut = jsonOut.getAsJsonObject();
    int jsonLiveDataNodes = beanJsonArray.get(0).getAsJsonObject().get("NumLiveDataNodes")
        .getAsInt();
    int jsonDeadDataNodes = beanJsonArray.get(0).getAsJsonObject().get("NumDeadDataNodes")
        .getAsInt();
    int numDecommissioningDataNodes = beanJsonArray.get(0).getAsJsonObject()
        .get("NumDecommissioningDataNodes")
        .getAsInt();
    jsonOut.addProperty("numLiveDataNodes", jsonLiveDataNodes);
    jsonOut.addProperty("numDeadDataNodes", jsonDeadDataNodes);
    jsonOut.addProperty("numDecommissioningDataNodes", numDecommissioningDataNodes);
  }

  /**
   * 获取hadoop运行时间
   */
  private void getHadoopUptime(JsonArray beanJsonArray, JsonObject jsonOut) {
    JsonObject rootJson = new JsonObject();
    String jsonUptime = beanJsonArray.get(0).getAsJsonObject().get("Uptime").getAsString();
    long uptimef = Long.valueOf(jsonUptime);
    jsonOut.addProperty("uptime", uptimef);
  }

  /**
   * 获取hadoop内存
   */
  private void getHadoopMemory(JsonArray beanJsonArray, JsonObject jsonOut) {
    JsonObject rootJson = new JsonObject();
    JsonObject jsonMy = beanJsonArray.get(0).getAsJsonObject().get("HeapMemoryUsage")
        .getAsJsonObject();
    DecimalFormat df = new DecimalFormat("0.0");
    long used = jsonMy.get("used").getAsLong();
    long max = jsonMy.get("max").getAsLong();
    String percent = df.format((float) used * 100 / max);
    jsonOut.addProperty("dataNodesUse", BytUtils.convertByteSize(used));
    jsonOut.addProperty("dataNodesMax", BytUtils.convertByteSize(max));
    jsonOut.addProperty("dataNodesPercent", percent);
  }

  private String getHDFSUrl(String paths) {
    return "/webhdfs/v1" + paths + "?op="
        + "LISTSTATUS" + "&user.name=" + "hdfs";
  }

  public String getHdfsHost(String cmpyId) throws IOException {
    String httpUrls = getHttpUrls(cmpyId);
    String hdfsHost = hAHttpClientFactory.doGetHaUrl(httpUrls, "");
    return hdfsHost;
  }

}
package com.databps.bigdaf.chiwen.model;

import com.databps.bigdaf.chiwen.util.StringUtil;
import com.sun.jersey.api.client.ClientResponse;
import java.io.Serializable;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author merlin
 * @create 2017-07-21 上午10:14
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseJson implements Serializable {

  private static final Logger LOG = Logger.getLogger(ResponseJson.class);

  private String        msgDesc;

  private static final long serialVersionUID = 1L;
  private int code = 1100;

  private String msg;


  private ChiWenPolicyPluginVo data;


  private int httpStatusCode;

  public ResponseJson() {
  }

  public void setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public static ResponseJson fromClientResponse(ClientResponse response) {
    ResponseJson ret = null;

    String jsonString = response == null ? null : response.getEntity(String.class);
    int httpStatus = response == null ? 0 : response.getStatus();

    if (!StringUtil.isEmpty(jsonString)) {
      ret = ResponseJson.fromJson(jsonString);
    }

    if (ret == null) {
      ret = new ResponseJson();
    }

    ret.setHttpStatusCode(httpStatus);

    return ret;
  }



  public String getMessage() {
    return StringUtil.isEmpty(msgDesc) ? ("HTTP " + httpStatusCode) : msgDesc;
  }


  public static ResponseJson fromJson(String jsonString) {
    try {
      ObjectMapper om = new ObjectMapper();

      return om.readValue(jsonString, ResponseJson.class);
    } catch (Exception e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("fromJson('" + jsonString + "') failed", e);
      }
    }

    return null;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ChiWenPolicyPluginVo getData() {
    return data;
  }

  public void setData(ChiWenPolicyPluginVo data) {
    this.data = data;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }
}

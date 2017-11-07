package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.service.AuditService;
import com.databps.bigdaf.admin.vo.AuditVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.util.RequestIPUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AuditController
 *
 * @author lgc
 * @create 2017-08-02 下午4:07
 */

@Controller
@RequestMapping(value = "/api")
public class AuditApiController extends BaseController{

  private static final Log LOG = LogFactory.getLog(AuditApiController.class);
  @Autowired
  private AuditService auditService;


  @RequestMapping(value = "/audits", method = RequestMethod.POST)
  @ResponseBody
  public ResponseJson logAudit(@RequestBody AuditVo auditVo, HttpServletRequest httpServletRequest) {

    String	cmpyId = "5968802a01cbaa46738eee3d";
    auditVo.setCmpyId(cmpyId);
    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    auditVo.setPluginIp(ip);
    auditService.AuditLog(auditVo);
    LOG.info("audit log  info == "+auditVo.toString());
    return responseMsg(0);
  }



}
package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.AuditService;
import com.databps.bigdaf.admin.vo.AuditVo;
import com.databps.bigdaf.admin.vo.LogQueryVo;
import com.databps.bigdaf.admin.vo.PersistentAuditEventVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AuditController
 *
 * @author lgc
 * @create 2017-08-02 下午4:07
 */

@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BaseController {

  private static final Log LOG = LogFactory.getLog(AuditController.class);

  @Autowired
  private AuditService auditService;

  @RequestMapping(value = "/admin/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.AUDITOR)
  public String admin(LogQueryVo query, MongoPage pageable, Model model) {
    List<PersistentAuditEventVo> adminLog = auditService.findPersistentAuditEvent(pageable);
    model.addAttribute("adminLogList", adminLog);
    model.addAttribute("page", pageable);
    return "audit/admin_log";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.AUDITOR)
  public String list(LogQueryVo query, MongoPage page, Model model) {
    query.setServiceType("hdfs");
    List<AuditVo> auditPage = auditService.findAuditsPage(page, query);
    model.addAttribute("auditVoList", auditPage);
    model.addAttribute("page", page);
    return "audit/list";
  }

  @RequestMapping(value = "/hbase/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.AUDITOR)
  public String hbase(LogQueryVo query, MongoPage page, Model model) {
    query.setServiceType("hbase");
    List<AuditVo> auditPage = auditService.findAuditsPage(page, query);
    model.addAttribute("auditVoList", auditPage);
    model.addAttribute("page", page);
    return "audit/hbase_log";
  }

  @RequestMapping(value = "/gateway/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.AUDITOR)
  public String gateway(LogQueryVo query, MongoPage page, Model model) {
    query.setServiceType("gateway");
    List<AuditVo> auditPage = auditService.findAuditsPage(page, query);
    model.addAttribute("auditVoList", auditPage);
    model.addAttribute("page", page);
    return "/audit/gateway_log";
  }


//  @RequestMapping(value = "/import", method=RequestMethod.GET)
//  public String receiveGet(@RequestParam(required = false) Integer typeId,
//      HttpServletResponse response, HttpServletRequest request,RedirectAttributes attr) {
//
//  }
//  @RequestMapping(value = "/deleteInBatch", method = RequestMethod.GET)
//  public String deleteInBatch(
//      @RequestParam(required = false) String currentPage,
//      @RequestParam(required = false) String[] ids,
//      RedirectAttributes attr) {
//
//    auditService.deleteInBatch(ids);
//
//    attr.addAttribute("currentPage", currentPage);
//
//    return "redirect:list";
//  }

  @RequestMapping(value = "/clean", method = RequestMethod.GET)
  @ResponseBody
  public ResponseJson clean(int date) {

    auditService.clean(date);

    return responseMsg(0);
  }


  enum LogType {
    HDFS("hdfs"),
    Admin("admin");
    private String name;

    LogType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

  }

}
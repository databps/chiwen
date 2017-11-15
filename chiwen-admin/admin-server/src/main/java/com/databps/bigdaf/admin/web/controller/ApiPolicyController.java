package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.admin.service.HbaseService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.service.HiveService;
import com.databps.bigdaf.admin.service.PolicyService;
import com.databps.bigdaf.admin.vo.ChiWenPolicyPluginVo;
import com.databps.bigdaf.admin.vo.HbasePolicyVo;
import com.databps.bigdaf.admin.vo.HivePolicyVo;
import com.databps.bigdaf.admin.vo.PolicyFormVo;
import com.databps.bigdaf.admin.vo.PolicyVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.message.ResponseJson;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PolicyController
 *
 * @author lgc
 * @create 2017-08-08 下午2:55
 */

@Controller
@RequestMapping(value = "")
public class ApiPolicyController extends BaseController {

  private static final Log LOG = LogFactory.getLog(ApiPolicyController.class);

  @Autowired
  private HbaseService hbaseService;

  @Autowired
  private HiveService hiveService;

  @Autowired
  private PolicyService policyService;

  @RequestMapping(value = "/api/policy/pull/{chiWenUUID}/{agenttype}", method = RequestMethod.GET)
  @ResponseBody
  public String pullPolicy(@PathVariable(name = "chiWenUUID") String chiWenUUID,
      @PathVariable(name = "agenttype") String agenttype, HttpServletRequest httpServletRequest) {
    String cmpyId = "5968802a01cbaa46738eee3d";
    String typeStr = "";
    String policyJsonStr = "";
    LOG.debug("/api/policy/pull/{chiWenUUID=" + chiWenUUID + "  agenttype:" + agenttype
        + "}:pullPolicy begin");
    if (AuditType.HDFS.getName().equalsIgnoreCase(agenttype)) {
      PolicyVo policyVo = policyService
          .getPolicy(cmpyId, chiWenUUID, agenttype, httpServletRequest);
      policyJsonStr = policyVo.getPolicyjsonStr();
    }
    if (AuditType.HBASE.getName().equalsIgnoreCase(agenttype)) {
      policyJsonStr = hbaseService.getPolicy(cmpyId, chiWenUUID, agenttype, httpServletRequest);
    }
    if (StringUtils.isEmpty(policyJsonStr)) {
      policyJsonStr = "";
    }
    LOG.debug("/api/policy/pull/{chiWenUUID=" + chiWenUUID + "  agenttype:" + agenttype
        + "}:pullPolicy end");
    return policyJsonStr;
  }


  @RequestMapping(value = "/v2/policy/pull/hdfs", method = RequestMethod.GET)
  @ResponseBody
  public ChiWenPolicyPluginVo pullPolicy2(@RequestParam(required = false)  String chiWenUUID, HttpServletRequest httpServletRequest) {
    String cmpyId = "5968802a01cbaa46738eee3d";
    String typeStr = "";
    HbasePolicyVo policyJsonStr = new HbasePolicyVo();

    ChiWenPolicyPluginVo policyVo = policyService
        .getPolicy2(cmpyId, chiWenUUID, "hdfs", httpServletRequest);
    return policyVo;

  }
 
  @RequestMapping(value = "/v2/policy/pull/hbase", method = RequestMethod.GET)
  @ResponseBody
  public HbasePolicyVo pullPolicyHbase(@RequestParam(required = false)  String chiWenUUID, HttpServletRequest httpServletRequest) {
    String cmpyId = "5968802a01cbaa46738eee3d";

    HbasePolicyVo policyVo = hbaseService
        .getPolicy2(cmpyId, chiWenUUID, "hbase", httpServletRequest);
    return policyVo;

  }

  @RequestMapping(value = "/v2/policy/pull/hive", method = RequestMethod.GET)
  @ResponseBody
  public HivePolicyVo pullPolicyHive(@RequestParam(required = false)  String chiWenUUID, HttpServletRequest httpServletRequest) {
    String cmpyId = "5968802a01cbaa46738eee3d";
    HivePolicyVo policyVo = hiveService.getPolicy2(cmpyId, chiWenUUID, "hive", httpServletRequest);
    return policyVo;

  }




  @RequestMapping(value = "/api/policy/save", method = RequestMethod.POST)
  public String save(@Valid PolicyFormVo vo) {
    if (StringUtils.isBlank(vo.getId())) {
      policyService.insert(vo);
    } else {
      //policyService.update(vo);
    }
    return "redirect:list";
  }

//  @RequestMapping(value = "/pull/{chiWenUUID}/{agenttype}", method = RequestMethod.GET)
//  @ResponseBody
//  public String listUser(@PathVariable(name = "chiWenUUID") String chiWenUUID,
//      @PathVariable(name = "agenttype") {
//    String cmpyId = "5968802a01cbaa46738eee3d";
//    String jsonString = hbaseService.getHBaseJson(cmpyId);
//    return jsonString;
//  }

}
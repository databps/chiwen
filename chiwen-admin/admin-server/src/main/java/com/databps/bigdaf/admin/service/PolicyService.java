package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.ChiWenPolicyPluginVo;
import com.databps.bigdaf.admin.vo.PolicyFormVo;
import com.databps.bigdaf.admin.vo.PolicyVo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by lgc on 17-8-8.
 */
public interface PolicyService {

  PolicyVo getPolicy(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest);

  ChiWenPolicyPluginVo getPolicy2(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest);

  void insert(PolicyFormVo vo);

  List<PolicyVo> findAllByName(Pageable pageable,String name);
}

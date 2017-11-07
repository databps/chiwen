package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;

/**
 * Created by lgc on 17-7-20.
 */
public interface ChiWenAuditHandler {
  void logAudit(ChiWenAccessResult result);
}

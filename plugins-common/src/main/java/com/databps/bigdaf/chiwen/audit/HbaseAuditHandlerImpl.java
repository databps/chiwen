package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author merlin
 * @create 2017-11-04 下午4:47
 */
public class HbaseAuditHandlerImpl extends ChiWenDefaultAuditHandler implements HbaseAuditHandler {

  private static final Log LOG = LogFactory.getLog(HbaseAuditHandlerImpl.class);
  static final List<AuthzAuditEvent> _EmptyList = new ArrayList<AuthzAuditEvent>();
  final List<AuthzAuditEvent> _allEvents = new ArrayList<AuthzAuditEvent>();
  // we replace its contents anytime new audit events are generated.
  AuthzAuditEvent _mostRecentEvent = null;
  boolean _superUserOverride = false;

  @Override
  public AuthzAuditEvent getAuthzEvents(ChiWenAccessResult result) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.getAuthzEvents(" + result + ")");
    }

    AuthzAuditEvent event = super.getAuthzEvents(result);
    // first accumulate last set of events and then capture these as the most recent ones
    if (_mostRecentEvent != null) {
      LOG.debug("getAuthzEvents: got one event from default audit handler");
      _allEvents.add(_mostRecentEvent);
    } else {
      LOG.debug("getAuthzEvents: no event produced by default audit handler");
    }
    _mostRecentEvent = event;

    if(LOG.isDebugEnabled()) {
      LOG.debug("==> getAuthzEvents: mostRecentEvent:" + _mostRecentEvent);
    }
    // We return null because we don't want default audit handler to audit anything!
    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.getAuthzEvents(" + result + "): null");
    }
    return null;
  }

  @Override
  public List<AuthzAuditEvent> getCapturedEvents() {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.getCapturedEvents()");
    }

    // construct a new collection since we don't want to lose track of which were the most recent events;
    List<AuthzAuditEvent> result = new ArrayList<AuthzAuditEvent>(_allEvents);
    if (_mostRecentEvent != null) {
      result.add(_mostRecentEvent);
    }
    applySuperUserOverride(result);

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.getAuthzEvents(): count[" + result.size() + "] :result : " + result);
    }
    return result;
  }

  @Override
  public void logAuthzAudits(Collection<AuthzAuditEvent> auditEvents) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.logAuthzAudits(" + auditEvents + ")");
    }

    if(auditEvents != null) {
      for(AuthzAuditEvent auditEvent : auditEvents) {
        logAuthzAudit(auditEvent);
      }
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.logAuthzAudits(" + auditEvents + ")");
    }
  }

  @Override
  public AuthzAuditEvent getAndDiscardMostRecentEvent() {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.getAndDiscardMostRecentEvent():");
    }

    AuthzAuditEvent result = _mostRecentEvent;
    applySuperUserOverride(result);
    _mostRecentEvent = null;

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.getAndDiscardMostRecentEvent(): " + result);
    }
    return result;
  }

  @Override
  public void setMostRecentEvent(AuthzAuditEvent event) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.setMostRecentEvent(" + event + ")");
    }
    _mostRecentEvent = event;
    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.setMostRecentEvent(...)");
    }
  }

  @Override
  public void setSuperUserOverride(boolean override) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.setSuperUserOverride(" + override + ")");
    }

    _superUserOverride = override;

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.setSuperUserOverride(...)");
    }
  }

  void applySuperUserOverride(List<AuthzAuditEvent> events) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.applySuperUserOverride(" + events + ")");
    }

    for (AuthzAuditEvent event : events) {
      applySuperUserOverride(event);
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.applySuperUserOverride(...)");
    }
  }

  void applySuperUserOverride(AuthzAuditEvent event) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> HbaseAuditHandlerImpl.applySuperUserOverride(" + event + ")");
    }
    if (event != null && _superUserOverride) {
      event.setAccessResult("success");
      event.setPolicyId(-1);
    }
    if(LOG.isDebugEnabled()) {
      LOG.debug("<== HbaseAuditHandlerImpl.applySuperUserOverride(...)");
    }
  }
}


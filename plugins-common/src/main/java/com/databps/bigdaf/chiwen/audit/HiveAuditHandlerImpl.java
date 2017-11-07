/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HiveAuditHandlerImpl extends ChiWenDefaultAuditHandler {

	private static final Log LOG = LogFactory.getLog(HiveAuditHandlerImpl.class);
	static final List<AuthzAuditEvent> _EmptyList = new ArrayList<AuthzAuditEvent>();
	final List<AuthzAuditEvent> _allEvents = new ArrayList<AuthzAuditEvent>();
	// we replace its contents anytime new audit events are generated.
	AuthzAuditEvent _mostRecentEvent = null;
	boolean _superUserOverride = false;


	public AuthzAuditEvent getAuthzEvents(ChiWenAccessResult result) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.getAuthzEvents(" + result + ")");
		}

		AuthzAuditEvent event = super.getAuthzEvents(result);
		_mostRecentEvent = event;
		// first accumulate last set of events and then capture these as the most recent ones
		if (_mostRecentEvent != null) {
			LOG.debug("getAuthzEvents: got one event from default audit handler");
			_allEvents.add(_mostRecentEvent);
		} else {
			LOG.debug("getAuthzEvents: no event produced by default audit handler");
		}


		if(LOG.isDebugEnabled()) {
			LOG.debug("==> getAuthzEvents: mostRecentEvent:" + _mostRecentEvent);
		}
		// We return null because we don't want default audit handler to audit anything!
		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.getAuthzEvents(" + result + "): null");
		}
		return event;
	}


	public List<AuthzAuditEvent> getCapturedEvents() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.getCapturedEvents()");
		}

		// construct a new collection since we don't want to lose track of which were the most recent events;
		List<AuthzAuditEvent> result = new ArrayList<AuthzAuditEvent>(_allEvents);
//		if (_mostRecentEvent != null) {
//			result.add(_mostRecentEvent);
//		}
		applySuperUserOverride(result);

		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.getAuthzEvents(): count[" + result.size() + "] :result : " + result);
		}
		return result;
	}


	public AuthzAuditEvent getAndDiscardMostRecentEvent() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.getAndDiscardMostRecentEvent():");
		}

		AuthzAuditEvent result = _mostRecentEvent;
		applySuperUserOverride(result);
		_mostRecentEvent = null;

		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.getAndDiscardMostRecentEvent(): " + result);
		}
		return result;
	}


	public void setMostRecentEvent(AuthzAuditEvent event) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.setMostRecentEvent(" + event + ")");
		}
		_mostRecentEvent = event;
		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.setMostRecentEvent(...)");
		}
	}

	public void setSuperUserOverride(boolean override) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.setSuperUserOverride(" + override + ")");
		}

		_superUserOverride = override;

		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.setSuperUserOverride(...)");
		}
	}

	void applySuperUserOverride(List<AuthzAuditEvent> events) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.applySuperUserOverride(" + events + ")");
		}

		for (AuthzAuditEvent event : events) {
			applySuperUserOverride(event);
		}

		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.applySuperUserOverride(...)");
		}
	}

	void applySuperUserOverride(AuthzAuditEvent event) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> HiveAuditHandlerImpl.applySuperUserOverride(" + event + ")");
		}
		if (event != null && _superUserOverride) {
			event.setAccessResult("success");
			event.setPolicyId(-1);
		}
		if(LOG.isDebugEnabled()) {
			LOG.debug("<== HiveAuditHandlerImpl.applySuperUserOverride(...)");
		}
	}

	public void logAuthzAudits(Collection<AuthzAuditEvent> auditEvents) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> ChiWenDefaultAuditHandler.logAuthzAudits(" + auditEvents + ")");
		}

		if(auditEvents != null) {
			for(AuthzAuditEvent auditEvent : auditEvents) {
				logAuthzAudit(auditEvent);
			}
		}
		if(LOG.isDebugEnabled()) {
			LOG.debug("<== ChiWenDefaultAuditHandler.logAuthzAudits(" + auditEvents + ")");
		}
	}

}

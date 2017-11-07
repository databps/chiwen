package com.databps.bigdaf.chiwen.common;/*
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
/*

package com.databps.bigdaf.chiwen.common;



import java.util.List;
import com.databps.bigdaf.chiwen.model.*;

public interface ServiceStore {
	void init() throws Exception;

	ChiWenServiceDef createServiceDef(ChiWenServiceDef serviceDef) throws Exception;

	ChiWenServiceDef updateServiceDef(ChiWenServiceDef serviceDef) throws Exception;

	void deleteServiceDef(Long id, Boolean forceDelete) throws Exception;

	void updateTagServiceDefForAccessTypes() throws Exception;

	ChiWenServiceDef getServiceDef(Long id) throws Exception;

	ChiWenServiceDef getServiceDefByName(String name) throws Exception;

	List<ChiWenServiceDef> getServiceDefs(SearchFilter filter) throws Exception;

	PList<ChiWenServiceDef> getPaginatedServiceDefs(SearchFilter filter) throws Exception;

	ChiWenService createService(ChiWenService service) throws Exception;

	ChiWenService updateService(ChiWenService service) throws Exception;

	void deleteService(Long id) throws Exception;

	ChiWenService getService(Long id) throws Exception;

	ChiWenService getServiceByName(String name) throws Exception;

	List<ChiWenService> getServices(SearchFilter filter) throws Exception;

	PList<ChiWenService> getPaginatedServices(SearchFilter filter) throws Exception;

	ChiWenPolicy createPolicy(ChiWenPolicy policy) throws Exception;

	ChiWenPolicy updatePolicy(ChiWenPolicy policy) throws Exception;

	void deletePolicy(Long id) throws Exception;

	ChiWenPolicy getPolicy(Long id) throws Exception;

	List<ChiWenPolicy> getPolicies(SearchFilter filter) throws Exception;

	PList<ChiWenPolicy> getPaginatedPolicies(SearchFilter filter) throws Exception;

	List<ChiWenPolicy> getPoliciesByResourceSignature(String serviceName, String policySignature, Boolean isPolicyEnabled) throws Exception;

	List<ChiWenPolicy> getServicePolicies(Long serviceId, SearchFilter filter) throws Exception;

	PList<ChiWenPolicy> getPaginatedServicePolicies(Long serviceId, SearchFilter filter) throws Exception;

	List<ChiWenPolicy> getServicePolicies(String serviceName, SearchFilter filter) throws Exception;

	PList<ChiWenPolicy> getPaginatedServicePolicies(String serviceName, SearchFilter filter) throws Exception;

	ServicePolicies getServicePoliciesIfUpdated(String serviceName, Long lastKnownVersion) throws Exception;

	Long getServicePolicyVersion(String serviceName);

	ServicePolicies getServicePolicies(String serviceName) throws Exception;

	ChiWenPolicy getPolicyFromEventTime(String eventTimeStr, Long policyId);

	void setPopulateExistingBaseFields(Boolean populateExistingBaseFields);

	Boolean getPopulateExistingBaseFields();
}
*/

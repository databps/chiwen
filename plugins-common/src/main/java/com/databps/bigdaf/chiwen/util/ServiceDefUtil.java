package com.databps.bigdaf.chiwen.util;/*
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
 *//*



package com.databps.bigdaf.chiwen.util;

import com.databps.bigdaf.chiwen.common.ChiWenConfiguration;
import com.databps.bigdaf.chiwen.model.ChiWenPolicy;
import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDefUtil {

    public static boolean getOption_enableDenyAndExceptionsInPolicies(ChiWenServiceDef serviceDef) {
        boolean ret = false;

        if(serviceDef != null) {
            boolean enableDenyAndExceptionsInPoliciesHiddenOption = ChiWenConfiguration.getInstance().getBoolean("ranger.servicedef.enableDenyAndExceptionsInPolicies", true);
            boolean defaultValue = enableDenyAndExceptionsInPoliciesHiddenOption || StringUtils.equalsIgnoreCase(serviceDef.getName(), EmbeddedServiceDefsUtil.EMBEDDED_SERVICEDEF_TAG_NAME);

            ret = ServiceDefUtil.getBooleanValue(serviceDef.getOptions(), ChiWenServiceDef.OPTION_ENABLE_DENY_AND_EXCEPTIONS_IN_POLICIES, defaultValue);
        }

        return ret;
    }

    public static ChiWenServiceDef.ChiWenDataMaskTypeDef getDataMaskType(ChiWenServiceDef serviceDef, String typeName) {
        ChiWenServiceDef.ChiWenDataMaskTypeDef ret = null;

        if(serviceDef != null && serviceDef.getDataMaskDef() != null) {
            List<ChiWenServiceDef.ChiWenDataMaskTypeDef> maskTypes = serviceDef.getDataMaskDef().getMaskTypes();

            if(CollectionUtils.isNotEmpty(maskTypes)) {
                for(ChiWenServiceDef.ChiWenDataMaskTypeDef maskType : maskTypes) {
                    if(StringUtils.equals(maskType.getName(), typeName)) {
                        ret = maskType;
                        break;
                    }
                }
            }
        }

        return ret;
    }

    public static ChiWenServiceDef normalize(ChiWenServiceDef serviceDef) {
        normalizeDataMaskDef(serviceDef);
        normalizeRowFilterDef(serviceDef);

        return serviceDef;
    }

    public static ChiWenServiceDef.ChiWenResourceDef getResourceDef(ChiWenServiceDef serviceDef, String resource) {
        ChiWenServiceDef.ChiWenResourceDef ret = null;

        if(serviceDef != null && resource != null && CollectionUtils.isNotEmpty(serviceDef.getResources())) {
            for(ChiWenServiceDef.ChiWenResourceDef resourceDef : serviceDef.getResources()) {
                if(StringUtils.equalsIgnoreCase(resourceDef.getName(), resource)) {
                    ret = resourceDef;
                    break;
                }
            }
        }

        return ret;
    }

    public static Integer getLeafResourceLevel(ChiWenServiceDef serviceDef, Map<String, ChiWenPolicy.ChiWenPolicyResource> policyResource) {
        Integer ret = null;

        if(serviceDef != null && policyResource != null) {
            for(Map.Entry<String, ChiWenPolicy.ChiWenPolicyResource> entry : policyResource.entrySet()) {
                String            resource    = entry.getKey();
                ChiWenResourceDef resourceDef = ServiceDefUtil.getResourceDef(serviceDef, resource);

                if(resourceDef != null && resourceDef.getLevel() != null) {
                    if(ret == null) {
                        ret = resourceDef.getLevel();
                    } else if(ret < resourceDef.getLevel()) {
                        ret = resourceDef.getLevel();
                    }
                }
            }
        }

        return ret;
    }

    public static String getOption(Map<String, String> options, String name, String defaultValue) {
        String ret = options != null && name != null ? options.get(name) : null;

        if(ret == null) {
            ret = defaultValue;
        }

        return ret;
    }

    public static boolean getBooleanOption(Map<String, String> options, String name, boolean defaultValue) {
        String val = getOption(options, name, null);

        return val == null ? defaultValue : Boolean.parseBoolean(val);
    }

    public static char getCharOption(Map<String, String> options, String name, char defaultValue) {
        String val = getOption(options, name, null);

        return StringUtils.isEmpty(val) ? defaultValue : val.charAt(0);
    }

    private static void normalizeDataMaskDef(ChiWenServiceDef serviceDef) {
        if(serviceDef != null && serviceDef.getDataMaskDef() != null) {
            List<ChiWenResourceDef>   dataMaskResources   = serviceDef.getDataMaskDef().getResources();
            List<ChiWenAccessTypeDef> dataMaskAccessTypes = serviceDef.getDataMaskDef().getAccessTypes();

            if(CollectionUtils.isNotEmpty(dataMaskResources)) {
                List<ChiWenResourceDef> resources     = serviceDef.getResources();
                List<ChiWenResourceDef> processedDefs = new ArrayList<ChiWenResourceDef>(dataMaskResources.size());

                for(ChiWenResourceDef dataMaskResource : dataMaskResources) {
                    ChiWenResourceDef processedDef = dataMaskResource;

                    for(ChiWenResourceDef resourceDef : resources) {
                        if(StringUtils.equals(resourceDef.getName(), dataMaskResource.getName())) {
                            processedDef = ServiceDefUtil.mergeResourceDef(resourceDef, dataMaskResource);
                            break;
                        }
                    }

                    processedDefs.add(processedDef);
                }

                serviceDef.getDataMaskDef().setResources(processedDefs);
            }

            if(CollectionUtils.isNotEmpty(dataMaskAccessTypes)) {
                List<ChiWenAccessTypeDef> accessTypes   = serviceDef.getAccessTypes();
                List<ChiWenAccessTypeDef> processedDefs = new ArrayList<ChiWenAccessTypeDef>(accessTypes.size());

                for(ChiWenAccessTypeDef dataMaskAccessType : dataMaskAccessTypes) {
                    ChiWenAccessTypeDef processedDef = dataMaskAccessType;

                    for(ChiWenAccessTypeDef accessType : accessTypes) {
                        if(StringUtils.equals(accessType.getName(), dataMaskAccessType.getName())) {
                            processedDef = ServiceDefUtil.mergeAccessTypeDef(accessType, dataMaskAccessType);
                            break;
                        }
                    }

                    processedDefs.add(processedDef);
                }

                serviceDef.getDataMaskDef().setAccessTypes(processedDefs);
            }
        }
    }

    private static void normalizeRowFilterDef(ChiWenServiceDef serviceDef) {
        if(serviceDef != null && serviceDef.getRowFilterDef() != null) {
            List<ChiWenResourceDef>   rowFilterResources   = serviceDef.getRowFilterDef().getResources();
            List<ChiWenAccessTypeDef> rowFilterAccessTypes = serviceDef.getRowFilterDef().getAccessTypes();

            if(CollectionUtils.isNotEmpty(rowFilterResources)) {
                List<ChiWenResourceDef> resources     = serviceDef.getResources();
                List<ChiWenResourceDef> processedDefs = new ArrayList<ChiWenResourceDef>(rowFilterResources.size());

                for(ChiWenResourceDef rowFilterResource : rowFilterResources) {
                    ChiWenResourceDef processedDef = rowFilterResource;

                    for(ChiWenResourceDef resourceDef : resources) {
                        if(StringUtils.equals(resourceDef.getName(), rowFilterResource.getName())) {
                            processedDef = ServiceDefUtil.mergeResourceDef(resourceDef, rowFilterResource);
                            break;
                        }
                    }

                    processedDefs.add(processedDef);
                }

                serviceDef.getRowFilterDef().setResources(processedDefs);
            }

            if(CollectionUtils.isNotEmpty(rowFilterAccessTypes)) {
                List<ChiWenAccessTypeDef> accessTypes   = serviceDef.getAccessTypes();
                List<ChiWenAccessTypeDef> processedDefs = new ArrayList<ChiWenAccessTypeDef>(accessTypes.size());

                for(ChiWenAccessTypeDef rowFilterAccessType : rowFilterAccessTypes) {
                    ChiWenAccessTypeDef processedDef = rowFilterAccessType;

                    for(ChiWenAccessTypeDef accessType : accessTypes) {
                        if(StringUtils.equals(accessType.getName(), rowFilterAccessType.getName())) {
                            processedDef = ServiceDefUtil.mergeAccessTypeDef(accessType, rowFilterAccessType);
                            break;
                        }
                    }

                    processedDefs.add(processedDef);
                }

                serviceDef.getRowFilterDef().setAccessTypes(processedDefs);
            }
        }
    }

    private static ChiWenResourceDef mergeResourceDef(ChiWenServiceDef.ChiWenResourceDef base, ChiWenServiceDef.ChiWenResourceDef delta) {
        ChiWenServiceDef.ChiWenResourceDef ret = new ChiWenServiceDef.ChiWenResourceDef(base);

        // retain base values for: itemId, name, type, level, parent, mandatory, lookupSupported

        if(delta.getRecursiveSupported() != null)
            ret.setRecursiveSupported(delta.getRecursiveSupported());

        if(delta.getExcludesSupported() != null)
            ret.setExcludesSupported(delta.getExcludesSupported());

        if(StringUtils.isNotEmpty(delta.getMatcher()))
            ret.setMatcher(delta.getMatcher());

        if(MapUtils.isNotEmpty(delta.getMatcherOptions())) {
            if(ret.getMatcherOptions() == null) {
                ret.setMatcherOptions(new HashMap<String, String>());
            }

            for(Map.Entry<String, String> e : delta.getMatcherOptions().entrySet()) {
                ret.getMatcherOptions().put(e.getKey(), e.getValue());
            }
        }

        if(StringUtils.isNotEmpty(delta.getValidationRegEx()))
            ret.setValidationRegEx(delta.getValidationRegEx());

        if(StringUtils.isNotEmpty(delta.getValidationMessage()))
            ret.setValidationMessage(delta.getValidationMessage());

        if(StringUtils.isNotEmpty(delta.getUiHint()))
            ret.setUiHint(delta.getUiHint());

        if(StringUtils.isNotEmpty(delta.getLabel()))
            ret.setLabel(delta.getLabel());

        if(StringUtils.isNotEmpty(delta.getDescription()))
            ret.setDescription(delta.getDescription());

        if(StringUtils.isNotEmpty(delta.getRbKeyLabel()))
            ret.setRbKeyLabel(delta.getRbKeyLabel());

        if(StringUtils.isNotEmpty(delta.getRbKeyDescription()))
            ret.setRbKeyDescription(delta.getRbKeyDescription());

        if(StringUtils.isNotEmpty(delta.getRbKeyValidationMessage()))
            ret.setRbKeyValidationMessage(delta.getRbKeyValidationMessage());

        return ret;
    }

    private static ChiWenServiceDef.ChiWenAccessTypeDef mergeAccessTypeDef(ChiWenServiceDef.ChiWenAccessTypeDef base, ChiWenServiceDef.ChiWenAccessTypeDef delta) {
        ChiWenServiceDef.ChiWenAccessTypeDef ret = new ChiWenServiceDef.ChiWenAccessTypeDef(base);

        // retain base values for: itemId, name, impliedGrants

        if(StringUtils.isNotEmpty(delta.getLabel()))
            ret.setLabel(delta.getLabel());

        if(StringUtils.isNotEmpty(delta.getRbKeyLabel()))
            ret.setRbKeyLabel(delta.getRbKeyLabel());

        return ret;
    }

    private static boolean getBooleanValue(Map<String, String> map, String elementName, boolean defaultValue) {
        boolean ret = defaultValue;

        if(MapUtils.isNotEmpty(map) && map.containsKey(elementName)) {
            String elementValue = map.get(elementName);

            if(StringUtils.isNotEmpty(elementValue)) {
                ret = Boolean.valueOf(elementValue.toString());
            }
        }

        return ret;
    }

}




*/

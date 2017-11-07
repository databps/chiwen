package com.databps.bigdaf.chiwen.model;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChiWenServiceDef extends ChiWenBaseModelObject implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public static final String OPTION_ENABLE_DENY_AND_EXCEPTIONS_IN_POLICIES = "enableDenyAndExceptionsInPolicies";

    private String                         name;
    private String                         implClass;
    private String                         label;
    private String                         description;
    private String                         rbKeyLabel;
    private String                         rbKeyDescription;
    private Map<String, String> options;
    private List<ChiWenServiceConfigDef> configs;
    private List<ChiWenResourceDef>        resources;
    private List<ChiWenAccessTypeDef>      accessTypes;
    private List<ChiWenPolicyConditionDef> policyConditions;
    private List<ChiWenContextEnricherDef> contextEnrichers;
    private List<ChiWenEnumDef>            enums;
    private ChiWenDataMaskTypeDef              dataMaskDef;
    private ChiWenRowFilterDef             rowFilterDef;

    public ChiWenServiceDef() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public ChiWenServiceDef(String name, String implClass, String label, String description, Map<String, String> options, List<ChiWenServiceConfigDef> configs, List<ChiWenResourceDef> resources, List<ChiWenAccessTypeDef> accessTypes, List<ChiWenPolicyConditionDef> policyConditions, List<ChiWenContextEnricherDef> contextEnrichers, List<ChiWenEnumDef> enums) {
        this(name, implClass, label, description, options, configs, resources, accessTypes, policyConditions, contextEnrichers, enums, null, null);
    }

    /**
     * @param name
     * @param implClass
     * @param label
     * @param description
     * @param options
     * @param configs
     * @param resources
     * @param accessTypes
     * @param policyConditions
     * @param contextEnrichers
     * @param dataMaskDef
     * @param enums
     */
    public ChiWenServiceDef(String name, String implClass, String label, String description, Map<String, String> options, List<ChiWenServiceConfigDef> configs, List<ChiWenResourceDef> resources, List<ChiWenAccessTypeDef> accessTypes, List<ChiWenPolicyConditionDef> policyConditions, List<ChiWenContextEnricherDef> contextEnrichers, List<ChiWenEnumDef> enums, ChiWenDataMaskTypeDef dataMaskDef, ChiWenRowFilterDef rowFilterDef) {
        super();

        setName(name);
        setImplClass(implClass);
        setLabel(label);
        setDescription(description);
        setConfigs(configs);
        setOptions(options);
        setResources(resources);
        setAccessTypes(accessTypes);
        setPolicyConditions(policyConditions);
        setContextEnrichers(contextEnrichers);
        setEnums(enums);
        setDataMaskDef(dataMaskDef);
        setRowFilterDef(rowFilterDef);
    }

    /**
     * @param other
     */
    public void updateFrom(ChiWenServiceDef other) {
        super.updateFrom(other);

        setName(other.getName());
        setImplClass(other.getImplClass());
        setLabel(other.getLabel());
        setDescription(other.getDescription());
        setConfigs(other.getConfigs());
        setOptions(other.getOptions());
        setResources(other.getResources());
        setAccessTypes(other.getAccessTypes());
        setPolicyConditions(other.getPolicyConditions());
        setEnums(other.getEnums());
        setDataMaskDef(other.getDataMaskDef());
        setRowFilterDef(other.getRowFilterDef());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the implClass
     */
    public String getImplClass() {
        return implClass;
    }

    /**
     * @param implClass the implClass to set
     */
    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rbKeyLabel
     */
    public String getRbKeyLabel() {
        return rbKeyLabel;
    }

    /**
     * @param rbKeyLabel the rbKeyLabel to set
     */
    public void setRbKeyLabel(String rbKeyLabel) {
        this.rbKeyLabel = rbKeyLabel;
    }

    /**
     * @return the rbKeyDescription
     */
    public String getRbKeyDescription() {
        return rbKeyDescription;
    }

    /**
     * @param rbKeyDescription the rbKeyDescription to set
     */
    public void setRbKeyDescription(String rbKeyDescription) {
        this.rbKeyDescription = rbKeyDescription;
    }

    /**
     * @return the configs
     */
    public List<ChiWenServiceConfigDef> getConfigs() {
        return configs;
    }

    /**
     * @param configs the configs to set
     */
    public void setConfigs(List<ChiWenServiceConfigDef> configs) {
        if(this.configs == null) {
            this.configs = new ArrayList<>();
        } else

        if(this.configs == configs) {
            return;
        }

        this.configs.clear();

        if(configs != null) {
            this.configs.addAll(configs);
        }
    }

    /**
     * @return the options
     */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(Map<String, String> options) {
        if(this.options == null) {
            this.options = new HashMap<>();
        } else if(this.options == options) {
            return;
        }

        this.options.clear();

        if(options != null) {
            for(Map.Entry<String, String> entry : options.entrySet()) {
                this.options.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @return the resources
     */
    public List<ChiWenResourceDef> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(List<ChiWenResourceDef> resources) {
        if(this.resources == null) {
            this.resources = new ArrayList<>();
        }

        if(this.resources == resources) {
            return;
        }

        this.resources.clear();

        if(resources != null) {
            this.resources.addAll(resources);
        }
    }

    /**
     * @return the accessTypes
     */
    public List<ChiWenAccessTypeDef> getAccessTypes() {
        return accessTypes;
    }

    /**
     * @param accessTypes the accessTypes to set
     */
    public void setAccessTypes(List<ChiWenAccessTypeDef> accessTypes) {
        if(this.accessTypes == null) {
            this.accessTypes = new ArrayList<>();
        }

        if(this.accessTypes == accessTypes) {
            return;
        }

        this.accessTypes.clear();

        if(accessTypes != null) {
            this.accessTypes.addAll(accessTypes);
        }
    }

    /**
     * @return the policyConditions
     */
    public List<ChiWenPolicyConditionDef> getPolicyConditions() {
        return policyConditions;
    }

    /**
     * @param policyConditions the policyConditions to set
     */
    public void setPolicyConditions(List<ChiWenPolicyConditionDef> policyConditions) {
        if(this.policyConditions == null) {
            this.policyConditions = new ArrayList<>();
        }

        if(this.policyConditions == policyConditions) {
            return;
        }

        this.policyConditions.clear();

        if(policyConditions != null) {
            this.policyConditions.addAll(policyConditions);
        }
    }

    /**
     * @return the contextEnrichers
     */
    public List<ChiWenContextEnricherDef> getContextEnrichers() {
        return contextEnrichers;
    }

    /**
     * @param contextEnrichers the contextEnrichers to set
     */
    public void setContextEnrichers(List<ChiWenContextEnricherDef> contextEnrichers) {
        if(this.contextEnrichers == null) {
            this.contextEnrichers = new ArrayList<>();
        }

        if(this.contextEnrichers == contextEnrichers) {
            return;
        }

        this.contextEnrichers.clear();

        if(contextEnrichers != null) {
            this.contextEnrichers.addAll(contextEnrichers);
        }
    }

    /**
     * @return the enums
     */
    public List<ChiWenEnumDef> getEnums() {
        return enums;
    }

    /**
     * @param enums the enums to set
     */
    public void setEnums(List<ChiWenEnumDef> enums) {
        if(this.enums == null) {
            this.enums = new ArrayList<>();
        }

        if(this.enums == enums) {
            return;
        }

        this.enums.clear();

        if(enums != null) {
            this.enums.addAll(enums);
        }
    }

    public ChiWenDataMaskTypeDef getDataMaskDef() {
        return dataMaskDef;
    }

    public void setDataMaskDef(ChiWenDataMaskTypeDef dataMaskDef) {
        this.dataMaskDef = dataMaskDef == null ? new ChiWenDataMaskTypeDef() : dataMaskDef;
    }

    public ChiWenRowFilterDef getRowFilterDef() {
        return rowFilterDef;
    }

    public void setRowFilterDef(ChiWenRowFilterDef rowFilterDef) {
        this.rowFilterDef = rowFilterDef == null ? new ChiWenRowFilterDef() : rowFilterDef;
    }

    @Override
    public String toString( ) {
        StringBuilder sb = new StringBuilder();

        toString(sb);

        return sb.toString();
    }

    public StringBuilder toString(StringBuilder sb) {
        sb.append("ChiWenServiceDef={");

        super.toString(sb);

        sb.append("name={").append(name).append("} ");
        sb.append("implClass={").append(implClass).append("} ");
        sb.append("label={").append(label).append("} ");
        sb.append("description={").append(description).append("} ");
        sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");
        sb.append("rbKeyDescription={").append(rbKeyDescription).append("} ");

        sb.append("options={");
        if(options != null) {
            for(Map.Entry<String, String> entry : options.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
            }
        }
        sb.append("} ");

        sb.append("configs={");
        if(configs != null) {
            for(ChiWenServiceConfigDef config : configs) {
                if(config != null) {
                    config.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("resources={");
        if(resources != null) {
            for(ChiWenResourceDef resource : resources) {
                if(resource != null) {
                    resource.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("accessTypes={");
        if(accessTypes != null) {
            for(ChiWenAccessTypeDef accessType : accessTypes) {
                if(accessType != null) {
                    accessType.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("policyConditions={");
        if(policyConditions != null) {
            for(ChiWenPolicyConditionDef policyCondition : policyConditions) {
                if(policyCondition != null) {
                    policyCondition.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("contextEnrichers={");
        if(contextEnrichers != null) {
            for(ChiWenContextEnricherDef contextEnricher : contextEnrichers) {
                if(contextEnricher != null) {
                    contextEnricher.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("enums={");
        if(enums != null) {
            for(ChiWenEnumDef e : enums) {
                if(e != null) {
                    e.toString(sb);
                }
            }
        }
        sb.append("} ");

        sb.append("dataMaskDef={");
        if(dataMaskDef != null) {
            dataMaskDef.toString(sb);
        }
        sb.append("} ");

        sb.append("rowFilterDef={");
        if(rowFilterDef != null) {
            rowFilterDef.toString(sb);
        }
        sb.append("} ");

        sb.append("}");

        return sb;
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenEnumDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long                       itemId;
        private String                     name;
        private List<ChiWenEnumElementDef> elements;
        private Integer                    defaultIndex;

        public ChiWenEnumDef() {
            this(null, null, null, null);
        }

        public ChiWenEnumDef(Long itemId, String name, List<ChiWenEnumElementDef> elements, Integer defaultIndex) {
            setItemId(itemId);
            setName(name);
            setElements(elements);
            setDefaultIndex(defaultIndex);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the elements
         */
        public List<ChiWenEnumElementDef> getElements() {
            return elements;
        }

        /**
         * @param elements the elements to set
         */
        public void setElements(List<ChiWenEnumElementDef> elements) {
            if(this.elements == null) {
                this.elements = new ArrayList<>();
            }

            if(this.elements == elements) {
                return;
            }

            this.elements.clear();

            if(elements != null) {
                this.elements.addAll(elements);
            }
        }

        /**
         * @return the defaultIndex
         */
        public Integer getDefaultIndex() {
            return defaultIndex;
        }

        /**
         * @param defaultIndex the defaultIndex to set
         */
        public void setDefaultIndex(Integer defaultIndex) {
            this.defaultIndex = (defaultIndex != null && this.elements.size() > defaultIndex) ? defaultIndex : 0;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenEnumDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("elements={");
            if(elements != null) {
                for(ChiWenEnumElementDef element : elements) {
                    if(element != null) {
                        element.toString(sb);
                    }
                }
            }
            sb.append("} ");
            sb.append("defaultIndex={").append(defaultIndex).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result
                    + ((defaultIndex == null) ? 0 : defaultIndex.hashCode());
            result = prime * result
                    + ((elements == null) ? 0 : elements.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenEnumDef other = (ChiWenEnumDef) obj;
            if (itemId == null) {
                if (other.itemId != null)
                    return false;
            } else if (other.itemId == null || !itemId.equals(other.itemId))
                return false;

            if (defaultIndex == null) {
                if (other.defaultIndex != null)
                    return false;
            } else if (!defaultIndex.equals(other.defaultIndex))
                return false;
            if (elements == null) {
                if (other.elements != null)
                    return false;
            } else if (!elements.equals(other.elements))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenEnumElementDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long   itemId;
        private String name;
        private String label;
        private String rbKeyLabel;

        public ChiWenEnumElementDef() {
            this(null, null, null, null);
        }

        public ChiWenEnumElementDef(Long itemId, String name, String label, String rbKeyLabel) {
            setItemId(itemId);
            setName(name);
            setLabel(label);
            setRbKeyLabel(rbKeyLabel);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the rbKeyLabel
         */
        public String getRbKeyLabel() {
            return rbKeyLabel;
        }

        /**
         * @param rbKeyLabel the rbKeyLabel to set
         */
        public void setRbKeyLabel(String rbKeyLabel) {
            this.rbKeyLabel = rbKeyLabel;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenEnumElementDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("label={").append(label).append("} ");
            sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result
                    + ((rbKeyLabel == null) ? 0 : rbKeyLabel.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenEnumElementDef other = (ChiWenEnumElementDef) obj;
            if (itemId == null) {
                if (other.itemId != null) {
                    return false;
                }
            } else if (other.itemId == null || !itemId.equals(other.itemId)) {
                return false;
            }

            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (rbKeyLabel == null) {
                if (other.rbKeyLabel != null)
                    return false;
            } else if (!rbKeyLabel.equals(other.rbKeyLabel))
                return false;
            return true;
        }
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenServiceConfigDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long    itemId;
        private String  name;
        private String  type;
        private String  subType;
        private Boolean mandatory;
        private String  defaultValue;
        private String  validationRegEx;
        private String  validationMessage;
        private String  uiHint;
        private String  label;
        private String  description;
        private String  rbKeyLabel;
        private String  rbKeyDescription;
        private String  rbKeyValidationMessage;

        public ChiWenServiceConfigDef() {
            this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        }

        public ChiWenServiceConfigDef(Long itemId, String name, String type, String subType, Boolean mandatory, String defaultValue, String validationRegEx, String validationMessage, String uiHint, String label, String description, String rbKeyLabel, String rbKeyDescription, String rbKeyValidationMessage) {
            setItemId(itemId);
            setName(name);
            setType(type);
            setSubType(subType);
            setMandatory(mandatory);
            setDefaultValue(defaultValue);
            setValidationRegEx(validationRegEx);
            setValidationMessage(validationMessage);
            setUiHint(uiHint);
            setLabel(label);
            setDescription(description);
            setRbKeyLabel(rbKeyLabel);
            setRbKeyDescription(rbKeyDescription);
            setRbKeyValidationMessage(rbKeyValidationMessage);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the subType
         */
        public String getSubType() {
            return subType;
        }

        /**
         * @param subType the subType to set
         */
        public void setSubType(String subType) {
            this.subType = subType;
        }

        /**
         * @return the mandatory
         */
        public Boolean getMandatory() {
            return mandatory;
        }

        /**
         * @param mandatory the mandatory to set
         */
        public void setMandatory(Boolean mandatory) {
            this.mandatory = mandatory == null ? Boolean.FALSE : mandatory;
        }

        /**
         * @return the defaultValue
         */
        public String getDefaultValue() {
            return defaultValue;
        }

        /**
         * @param defaultValue the defaultValue to set
         */
        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        /**
         * @return the validationRegEx
         */
        public String getValidationRegEx() {
            return validationRegEx;
        }

        /**
         * @param validationRegEx the validationRegEx to set
         */
        public void setValidationRegEx(String validationRegEx) {
            this.validationRegEx = validationRegEx;
        }

        /**
         * @return the validationMessage
         */
        public String getValidationMessage() {
            return validationMessage;
        }

        /**
         * @param validationMessage the validationMessage to set
         */
        public void setValidationMessage(String validationMessage) {
            this.validationMessage = validationMessage;
        }

        /**
         * @return the uiHint
         */
        public String getUiHint() {
            return uiHint;
        }

        /**
         * @param uiHint the uiHint to set
         */
        public void setUiHint(String uiHint) {
            this.uiHint = uiHint;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the rbKeyLabel
         */
        public String getRbKeyLabel() {
            return rbKeyLabel;
        }

        /**
         * @param rbKeyLabel the rbKeyLabel to set
         */
        public void setRbKeyLabel(String rbKeyLabel) {
            this.rbKeyLabel = rbKeyLabel;
        }

        /**
         * @return the rbKeyDescription
         */
        public String getRbKeyDescription() {
            return rbKeyDescription;
        }

        /**
         * @param rbKeyDescription the rbKeyDescription to set
         */
        public void setRbKeyDescription(String rbKeyDescription) {
            this.rbKeyDescription = rbKeyDescription;
        }

        /**
         * @return the rbKeyValidationMessage
         */
        public String getRbKeyValidationMessage() {
            return rbKeyValidationMessage;
        }

        /**
         * @param rbKeyValidationMessage the rbKeyValidationMessage to set
         */
        public void setRbKeyValidationMessage(String rbKeyValidationMessage) {
            this.rbKeyValidationMessage = rbKeyValidationMessage;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenServiceConfigDef={");
            sb.append("itemId={").append(name).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("type={").append(type).append("} ");
            sb.append("subType={").append(subType).append("} ");
            sb.append("mandatory={").append(mandatory).append("} ");
            sb.append("defaultValue={").append(defaultValue).append("} ");
            sb.append("validationRegEx={").append(validationRegEx).append("} ");
            sb.append("validationMessage={").append(validationMessage).append("} ");
            sb.append("uiHint={").append(uiHint).append("} ");
            sb.append("label={").append(label).append("} ");
            sb.append("description={").append(description).append("} ");
            sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");
            sb.append("rbKeyDescription={").append(rbKeyDescription).append("} ");
            sb.append("rbKeyValidationMessage={").append(rbKeyValidationMessage).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((defaultValue == null) ? 0 : defaultValue.hashCode());
            result = prime * result
                    + ((description == null) ? 0 : description.hashCode());
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            result = prime * result
                    + ((mandatory == null) ? 0 : mandatory.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime
                    * result
                    + ((rbKeyDescription == null) ? 0 : rbKeyDescription
                    .hashCode());
            result = prime * result
                    + ((rbKeyLabel == null) ? 0 : rbKeyLabel.hashCode());
            result = prime
                    * result
                    + ((rbKeyValidationMessage == null) ? 0
                    : rbKeyValidationMessage.hashCode());
            result = prime * result
                    + ((subType == null) ? 0 : subType.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            result = prime * result
                    + ((uiHint == null) ? 0 : uiHint.hashCode());
            result = prime
                    * result
                    + ((validationMessage == null) ? 0 : validationMessage
                    .hashCode());
            result = prime
                    * result
                    + ((validationRegEx == null) ? 0 : validationRegEx
                    .hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenServiceConfigDef other = (ChiWenServiceConfigDef) obj;
            if (defaultValue == null) {
                if (other.defaultValue != null)
                    return false;
            } else if (!defaultValue.equals(other.defaultValue))
                return false;
            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            if (mandatory == null) {
                if (other.mandatory != null)
                    return false;
            } else if (!mandatory.equals(other.mandatory))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (rbKeyDescription == null) {
                if (other.rbKeyDescription != null)
                    return false;
            } else if (!rbKeyDescription.equals(other.rbKeyDescription))
                return false;
            if (rbKeyLabel == null) {
                if (other.rbKeyLabel != null)
                    return false;
            } else if (!rbKeyLabel.equals(other.rbKeyLabel))
                return false;
            if (rbKeyValidationMessage == null) {
                if (other.rbKeyValidationMessage != null)
                    return false;
            } else if (!rbKeyValidationMessage
                    .equals(other.rbKeyValidationMessage))
                return false;
            if (subType == null) {
                if (other.subType != null)
                    return false;
            } else if (!subType.equals(other.subType))
                return false;
            if (type == null) {
                if (other.type != null)
                    return false;
            } else if (!type.equals(other.type))
                return false;
            if (uiHint == null) {
                if (other.uiHint != null)
                    return false;
            } else if (!uiHint.equals(other.uiHint))
                return false;
            if (validationMessage == null) {
                if (other.validationMessage != null)
                    return false;
            } else if (!validationMessage.equals(other.validationMessage))
                return false;
            if (validationRegEx == null) {
                if (other.validationRegEx != null)
                    return false;
            } else if (!validationRegEx.equals(other.validationRegEx))
                return false;
            return true;
        }
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenResourceDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long                itemId;
        private String              name;
        private String              type;
        private Integer             level;
        private String              parent;
        private Boolean             mandatory;
        private Boolean             lookupSupported;
        private Boolean             recursiveSupported;
        private Boolean             excludesSupported;
        private String              matcher;
        private Map<String, String> matcherOptions;
        private String              validationRegEx;
        private String              validationMessage;
        private String              uiHint;
        private String              label;
        private String              description;
        private String              rbKeyLabel;
        private String              rbKeyDescription;
        private String              rbKeyValidationMessage;

        public ChiWenResourceDef() {
            this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        }

        public ChiWenResourceDef(ChiWenResourceDef other) {
            setItemId(other.getItemId());
            setName(other.getName());
            setType(other.getType());
            setLevel(other.getLevel());
            setParent(other.getParent());
            setMandatory(other.getMandatory());
            setLookupSupported(other.getLookupSupported());
            setRecursiveSupported(other.getRecursiveSupported());
            setExcludesSupported(other.getExcludesSupported());
            setMatcher(other.getMatcher());
            setMatcherOptions(other.getMatcherOptions());
            setValidationRegEx(other.getValidationRegEx());
            setValidationMessage(other.getValidationMessage());
            setUiHint(other.getUiHint());
            setLabel(other.getLabel());
            setDescription(other.getDescription());
            setRbKeyLabel(other.getRbKeyLabel());
            setRbKeyDescription(other.getRbKeyDescription());
            setRbKeyValidationMessage(other.getRbKeyValidationMessage());
        }

        public ChiWenResourceDef(Long itemId, String name, String type, Integer level, String parent, Boolean mandatory, Boolean lookupSupported, Boolean recursiveSupported, Boolean excludesSupported, String matcher, Map<String, String> matcherOptions, String validationRegEx, String validationMessage, String uiHint, String label, String description, String rbKeyLabel, String rbKeyDescription, String rbKeyValidationMessage) {
            setItemId(itemId);
            setName(name);
            setType(type);
            setLevel(level);
            setParent(parent);
            setMandatory(mandatory);
            setLookupSupported(lookupSupported);
            setRecursiveSupported(recursiveSupported);
            setExcludesSupported(excludesSupported);
            setMatcher(matcher);
            setMatcherOptions(matcherOptions);
            setValidationRegEx(validationRegEx);
            setValidationMessage(validationMessage);
            setUiHint(uiHint);
            setLabel(label);
            setDescription(description);
            setRbKeyLabel(rbKeyLabel);
            setRbKeyDescription(rbKeyDescription);
            setRbKeyValidationMessage(rbKeyValidationMessage);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return the level
         */
        public Integer getLevel() {
            return level;
        }

        /**
         * @param level the level to set
         */
        public void setLevel(Integer level) {
            this.level = level == null ? 1 : level;
        }

        /**
         * @return the parent
         */
        public String getParent() {
            return parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(String parent) {
            this.parent = parent;
        }

        /**
         * @return the mandatory
         */
        public Boolean getMandatory() {
            return mandatory;
        }

        /**
         * @param mandatory the mandatory to set
         */
        public void setMandatory(Boolean mandatory) {
            this.mandatory = mandatory == null ? Boolean.FALSE : mandatory;
        }

        /**
         * @return the lookupSupported
         */
        public Boolean getLookupSupported() {
            return lookupSupported;
        }

        /**
         * @param lookupSupported the lookupSupported to set
         */
        public void setLookupSupported(Boolean lookupSupported) {
            this.lookupSupported = lookupSupported == null ? Boolean.FALSE : lookupSupported;
        }

        /**
         * @return the recursiveSupported
         */
        public Boolean getRecursiveSupported() {
            return recursiveSupported;
        }

        /**
         * @param recursiveSupported the recursiveSupported to set
         */
        public void setRecursiveSupported(Boolean recursiveSupported) {
            this.recursiveSupported = recursiveSupported == null ? Boolean.FALSE : recursiveSupported;
        }

        /**
         * @return the excludesSupported
         */
        public Boolean getExcludesSupported() {
            return excludesSupported;
        }

        /**
         * @param excludesSupported the excludesSupported to set
         */
        public void setExcludesSupported(Boolean excludesSupported) {
            this.excludesSupported = excludesSupported == null ? Boolean.FALSE : excludesSupported;
        }

        /**
         * @return the matcher
         */
        public String getMatcher() {
            return matcher;
        }

        /**
         * @param matcher the matcher to set
         */
        public void setMatcher(String matcher) {
            this.matcher = matcher;
        }

        /**
         * @return the matcherOptions
         */
        public Map<String, String> getMatcherOptions() {
            return matcherOptions;
        }

        /**
         * @param matcherOptions the matcherOptions to set
         */
        public void setMatcherOptions(Map<String, String> matcherOptions) {
            this.matcherOptions = matcherOptions == null ? new HashMap<String, String>() : new HashMap<String, String>(matcherOptions);
        }

        /**
         * @return the validationRegEx
         */
        public String getValidationRegEx() {
            return validationRegEx;
        }

        /**
         * @param validationRegEx the validationRegEx to set
         */
        public void setValidationRegEx(String validationRegEx) {
            this.validationRegEx = validationRegEx;
        }

        /**
         * @return the validationMessage
         */
        public String getValidationMessage() {
            return validationMessage;
        }

        /**
         * @param validationMessage the validationMessage to set
         */
        public void setValidationMessage(String validationMessage) {
            this.validationMessage = validationMessage;
        }

        /**
         * @return the uiHint
         */
        public String getUiHint() {
            return uiHint;
        }

        /**
         * @param uiHint the uiHint to set
         */
        public void setUiHint(String uiHint) {
            this.uiHint = uiHint;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the rbKeyLabel
         */
        public String getRbKeyLabel() {
            return rbKeyLabel;
        }

        /**
         * @param rbKeyLabel the rbKeyLabel to set
         */
        public void setRbKeyLabel(String rbKeyLabel) {
            this.rbKeyLabel = rbKeyLabel;
        }

        /**
         * @return the rbKeyDescription
         */
        public String getRbKeyDescription() {
            return rbKeyDescription;
        }

        /**
         * @param rbKeyDescription the rbKeyDescription to set
         */
        public void setRbKeyDescription(String rbKeyDescription) {
            this.rbKeyDescription = rbKeyDescription;
        }

        /**
         * @return the rbKeyValidationMessage
         */
        public String getRbKeyValidationMessage() {
            return rbKeyValidationMessage;
        }

        /**
         * @param rbKeyValidationMessage the rbKeyValidationMessage to set
         */
        public void setRbKeyValidationMessage(String rbKeyValidationMessage) {
            this.rbKeyValidationMessage = rbKeyValidationMessage;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenResourceDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("type={").append(type).append("} ");
            sb.append("level={").append(level).append("} ");
            sb.append("parent={").append(parent).append("} ");
            sb.append("mandatory={").append(mandatory).append("} ");
            sb.append("lookupSupported={").append(lookupSupported).append("} ");
            sb.append("recursiveSupported={").append(recursiveSupported).append("} ");
            sb.append("excludesSupported={").append(excludesSupported).append("} ");
            sb.append("matcher={").append(matcher).append("} ");
            sb.append("matcherOptions={").append(matcherOptions).append("} ");
            sb.append("validationRegEx={").append(validationRegEx).append("} ");
            sb.append("validationMessage={").append(validationMessage).append("} ");
            sb.append("uiHint={").append(uiHint).append("} ");
            sb.append("label={").append(label).append("} ");
            sb.append("description={").append(description).append("} ");
            sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");
            sb.append("rbKeyDescription={").append(rbKeyDescription).append("} ");
            sb.append("rbKeyValidationMessage={").append(rbKeyValidationMessage).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((description == null) ? 0 : description.hashCode());
            result = prime
                    * result
                    + ((excludesSupported == null) ? 0 : excludesSupported
                    .hashCode());
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            result = prime * result + ((level == null) ? 0 : level.hashCode());
            result = prime
                    * result
                    + ((lookupSupported == null) ? 0 : lookupSupported
                    .hashCode());
            result = prime * result
                    + ((mandatory == null) ? 0 : mandatory.hashCode());
            result = prime * result
                    + ((matcher == null) ? 0 : matcher.hashCode());
            result = prime
                    * result
                    + ((matcherOptions == null) ? 0 : matcherOptions.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result
                    + ((parent == null) ? 0 : parent.hashCode());
            result = prime
                    * result
                    + ((rbKeyDescription == null) ? 0 : rbKeyDescription
                    .hashCode());
            result = prime * result
                    + ((rbKeyLabel == null) ? 0 : rbKeyLabel.hashCode());
            result = prime
                    * result
                    + ((rbKeyValidationMessage == null) ? 0
                    : rbKeyValidationMessage.hashCode());
            result = prime
                    * result
                    + ((recursiveSupported == null) ? 0 : recursiveSupported
                    .hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            result = prime * result
                    + ((uiHint == null) ? 0 : uiHint.hashCode());
            result = prime
                    * result
                    + ((validationMessage == null) ? 0 : validationMessage
                    .hashCode());
            result = prime
                    * result
                    + ((validationRegEx == null) ? 0 : validationRegEx
                    .hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenResourceDef other = (ChiWenResourceDef) obj;
            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (excludesSupported == null) {
                if (other.excludesSupported != null)
                    return false;
            } else if (!excludesSupported.equals(other.excludesSupported))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            if (level == null) {
                if (other.level != null)
                    return false;
            } else if (!level.equals(other.level))
                return false;
            if (lookupSupported == null) {
                if (other.lookupSupported != null)
                    return false;
            } else if (!lookupSupported.equals(other.lookupSupported))
                return false;
            if (mandatory == null) {
                if (other.mandatory != null)
                    return false;
            } else if (!mandatory.equals(other.mandatory))
                return false;
            if (matcher == null) {
                if (other.matcher != null)
                    return false;
            } else if (!matcher.equals(other.matcher))
                return false;
            if (matcherOptions == null) {
                if (other.matcherOptions != null)
                    return false;
            } else if (!matcherOptions.equals(other.matcherOptions))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (parent == null) {
                if (other.parent != null)
                    return false;
            } else if (!parent.equals(other.parent))
                return false;
            if (rbKeyDescription == null) {
                if (other.rbKeyDescription != null)
                    return false;
            } else if (!rbKeyDescription.equals(other.rbKeyDescription))
                return false;
            if (rbKeyLabel == null) {
                if (other.rbKeyLabel != null)
                    return false;
            } else if (!rbKeyLabel.equals(other.rbKeyLabel))
                return false;
            if (rbKeyValidationMessage == null) {
                if (other.rbKeyValidationMessage != null)
                    return false;
            } else if (!rbKeyValidationMessage
                    .equals(other.rbKeyValidationMessage))
                return false;
            if (recursiveSupported == null) {
                if (other.recursiveSupported != null)
                    return false;
            } else if (!recursiveSupported.equals(other.recursiveSupported))
                return false;
            if (type == null) {
                if (other.type != null)
                    return false;
            } else if (!type.equals(other.type))
                return false;
            if (uiHint == null) {
                if (other.uiHint != null)
                    return false;
            } else if (!uiHint.equals(other.uiHint))
                return false;
            if (validationMessage == null) {
                if (other.validationMessage != null)
                    return false;
            } else if (!validationMessage.equals(other.validationMessage))
                return false;
            if (validationRegEx == null) {
                if (other.validationRegEx != null)
                    return false;
            } else if (!validationRegEx.equals(other.validationRegEx))
                return false;
            return true;
        }

    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenAccessTypeDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long               itemId;
        private String             name;
        private String             label;
        private String             rbKeyLabel;
        private Collection<String> impliedGrants;

        public ChiWenAccessTypeDef() {
            this(null, null, null, null, null);
        }

        public ChiWenAccessTypeDef(Long itemId, String name, String label, String rbKeyLabel, Collection<String> impliedGrants) {
            setItemId(itemId);
            setName(name);
            setLabel(label);
            setRbKeyLabel(rbKeyLabel);
            setImpliedGrants(impliedGrants);
        }

        public ChiWenAccessTypeDef(ChiWenAccessTypeDef other) {
            setItemId(other.getItemId());
            setName(other.getName());
            setLabel(other.getLabel());
            setRbKeyLabel(other.getRbKeyLabel());
            setImpliedGrants(other.getImpliedGrants());
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the rbKeyLabel
         */
        public String getRbKeyLabel() {
            return rbKeyLabel;
        }

        /**
         * @param rbKeyLabel the rbKeyLabel to set
         */
        public void setRbKeyLabel(String rbKeyLabel) {
            this.rbKeyLabel = rbKeyLabel;
        }

        /**
         * @return the impliedGrants
         */
        public Collection<String> getImpliedGrants() {
            return impliedGrants;
        }

        /**
         * @param impliedGrants the impliedGrants to set
         */
        public void setImpliedGrants(Collection<String> impliedGrants) {
            if(this.impliedGrants == null) {
                this.impliedGrants = new ArrayList<>();
            }

            if(this.impliedGrants == impliedGrants) {
                return;
            }

            this.impliedGrants.clear();

            if(impliedGrants != null) {
                this.impliedGrants.addAll(impliedGrants);
            }
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenAccessTypeDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("label={").append(label).append("} ");
            sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");

            sb.append("impliedGrants={");
            if(impliedGrants != null) {
                for(String impliedGrant : impliedGrants) {
                    if(impliedGrant != null) {
                        sb.append(impliedGrant).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result
                    + ((impliedGrants == null) ? 0 : impliedGrants.hashCode());
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result
                    + ((rbKeyLabel == null) ? 0 : rbKeyLabel.hashCode());

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenAccessTypeDef other = (ChiWenAccessTypeDef) obj;
            if (itemId == null) {
                if (other.itemId != null)
                    return false;
            } else if (other.itemId == null || !itemId.equals(other.itemId))
                return false;

            if (impliedGrants == null) {
                if (other.impliedGrants != null)
                    return false;
            } else if (!impliedGrants.equals(other.impliedGrants))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (rbKeyLabel == null) {
                if (other.rbKeyLabel != null)
                    return false;
            } else if (!rbKeyLabel.equals(other.rbKeyLabel))
                return false;
            return true;
        }
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenPolicyConditionDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long                itemId;
        private String              name;
        private String              evaluator;
        private Map<String, String> evaluatorOptions;
        private String              validationRegEx;
        private String              validationMessage;
        private String              uiHint;
        private String              label;
        private String              description;
        private String              rbKeyLabel;
        private String              rbKeyDescription;
        private String              rbKeyValidationMessage;

        public ChiWenPolicyConditionDef() {
            this(null, null, null, null, null, null, null, null, null, null, null, null);
        }

        public ChiWenPolicyConditionDef(Long itemId, String name, String evaluator, Map<String, String> evaluatorOptions) {
            this(itemId, name, evaluator, evaluatorOptions, null, null, null, null, null, null, null, null);
        }

        public ChiWenPolicyConditionDef(Long itemId, String name, String evaluator, Map<String, String> evaluatorOptions, String validationRegEx, String vaidationMessage, String uiHint, String label, String description, String rbKeyLabel, String rbKeyDescription, String rbKeyValidationMessage) { //NOPMD
            setItemId(itemId);
            setName(name);
            setEvaluator(evaluator);
            setEvaluatorOptions(evaluatorOptions);
            setValidationRegEx(validationRegEx);
            setValidationMessage(validationMessage);
            setUiHint(uiHint);
            setLabel(label);
            setDescription(description);
            setRbKeyLabel(rbKeyLabel);
            setRbKeyDescription(rbKeyDescription);
            setRbKeyValidationMessage(rbKeyValidationMessage);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the evaluator
         */
        public String getEvaluator() {
            return evaluator;
        }

        /**
         * @param evaluator the evaluator to set
         */
        public void setEvaluator(String evaluator) {
            this.evaluator = evaluator;
        }

        /**
         * @return the evaluatorOptions
         */
        public Map<String, String> getEvaluatorOptions() {
            return evaluatorOptions;
        }

        /**
         * @param evaluatorOptions the evaluatorOptions to set
         */
        public void setEvaluatorOptions(Map<String, String> evaluatorOptions) {
            this.evaluatorOptions = evaluatorOptions == null ? new HashMap<String, String>() : evaluatorOptions;
        }

        /**
         * @return the validationRegEx
         */
        public String getValidationRegEx() {
            return validationRegEx;
        }

        /**
         * @param validationRegEx the validationRegEx to set
         */
        public void setValidationRegEx(String validationRegEx) {
            this.validationRegEx = validationRegEx;
        }

        /**
         * @return the validationMessage
         */
        public String getValidationMessage() {
            return validationMessage;
        }

        /**
         * @param validationMessage the validationMessage to set
         */
        public void setValidationMessage(String validationMessage) {
            this.validationMessage = validationMessage;
        }

        /**
         * @return the uiHint
         */
        public String getUiHint() {
            return uiHint;
        }

        /**
         * @param uiHint the uiHint to set
         */
        public void setUiHint(String uiHint) {
            this.uiHint = uiHint;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the rbKeyLabel
         */
        public String getRbKeyLabel() {
            return rbKeyLabel;
        }

        /**
         * @param rbKeyLabel the rbKeyLabel to set
         */
        public void setRbKeyLabel(String rbKeyLabel) {
            this.rbKeyLabel = rbKeyLabel;
        }

        /**
         * @return the rbKeyDescription
         */
        public String getRbKeyDescription() {
            return rbKeyDescription;
        }

        /**
         * @param rbKeyDescription the rbKeyDescription to set
         */
        public void setRbKeyDescription(String rbKeyDescription) {
            this.rbKeyDescription = rbKeyDescription;
        }

        /**
         * @return the rbKeyValidationMessage
         */
        public String getRbKeyValidationMessage() {
            return rbKeyValidationMessage;
        }

        /**
         * @param rbKeyValidationMessage the rbKeyValidationMessage to set
         */
        public void setRbKeyValidationMessage(String rbKeyValidationMessage) {
            this.rbKeyValidationMessage = rbKeyValidationMessage;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenPolicyConditionDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("evaluator={").append(evaluator).append("} ");
            sb.append("evaluatorOptions={").append(evaluatorOptions).append("} ");
            sb.append("validationRegEx={").append(validationRegEx).append("} ");
            sb.append("validationMessage={").append(validationMessage).append("} ");
            sb.append("uiHint={").append(uiHint).append("} ");
            sb.append("label={").append(label).append("} ");
            sb.append("description={").append(description).append("} ");
            sb.append("rbKeyLabel={").append(rbKeyLabel).append("} ");
            sb.append("rbKeyDescription={").append(rbKeyDescription).append("} ");
            sb.append("rbKeyValidationMessage={").append(rbKeyValidationMessage).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result
                    + ((description == null) ? 0 : description.hashCode());
            result = prime * result
                    + ((evaluator == null) ? 0 : evaluator.hashCode());
            result = prime
                    * result
                    + ((evaluatorOptions == null) ? 0 : evaluatorOptions
                    .hashCode());
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime
                    * result
                    + ((rbKeyDescription == null) ? 0 : rbKeyDescription
                    .hashCode());
            result = prime * result
                    + ((rbKeyLabel == null) ? 0 : rbKeyLabel.hashCode());
            result = prime
                    * result
                    + ((rbKeyValidationMessage == null) ? 0
                    : rbKeyValidationMessage.hashCode());
            result = prime * result
                    + ((uiHint == null) ? 0 : uiHint.hashCode());
            result = prime
                    * result
                    + ((validationMessage == null) ? 0 : validationMessage
                    .hashCode());
            result = prime
                    * result
                    + ((validationRegEx == null) ? 0 : validationRegEx
                    .hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenPolicyConditionDef other = (ChiWenPolicyConditionDef) obj;
            if (itemId == null) {
                if (other.itemId != null)
                    return false;
            } else if (other.itemId != null || !itemId.equals(other.itemId)) {
                return false;
            }

            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (evaluator == null) {
                if (other.evaluator != null)
                    return false;
            } else if (!evaluator.equals(other.evaluator))
                return false;
            if (evaluatorOptions == null) {
                if (other.evaluatorOptions != null)
                    return false;
            } else if (!evaluatorOptions.equals(other.evaluatorOptions))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (rbKeyDescription == null) {
                if (other.rbKeyDescription != null)
                    return false;
            } else if (!rbKeyDescription.equals(other.rbKeyDescription))
                return false;
            if (rbKeyLabel == null) {
                if (other.rbKeyLabel != null)
                    return false;
            } else if (!rbKeyLabel.equals(other.rbKeyLabel))
                return false;
            if (rbKeyValidationMessage == null) {
                if (other.rbKeyValidationMessage != null)
                    return false;
            } else if (!rbKeyValidationMessage
                    .equals(other.rbKeyValidationMessage))
                return false;
            if (uiHint == null) {
                if (other.uiHint != null)
                    return false;
            } else if (!uiHint.equals(other.uiHint))
                return false;
            if (validationMessage == null) {
                if (other.validationMessage != null)
                    return false;
            } else if (!validationMessage.equals(other.validationMessage))
                return false;
            if (validationRegEx == null) {
                if (other.validationRegEx != null)
                    return false;
            } else if (!validationRegEx.equals(other.validationRegEx))
                return false;
            return true;
        }
    }

    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenContextEnricherDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private Long                itemId;
        private String              name;
        private String              enricher;
        private Map<String, String> enricherOptions;

        public ChiWenContextEnricherDef() {
            this(null, null, null, null);
        }

        public ChiWenContextEnricherDef(Long itemId, String name, String enricher, Map<String, String> enricherOptions) {
            setItemId(itemId);
            setName(name);
            setEnricher(enricher);
            setEnricherOptions(enricherOptions);
        }

        /**
         * @return the itemId
         */
        public Long getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the enricher
         */
        public String getEnricher() {
            return enricher;
        }

        /**
         * @param enricher the enricher to set
         */
        public void setEnricher(String enricher) {
            this.enricher = enricher;
        }

        /**
         * @return the enricherOptions
         */
        public Map<String, String> getEnricherOptions() {
            return enricherOptions;
        }

        /**
         * @param enricherOptions the enricherOptions to set
         */
        public void setEnricherOptions(Map<String, String> enricherOptions) {
            this.enricherOptions = enricherOptions == null ? new HashMap<String, String>() : enricherOptions;
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenContextEnricherDef={");
            sb.append("itemId={").append(itemId).append("} ");
            sb.append("name={").append(name).append("} ");
            sb.append("enricher={").append(enricher).append("} ");
            sb.append("enricherOptions={").append(enricherOptions).append("} ");
            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result
                    + ((enricher == null) ? 0 : enricher.hashCode());
            result = prime
                    * result
                    + ((enricherOptions == null) ? 0 : enricherOptions
                    .hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenContextEnricherDef other = (ChiWenContextEnricherDef) obj;
            if (itemId == null) {
                if (other.itemId != null)
                    return false;
            } else if (other.itemId == null || !itemId.equals(other.itemId))
                return false;

            if (enricher == null) {
                if (other.enricher != null)
                    return false;
            } else if (!enricher.equals(other.enricher))
                return false;
            if (enricherOptions == null) {
                if (other.enricherOptions != null)
                    return false;
            } else if (!enricherOptions.equals(other.enricherOptions))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    }



    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenDataMaskTypeDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private List<ChiWenDataMaskTypeDef> maskTypes;
        private List<ChiWenAccessTypeDef>   accessTypes;
        private List<ChiWenResourceDef>     resources;


        public ChiWenDataMaskTypeDef() {
            setMaskTypes(null);
            setAccessTypes(null);
            setResources(null);
        }

        public ChiWenDataMaskTypeDef(List<ChiWenDataMaskTypeDef> maskTypes, List<ChiWenAccessTypeDef> accessTypes, List<ChiWenResourceDef> resources) {
            setMaskTypes(maskTypes);
            setAccessTypes(accessTypes);
            setResources(resources);
        }

        public ChiWenDataMaskTypeDef(ChiWenDataMaskTypeDef other) {
            setMaskTypes(other.getMaskTypes());
            setAccessTypes(other.getAccessTypes());
            setResources(other.getResources());
        }

        public List<ChiWenDataMaskTypeDef> getMaskTypes() {
            return maskTypes;
        }

        public void setMaskTypes(List<ChiWenDataMaskTypeDef> maskTypes) {
            if(this.maskTypes == null) {
                this.maskTypes = new ArrayList<>();
            }

            if(this.maskTypes == maskTypes) {
                return;
            }

            this.maskTypes.clear();

            if(maskTypes != null) {
                this.maskTypes.addAll(maskTypes);
            }
        }

        public List<ChiWenAccessTypeDef> getAccessTypes() {
            return accessTypes;
        }

        public void setAccessTypes(List<ChiWenAccessTypeDef> accessTypes) {
            if(this.accessTypes == null) {
                this.accessTypes = new ArrayList<>();
            }

            if(this.accessTypes == accessTypes) {
                return;
            }

            this.accessTypes.clear();

            if(accessTypes != null) {
                this.accessTypes.addAll(accessTypes);
            }
        }

        public List<ChiWenResourceDef> getResources() {
            return resources;
        }

        public void setResources(List<ChiWenResourceDef> resources) {
            if(this.resources == null) {
                this.resources = new ArrayList<>();
            }

            if(this.resources == resources) {
                return;
            }

            this.resources.clear();

            if(resources != null) {
                this.resources.addAll(resources);
            }
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenDataMaskTypeDef={");

            sb.append("maskTypes={");
            if(maskTypes != null) {
                for(ChiWenDataMaskTypeDef maskType : maskTypes) {
                    if(maskType != null) {
                        sb.append(maskType).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("accessTypes={");
            if(accessTypes != null) {
                for(ChiWenAccessTypeDef accessType : accessTypes) {
                    if(accessType != null) {
                        accessType.toString(sb).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("resources={");
            if(resources != null) {
                for(ChiWenResourceDef resource : resources) {
                    if(resource != null) {
                        resource.toString(sb).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((maskTypes == null) ? 0 : maskTypes.hashCode());
            result = prime * result + ((accessTypes == null) ? 0 : accessTypes.hashCode());
            result = prime * result + ((resources == null) ? 0 : resources.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenDataMaskTypeDef other = (ChiWenDataMaskTypeDef) obj;
            if (maskTypes == null) {
                if (other.maskTypes != null)
                    return false;
            } else if (other.maskTypes == null || !maskTypes.equals(other.maskTypes))
                return false;

            if (accessTypes == null) {
                if (other.accessTypes != null)
                    return false;
            } else if (!accessTypes.equals(other.accessTypes))
                return false;
            if (resources == null) {
                if (other.resources != null)
                    return false;
            } else if (!resources.equals(other.resources))
                return false;
            return true;
        }
    }


    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown=true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ChiWenRowFilterDef implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private List<ChiWenAccessTypeDef> accessTypes;
        private List<ChiWenResourceDef>   resources;


        public ChiWenRowFilterDef() {
            setAccessTypes(null);
            setResources(null);
        }

        public ChiWenRowFilterDef(List<ChiWenAccessTypeDef> accessTypes, List<ChiWenResourceDef> resources) {
            setAccessTypes(accessTypes);
            setResources(resources);
        }

        public ChiWenRowFilterDef(ChiWenRowFilterDef other) {
            setAccessTypes(other.getAccessTypes());
            setResources(other.getResources());
        }

        public List<ChiWenAccessTypeDef> getAccessTypes() {
            return accessTypes;
        }

        public void setAccessTypes(List<ChiWenAccessTypeDef> accessTypes) {
            if(this.accessTypes == null) {
                this.accessTypes = new ArrayList<>();
            }

            if(this.accessTypes == accessTypes) {
                return;
            }

            this.accessTypes.clear();

            if(accessTypes != null) {
                this.accessTypes.addAll(accessTypes);
            }
        }

        public List<ChiWenResourceDef> getResources() {
            return resources;
        }

        public void setResources(List<ChiWenResourceDef> resources) {
            if(this.resources == null) {
                this.resources = new ArrayList<>();
            }

            if(this.resources == resources) {
                return;
            }

            this.resources.clear();

            if(resources != null) {
                this.resources.addAll(resources);
            }
        }

        @Override
        public String toString( ) {
            StringBuilder sb = new StringBuilder();

            toString(sb);

            return sb.toString();
        }

        public StringBuilder toString(StringBuilder sb) {
            sb.append("ChiWenRowFilterDef={");

            sb.append("accessTypes={");
            if(accessTypes != null) {
                for(ChiWenAccessTypeDef accessType : accessTypes) {
                    if(accessType != null) {
                        accessType.toString(sb).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("resources={");
            if(resources != null) {
                for(ChiWenResourceDef resource : resources) {
                    if(resource != null) {
                        resource.toString(sb).append(" ");
                    }
                }
            }
            sb.append("} ");

            sb.append("}");

            return sb;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((accessTypes == null) ? 0 : accessTypes.hashCode());
            result = prime * result + ((resources == null) ? 0 : resources.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChiWenRowFilterDef other = (ChiWenRowFilterDef) obj;

            if (accessTypes == null) {
                if (other.accessTypes != null)
                    return false;
            } else if (!accessTypes.equals(other.accessTypes))
                return false;
            if (resources == null) {
                if (other.resources != null)
                    return false;
            } else if (!resources.equals(other.resources))
                return false;
            return true;
        }
    }
}

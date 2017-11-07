package com.databps.bigdaf.chiwen.common;

import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResource;

public interface ChiWenMutableResource extends ChiWenAccessResource {
    void setOwnerUser(String ownerUser);

    void setValue(String type, String value);
    void setServiceDef(ChiWenServiceDef serviceDef);



}

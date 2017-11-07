package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.audit.domain.PersistentAuditEvent;
import com.databps.bigdaf.admin.vo.PersistentAuditEventVo;
import org.mapstruct.Mapper;

/**
 * @author shibingxin
 * @create 2017-08-18 下午3:11
 */
@Mapper(componentModel = "spring")
public interface PersistentAuditEventMapper extends EntityMapper <PersistentAuditEventVo, PersistentAuditEvent>{}

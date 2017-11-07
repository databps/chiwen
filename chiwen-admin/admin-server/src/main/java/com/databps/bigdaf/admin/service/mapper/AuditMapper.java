package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.Audit;
import com.databps.bigdaf.admin.vo.AuditVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by lgc on 17-8-3.
 */


@Mapper(componentModel = "spring")
public interface AuditMapper extends EntityMapper <AuditVo, Audit>{

}

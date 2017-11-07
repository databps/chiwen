package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.FileAccessType;
import com.databps.bigdaf.admin.domain.PluginStatus;
import com.databps.bigdaf.admin.vo.FileAccessTypeVo;
import com.databps.bigdaf.admin.vo.PluginStatusVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileAccessTypeMapper extends EntityMapper <FileAccessTypeVo, FileAccessType> {
}


package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.PluginStatus;
import com.databps.bigdaf.admin.vo.PluginStatusVo;
import org.mapstruct.Mapper;

/**
 * @author merlin
 * @create 2017-07-26 上午1:14
// */
@Mapper(componentModel = "spring")
public interface PluginStatusMapper extends EntityMapper <PluginStatusVo, PluginStatus> {
}


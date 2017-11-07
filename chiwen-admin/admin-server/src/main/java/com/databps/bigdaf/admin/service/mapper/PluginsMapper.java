package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.Plugins;
import com.databps.bigdaf.admin.vo.PluginsVo;
import org.mapstruct.Mapper;

/**
 * @author shibingxin
 * @create 2017-08-11 上午10:52
 */
@Mapper(componentModel = "spring")
public interface PluginsMapper extends EntityMapper <PluginsVo, Plugins> {
}

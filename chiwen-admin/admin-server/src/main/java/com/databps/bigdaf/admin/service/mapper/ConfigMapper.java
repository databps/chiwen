package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.vo.ConfigVo;
import org.mapstruct.Mapper;

/**
 * @author shibingxin
 * @create 2017-08-30 下午1:48
 */
@Mapper(componentModel = "spring")
public interface ConfigMapper extends EntityMapper <ConfigVo, Config> {
}

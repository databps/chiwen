package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.AuditStatistics;
import com.databps.bigdaf.admin.domain.PluginStatus;
import com.databps.bigdaf.admin.vo.AuditStatisticsVo;
import com.databps.bigdaf.admin.vo.PluginStatusVo;
import org.mapstruct.Mapper;

/**
 * @author shibingxin
 * @create 2017-08-03 上午10:01
 */
@Mapper(componentModel = "spring")
public interface AuditStatisticsMapper extends EntityMapper <AuditStatisticsVo, AuditStatistics>{}
package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.AuditDailyStatistics;
import com.databps.bigdaf.admin.vo.AuditDailyStatisticsVo;
import org.mapstruct.Mapper;

/**
 * @author haipeng
 * @create 17-10-11 下午3:07
 */
@Mapper(componentModel = "spring")
public interface AuditDailyStatisticsMapper extends EntityMapper <AuditDailyStatisticsVo, AuditDailyStatistics>{}
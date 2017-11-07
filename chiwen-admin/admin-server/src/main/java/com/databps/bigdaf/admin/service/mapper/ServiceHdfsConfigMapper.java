package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.ServiceHdfsConfig;
import com.databps.bigdaf.admin.vo.ServiceHdfsConfigVo;
import org.mapstruct.Mapper;

/**
 * Created by shibingxin on 2017/8/7.
 */
@Mapper(componentModel = "spring")
public interface ServiceHdfsConfigMapper  extends EntityMapper <ServiceHdfsConfigVo, ServiceHdfsConfig>{

}

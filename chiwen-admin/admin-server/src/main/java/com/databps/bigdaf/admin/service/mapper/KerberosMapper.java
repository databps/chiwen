package com.databps.bigdaf.admin.service.mapper;

import com.databps.bigdaf.admin.domain.Kerberos;
import com.databps.bigdaf.admin.vo.KerberosVo;
import org.mapstruct.Mapper;

/**
 * @author shibingxin
 * @create 2017-10-10 上午11:05
 */
@Mapper(componentModel = "spring")
public interface KerberosMapper extends EntityMapper <KerberosVo, Kerberos>{

}
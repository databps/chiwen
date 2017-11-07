package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.Kerberos;
import com.databps.bigdaf.admin.security.kerberos.execption.KerberosOperationException;
import com.databps.bigdaf.admin.vo.KerberosVo;
import java.io.IOException;

public interface KerberosService {


	void saveKerberosConf(KerberosVo kerberosVo) throws IOException, KerberosOperationException;

	KerberosVo findKerverosConfig(String cmpyId) ;

	Integer ping(String host) throws IOException ;

	String getHdfsPrincipal(String cmpyId);

	String getHdfsKeytabPath(String cmpyId);

	String getKrb5ConfPath(String cmpyId);
}

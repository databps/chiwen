package com.databps.bigdaf.admin.service.impl;


import static com.databps.bigdaf.admin.config.StartupRunner.cmpyId;

import com.databps.bigdaf.admin.BigDafApplication;
import com.databps.bigdaf.admin.config.KerberosConfig;
import com.databps.bigdaf.admin.dao.KerberosDao;
import com.databps.bigdaf.admin.domain.Kerberos;
import com.databps.bigdaf.admin.domain.KerberosConf;
import com.databps.bigdaf.admin.security.kerberos.KerberosOperationHandler;
import com.databps.bigdaf.admin.security.kerberos.MITKerberosOperationHandler;
import com.databps.bigdaf.admin.security.kerberos.PrincipalKeyCredential;
import com.databps.bigdaf.admin.security.kerberos.SecurePasswordUtils;
import com.databps.bigdaf.admin.security.kerberos.execption.KerberosOperationException;
import com.databps.bigdaf.admin.security.kerberos.execption.KerberosPrincipalAlreadyExistsException;
import com.databps.bigdaf.admin.service.KerberosService;
import com.databps.bigdaf.admin.service.mapper.KerberosMapper;
import com.databps.bigdaf.admin.util.KerberosFileHelper;
import com.databps.bigdaf.admin.util.NetUtils;
import com.databps.bigdaf.admin.vo.KerberosVo;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KerberosServiceImpl implements KerberosService {
	@Autowired
	private KerberosDao kerberosDao;
	@Autowired
	private KerberosConfig kerberosConfig ;
	@Autowired
	private KerberosOperationHandler kerberosOperationHandler;
	@Autowired
	private KerberosMapper kerberosMapper;
	private static Log log = LogFactory.getLog(KerberosServiceImpl.class);

	@Override
	public void saveKerberosConf(KerberosVo kerberosVo) throws IOException, KerberosOperationException {
		String dnsState =  kerberosVo.getDnsLookupKdc() !=null? "true":"false";
		String forwardState =  kerberosVo.getForwardable() !=null? "true":"false";
		kerberosVo.setDnsLookupKdc(dnsState);
		kerberosVo.setForwardable(forwardState);
 		Kerberos kerberos = kerberosMapper.toEntity(kerberosVo);
		kerberosDao.deleteKerberos(kerberosVo.getCmpyId());
		kerberosDao.saveKerberos(kerberos);
		deleteKeytabFiles(kerberosVo.getCmpyId());
		buildKrb5Conf(kerberos);
		createPrincipal(kerberos.getCmpyId());
	}


	public Integer createPrincipal(String cmpyId) throws KerberosOperationException {
		Integer version =null;
		try {
			Kerberos kerberos = kerberosDao.findKerberosConfig(cmpyId);
			if(kerberos ==null)
				throw new KerberosOperationException("创建KDC账号关于Kerberos的配置信息不能为空");
			PrincipalKeyCredential administratorCredential = new PrincipalKeyCredential(kerberos.getAdminPrincipal(),kerberos.getAdminPassword());
			kerberosOperationHandler.open(administratorCredential, kerberos.getRealm(), getKerberosEnvType(kerberos));
			createAdminPrincipal(kerberosOperationHandler,kerberos);
			createHdfsPrincipal(kerberosOperationHandler,kerberos);
			createHbasePrincipal(kerberosOperationHandler,kerberos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			kerberosOperationHandler.close();
		}
		return version;
	}

	/**
	 * 创建admin的principal
	 * @param kerberosOperationHandler //TODO shibingxin 这个有时间在改造 看起来更通用一些
	 * @throws KerberosOperationException
	 */
	private void createAdminPrincipal(KerberosOperationHandler kerberosOperationHandler,Kerberos kerberos)
			throws KerberosOperationException, InterruptedException {
		String principal = kerberos.getBigdafPrincipal();
		principal = replaceHostReleam(principal, NetUtils.getHostNameForLiunx(),kerberos.getRealm());
		try{
			kerberosOperationHandler.createPrincipal(principal, SecurePasswordUtils.createSecurePassword(), true);
		}catch (KerberosPrincipalAlreadyExistsException e) {
			log.error(String.format("create admin kerberos %s principal already Exists %s ",principal,e));
		}
		Thread.sleep(1000);
		kerberosOperationHandler.produceKeytabFile(principal, buildKeytabFile(kerberos.getKeytabDir(),kerberos.getBigdafKeytab()), true);
	}

	private void createHdfsPrincipal(KerberosOperationHandler kerberosOperationHandler,Kerberos kerberos)
			throws KerberosOperationException, InterruptedException {
		String principal = kerberos.getHdfsPrincipal();
		principal = replaceHostReleam(principal, NetUtils.getHostNameForLiunx(),kerberos.getRealm());
		try{
			kerberosOperationHandler.createPrincipal(principal, SecurePasswordUtils.createSecurePassword(), true);
		}catch (KerberosPrincipalAlreadyExistsException e) {
			log.error(String.format("create hdfs kerberos %s principal already Exists %s ",principal,e));
		}
		Thread.sleep(1000);
		kerberosOperationHandler.produceKeytabFile(principal,buildKeytabFile(kerberos.getKeytabDir(),kerberos.getHdfsKeytab()), true);
	}

	private void createHbasePrincipal(KerberosOperationHandler kerberosOperationHandler,Kerberos kerberos)
			throws KerberosOperationException, InterruptedException {
		String principal = kerberos.getHbasePrincipal();
		principal = replaceHostReleam(principal, NetUtils.getHostNameForLiunx(),kerberos.getRealm());
		try{
			kerberosOperationHandler.createPrincipal(principal, SecurePasswordUtils.createSecurePassword(), true);
		}catch (KerberosPrincipalAlreadyExistsException e) {
			log.error(String.format("create hbase kerberos %s principal already Exists %s ",principal,e));
		}
		Thread.sleep(1000);
		kerberosOperationHandler.produceKeytabFile(principal,buildKeytabFile(kerberos.getKeytabDir(),kerberos.getHbaseKeytab()), true);
	}


	public void deleteKeytabFiles(String cmpyId) throws KerberosOperationException, IOException {
		Kerberos kerberos = kerberosDao.findKerberosConfig(cmpyId);
		if(kerberos !=null) {
			String hbasePath = buildKeytabFile(kerberos.getKeytabDir(),kerberos.getHbaseKeytab());
			String hdfsPath = buildKeytabFile(kerberos.getKeytabDir(),kerberos.getHdfsKeytab());
			String bigdafPath = buildKeytabFile(kerberos.getKeytabDir(),kerberos.getBigdafKeytab());
			KerberosFileHelper.deleteKeytabs(hbasePath);
			KerberosFileHelper.deleteKeytabs(hdfsPath);
			KerberosFileHelper.deleteKeytabs(bigdafPath);
		}

	}


	public String getHdfsPrincipal(String cmpyId) {
		Kerberos kerberosConf = kerberosDao.findKerberosConfig(cmpyId);
		String principal = kerberosConf.getHdfsPrincipal();
		String realm = kerberosConf.getRealm();
		principal= principal+"@"+realm;
		return principal;
	}
	public String getHdfsKeytabPath(String cmpyId) {
		Kerberos kerberos = kerberosDao.findKerberosConfig(cmpyId);
		return buildKeytabFile(kerberos.getKeytabDir(),kerberos.getHdfsKeytab());
	}

	public String getKrb5ConfPath(String cmpyId) {
		Kerberos kerberos = kerberosDao.findKerberosConfig(cmpyId);
		String  absolutePath = kerberos.getKeytabDir();
		String krb5ConfPath =absolutePath+"/conf/krb5.conf";
		return krb5ConfPath;
	}

	private String replaceHostReleam(String principal ,String hostName,String realm) throws KerberosOperationException {
		if(StringUtils.isBlank(principal))
			throw new KerberosOperationException("创建的账号不能为空");
		if(StringUtils.isBlank(hostName))
			throw new KerberosOperationException("创建的hostname为空");
		principal = principal.replace("_HOST",hostName);
		principal= principal+"@"+realm;
		return principal;
	}


	private String buildKeytabFile(String keytabDir ,String keytabPath) {
		String filePath = keytabPath.replace("$[keytab_dir]",keytabDir);
		return filePath;
	}

	public KerberosVo findKerverosConfig(String cmpyId) {
		Kerberos config = kerberosDao.findKerberosConfig(cmpyId);
		KerberosVo kerberos = kerberosMapper.toVO(config);
		return kerberos;
	}

	public Integer productKeytabFile(String cmpyId) throws KerberosOperationException {
		Integer version;
		try {
			Kerberos KerberosConf = kerberosDao.findKerberosConfig(cmpyId);
			PrincipalKeyCredential  administratorCredential = new PrincipalKeyCredential(KerberosConf.getAdminPrincipal(),KerberosConf.getAdminPassword());
			kerberosOperationHandler.open(administratorCredential, KerberosConf.getRealm(), getKerberosEnvType(KerberosConf));
			version = kerberosOperationHandler.produceKeytabFile(KerberosConf.getAdminPrincipal(), kerberosConfig.getKerberosBigdafAdminKeytabFile(), true);
		}finally {
			kerberosOperationHandler.close();
		}
		return version;
	}

	@Override
	public Integer ping(String host) throws IOException {
		boolean bool = NetUtils.ping(host);
		return bool?1:0;
	}

	private  Map<String, String>  getKerberosEnvType(Kerberos KerberosConf) {
		Map<String, String> kerberos_env_map = new HashMap<>();
		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_ENCRYPTION_TYPES, KerberosConf.getEncryptionTypes());
		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_KDC_HOSTS, KerberosConf.getKdcHost());
		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_ADMIN_SERVER_HOST, KerberosConf.getKdcHost());
		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_KDC_CREATE_ATTRIBUTES, " ");
		return kerberos_env_map ;
	}

	private void buildKrb5Conf(Kerberos conf) throws IOException {
		Map<String,String> relaceMap = new HashMap<>();
		relaceMap.put("encryption_types", conf.getEncryptionTypes());
		relaceMap.put("kdc_host", conf.getKdcHost());
		relaceMap.put("realm", conf.getRealm());
		String  absolutePath = conf.getKeytabDir();
		String sourcePath =absolutePath+"/conf/krb5.model";
		String krb5ConfPath =absolutePath+"/conf/krb5.conf";
		KerberosFileHelper.buildKrb5Conf(relaceMap, sourcePath,krb5ConfPath);
	}
}

package com.databps.bigdaf.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Kerberos Config
 */
@Configuration
@PropertySource(value = "classpath:kerberos.properties")
public class KerberosConfig {

	private String KERBEROS_OPERATION_RETRY_TIMEOUT = "5";
	private String KERBEROS_OPERATION_RETRIES = "0";
	private String KERBEROS_AUTH_HTTP_PRINCIPAL = "HTTP/_HOST";
	private String KERBEROS_AUTH_KEYTAB_FILE = "etc/security/keytab/kerberos.keytab";
	private String KERBEROS_SERVER_DEFAULT_PORT = "88";
	private String KERBEROS_KEYTAB_CACHE_DIR = "tmp/krb5cc_0";
	private String KERBEROS_BIGDAF_ADMIN_KEYTAB_FILE = "../conf/bigdaf_admin.keytab";
	private String KERBEROS_HDFS_KEYTAB_FILE = "../conf/hdfs.keytab";
	private String KERBEROS_BIGDAF_GATEWAY_KEYTAB_FILE = "../conf/bigdaf_gateway.keytab";
	@Value("${kerberos.bigdaf.admin.krb5.conf}")
	private String KERBEROS_BIGDAF_ADMIN_KRB5_CONF  = "/conf/krb5.conf";
	private String KERVEROS_BIGDAF_ADMIN_PRINCIPAL = "bigdaf_admin/_HOST";
	@Value("${kerberos.bigdaf.admin.krb5.model}")
	private String KERBEROS_BIGDAF_ADMIN_KRB5_MODEL = "/conf/krb5.model";
	private String KERBEROS_HDFS_PRINCIPAL = "hdfs";

	public String getKerberBigdafAdaminKrb5Model() {
		return KERBEROS_BIGDAF_ADMIN_KRB5_MODEL;
	}

	public int getKerberosOperationRetries() {
		return Integer.parseInt(KERBEROS_OPERATION_RETRIES);
	}

	public int getKerberosOperationRetryTimeout() {
		return Integer.parseInt(KERBEROS_OPERATION_RETRY_TIMEOUT);
	}

	public String getKerberosAuthHttpPrincipal() {
		return KERBEROS_AUTH_HTTP_PRINCIPAL;
	}

	public String getKerberosAuthKeytabFile() {
		return KERBEROS_AUTH_KEYTAB_FILE;
	}

	public int getKerberosServerDefaultPort() {
		return Integer.parseInt(KERBEROS_SERVER_DEFAULT_PORT);
	}

	public String getKerberosKeytabCacheDir() {
		return KERBEROS_KEYTAB_CACHE_DIR;
	}

	public String getKerberosBigdafAdminKeytabFile() {
		return KERBEROS_BIGDAF_ADMIN_KEYTAB_FILE;
	}

	public String getKerberosHdfsKeytabFile() {
		return KERBEROS_HDFS_KEYTAB_FILE;
	}

	public String getKerberosBigdafGatewayKeytabFile() {
		return KERBEROS_BIGDAF_GATEWAY_KEYTAB_FILE;
	}

	public String getKerberosBigdafAdminPrincipal() {
		return KERVEROS_BIGDAF_ADMIN_PRINCIPAL;
	}

	public String getKerberosHdfsPrincipal() {
		return KERBEROS_HDFS_PRINCIPAL;
	}
	public String getKerberosBigdafAdmainKrb5Conf(){
		return KERBEROS_BIGDAF_ADMIN_KRB5_CONF;
	}
}

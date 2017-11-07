package com.databps.bigdaf.admin.service;

//public class KerberosServiceTest {
//
//	@Test
//	public void create() throws KerberosOperationException {
//		Integer version =null;
//		KerberosOperationHandler handle = new MITKerberosOperationHandler();
//		try {
//			 KerberosConf = new KerberosConf();
//			KerberosConf.setAdmin_password("123456");
//			KerberosConf.setAdmin_principal("admin/admin");
//			KerberosConf.setKdc_host("192.168.1.243");
//			KerberosConf.setRealm("BIGDATA");
//			PrincipalKeyCredential  administratorCredential = new PrincipalKeyCredential(KerberosConf.getAdmin_principal(),KerberosConf.getAdmin_password());
//			handle.open(administratorCredential, KerberosConf.getRealm(), getKerberosEnvType(KerberosConf));
//			String password = new SecurePasswordHelper().createSecurePassword();
//			version = handle.createPrincipal(getprincipal(), password, true);
//			System.out.println(version);
//		}finally {
//			handle.close();
//		}
//		
//	}
//	
//	private String getprincipal() {
//		
//		return "bigdaf-admin/hostName";
//	}
//	
//	private  Map<String, String>  getKerberosEnvType(KerberosConf KerberosConf) {
//		
//		Map<String, String> kerberos_env_map = new HashMap<>();
//		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_ENCRYPTION_TYPES, KerberosConf.getEncryption_type());
//		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_KDC_HOSTS, KerberosConf.getKdc_host());
//		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_ADMIN_SERVER_HOST, KerberosConf.getKdc_host());
//		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_AD_CREATE_ATTRIBUTES_TEMPLATE, "AD Create Template");
//		kerberos_env_map.put(MITKerberosOperationHandler.KERBEROS_ENV_KDC_CREATE_ATTRIBUTES, " ");
//		return kerberos_env_map ;
//	}
//}

package com.databps.bigdaf.admin.domain;

public class KerberosConfEntry {

private String kdc_host;
	
	private String realm;
	
	private String encryption_type;
	
	private String admin_principal ;
	
	private String admin_password ;

	public String getKdc_host() {
		return kdc_host;
	}

	public void setKdc_host(String kdc_host) {
		this.kdc_host = kdc_host;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getEncryption_type() {
		return encryption_type;
	}


	public String getAdmin_principal() {
		return admin_principal;
	}

	public void setAdmin_principal(String admin_principal) {
		this.admin_principal = admin_principal;
	}

	public String getAdmin_password() {
		return admin_password;
	}

	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

	public void setEncryption_type(String encryption_type) {
		this.encryption_type = encryption_type;
	}
}

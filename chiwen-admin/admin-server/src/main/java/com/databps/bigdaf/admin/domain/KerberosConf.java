package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class KerberosConf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1749210489005712034L;


	@Id
	private String _id;

	private String kdc_host;
	
	private String realm;
	
	private String encryption_types;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getEncryption_types() {
		return encryption_types;
	}

	public void setEncryption_types(String encryption_types) {
		this.encryption_types = encryption_types;
	}
}

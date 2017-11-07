package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Field;

public class Kerberos implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1749210489005712034L;


	@Id
	private String _id;
	@Field("cmpy_id")
	private String cmpyId;
	@Field("kdc_host")
	private String kdcHost;

	@Field("default_realm")
	private String defaultRealm;
	@Field("encryption_types")
	private String encryptionTypes;
	@Field("principal_lifetime")
	private String principalLifetime ;
	@Field("principal_lifetime_unit") //账号更新生命周期单位
	private String principalLifetimeUnit;
	@Field("ticket_lifetime")
	private String ticketLifetime ;
	@Field("ticket_lifetime_unit")
	private String ticketLifetimeUnit;
	@Field("renew_lifetime")
	private String renewLifetime ;
	@Field("renew_lifetime_unit")
	private String renewlifetimeUnit;
	@Field("dns_lookup_kdc")
	private String dnsLookupKdc; //DNS 查找 KDC
	@Field("forwardable")
	private String forwardable; //可转发票证
	@Field("kdc_timeout")
	private String  kdcTimeout ;
	@Field("kdc_timeout_unit")
	private String kdcTimeoutUnit;

	@Field("admin_principal")
	private String adminPrincipal ;
	@Field("admin_password")
	private String adminPassword ;

	@Field("keytab_dir")
	private String keytabDir ;
	@Field("realm")
	private String realm;
	@Field("additional_realms")
	private String additionalRealms;
	@Field("spnego_keytab")
	private String spnegoKeytab;
	@Field("spnego_principal")
	private String spnegoPrincipal;

	@Field("bigdaf_keytab")
	private String bigdafKeytab;
	@Field("bigdaf_principal")
	private String bigdafPrincipal;
	@Field("hbase_keytab")
	private String hbaseKeytab;
	@Field("hbase_principal")
	private String hbasePrincipal;
	@Field("hdfs_keytab")
	private String hdfsKeytab;
	@Field("hdfs_principal")
	private String hdfsPrincipal;



	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCmpyId() {
		return cmpyId;
	}

	public void setCmpyId(String cmpyId) {
		this.cmpyId = cmpyId;
	}

	public String getKdcHost() {
		return kdcHost;
	}

	public void setKdcHost(String kdcHost) {
		this.kdcHost = kdcHost;
	}

	public String getDefaultRealm() {
		return defaultRealm;
	}

	public void setDefaultRealm(String defaultRealm) {
		this.defaultRealm = defaultRealm;
	}

	public String getEncryptionTypes() {
		return encryptionTypes;
	}

	public void setEncryptionTypes(String encryptionTypes) {
		this.encryptionTypes = encryptionTypes;
	}

	public String getPrincipalLifetime() {
		return principalLifetime;
	}

	public void setPrincipalLifetime(String principalLifetime) {
		this.principalLifetime = principalLifetime;
	}

	public String getPrincipalLifetimeUnit() {
		return principalLifetimeUnit;
	}

	public void setPrincipalLifetimeUnit(String principalLifetimeUnit) {
		this.principalLifetimeUnit = principalLifetimeUnit;
	}

	public String getTicketLifetime() {
		return ticketLifetime;
	}

	public void setTicketLifetime(String ticketLifetime) {
		this.ticketLifetime = ticketLifetime;
	}

	public String getTicketLifetimeUnit() {
		return ticketLifetimeUnit;
	}

	public void setTicketLifetimeUnit(String ticketLifetimeUnit) {
		this.ticketLifetimeUnit = ticketLifetimeUnit;
	}

	public String getRenewLifetime() {
		return renewLifetime;
	}

	public void setRenewLifetime(String renewLifetime) {
		this.renewLifetime = renewLifetime;
	}

	public String getRenewlifetimeUnit() {
		return renewlifetimeUnit;
	}

	public void setRenewlifetimeUnit(String renewlifetimeUnit) {
		this.renewlifetimeUnit = renewlifetimeUnit;
	}

	public String getDnsLookupKdc() {
		return dnsLookupKdc;
	}

	public void setDnsLookupKdc(String dnsLookupKdc) {
		this.dnsLookupKdc = dnsLookupKdc;
	}

	public String getForwardable() {
		return forwardable;
	}

	public void setForwardable(String forwardable) {
		this.forwardable = forwardable;
	}

	public String getKdcTimeout() {
		return kdcTimeout;
	}

	public void setKdcTimeout(String kdcTimeout) {
		this.kdcTimeout = kdcTimeout;
	}

	public String getKdcTimeoutUnit() {
		return kdcTimeoutUnit;
	}

	public void setKdcTimeoutUnit(String kdcTimeoutUnit) {
		this.kdcTimeoutUnit = kdcTimeoutUnit;
	}

	public String getAdminPrincipal() {
		return adminPrincipal;
	}

	public void setAdminPrincipal(String adminPrincipal) {
		this.adminPrincipal = adminPrincipal;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getKeytabDir() {
		return keytabDir;
	}

	public void setKeytabDir(String keytabDir) {
		this.keytabDir = keytabDir;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getAdditionalRealms() {
		return additionalRealms;
	}

	public void setAdditionalRealms(String additionalRealms) {
		this.additionalRealms = additionalRealms;
	}

	public String getSpnegoKeytab() {
		return spnegoKeytab;
	}

	public void setSpnegoKeytab(String spnegoKeytab) {
		this.spnegoKeytab = spnegoKeytab;
	}

	public String getSpnegoPrincipal() {
		return spnegoPrincipal;
	}

	public void setSpnegoPrincipal(String spnegoPrincipal) {
		this.spnegoPrincipal = spnegoPrincipal;
	}

	public String getBigdafKeytab() {
		return bigdafKeytab;
	}

	public void setBigdafKeytab(String bigdafKeytab) {
		this.bigdafKeytab = bigdafKeytab;
	}

	public String getBigdafPrincipal() {
		return bigdafPrincipal;
	}

	public void setBigdafPrincipal(String bigdafPrincipal) {
		this.bigdafPrincipal = bigdafPrincipal;
	}

	public String getHbaseKeytab() {
		return hbaseKeytab;
	}

	public void setHbaseKeytab(String hbaseKeytab) {
		this.hbaseKeytab = hbaseKeytab;
	}

	public String getHbasePrincipal() {
		return hbasePrincipal;
	}

	public void setHbasePrincipal(String hbasePrincipal) {
		this.hbasePrincipal = hbasePrincipal;
	}

	public String getHdfsKeytab() {
		return hdfsKeytab;
	}

	public void setHdfsKeytab(String hdfsKeytab) {
		this.hdfsKeytab = hdfsKeytab;
	}

	public String getHdfsPrincipal() {
		return hdfsPrincipal;
	}

	public void setHdfsPrincipal(String hdfsPrincipal) {
		this.hdfsPrincipal = hdfsPrincipal;
	}
}

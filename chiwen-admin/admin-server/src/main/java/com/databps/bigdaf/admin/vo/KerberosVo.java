package com.databps.bigdaf.admin.vo;

import javax.validation.constraints.NotNull;

/**
 * @author shibingxin
 * @create 2017-10-10 上午10:49
 */
public class KerberosVo {

  private String cmpyId;
  @NotNull
  private String kdcHost;

  private String defaultRealm;
  private String encryptionTypes;
  private String principalLifetime ;
  private String principalLifetimeUnit;

  private String ticketLifetime ;
  private String ticketLifetimeUnit;
  private String renewLifetime ;
  private String renewlifetimeUnit;
  private String dnsLookupKdc; //DNS 查找 KDC
  private String forwardable; //可转发票证
  private String  kdcTimeout ;
  private String kdcTimeoutUnit;
  @NotNull
  private String adminPrincipal ;
  @NotNull
  private String adminPassword ;

  private String keytabDir ;
  @NotNull
  private String realm;
  private String additionalRealms;
  private String spnegoKeytab;
  private String spnegoPrincipal;

  private String bigdafKeytab;
  private String bigdafPrincipal;
  private String hbaseKeytab;
  private String hbasePrincipal;
  private String hdfsKeytab;
  private String hdfsPrincipal;


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
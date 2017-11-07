package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;


	private String uname;
	
	private String password;

	private String kaptcha;

	public String getKaptcha() {
		return kaptcha;
	}

	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}

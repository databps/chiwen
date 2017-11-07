package com.databps.bigdaf.admin.domain;

import com.databps.bigdaf.core.util.DateUtils;
import java.io.Serializable;
import org.springframework.data.annotation.Id;

public class TKdcUser implements Serializable {
	private static final long serialVersionUID = 1899983333389L;
	@Id
	private String _id;
	private String principal_name;
	private String principal_pwd;
	private String status = "1";
	private String flag;
	private String create_time = DateUtils.format(DateUtils.YYYYMMDDHHMMSSSSS);
	private String knox_user_id;
	private String cmpy_id;
	private String description = "flag=1: 用户账号，2:hadoop系统账号,3:daf系统账号；status=1:启用，0：禁用";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPrincipal_name() {
		return principal_name;
	}

	public void setPrincipal_name(String principal_name) {
		this.principal_name = principal_name;
	}

	public String getPrincipal_pwd() {
		return principal_pwd;
	}

	public void setPrincipal_pwd(String principal_pwd) {
		this.principal_pwd = principal_pwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getKnox_user_id() {
		return knox_user_id;
	}

	public void setKnox_user_id(String knox_user_id) {
		this.knox_user_id = knox_user_id;
	}

	public String getCmpy_id() {
		return cmpy_id;
	}

	public void setCmpy_id(String cmpy_id) {
		this.cmpy_id = cmpy_id;
	}
}

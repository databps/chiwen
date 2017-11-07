package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public class TWhiteList implements Serializable {

	/**
	 * hdfs资源控制
	 */
	private static final long serialVersionUID = 56262231L;

	@Id
	private String _id;
	@NotBlank
	private String ip;

	private String status;

	private String create_time;
	private String cmpy_id;

	public String getCmpy_id() {
		return cmpy_id;
	}

	public void setCmpy_id(String cmpy_id) {
		this.cmpy_id = cmpy_id;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}

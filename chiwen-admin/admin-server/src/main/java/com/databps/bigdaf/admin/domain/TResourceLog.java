package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TResourceLog implements Serializable {

	/**
	 * hdfs资源控制
	 */
	private static final long serialVersionUID = 56262231L;

	private String resource_name;
	private String outcome;
	private Map<String, Object> kuser;
	private String message;
	private String create_time;
	private String ip;
	private String cmpy_id;
	private String service_name;

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	private String directory;

	public String getCmpy_id() {
		return cmpy_id;
	}

	public void setCmpy_id(String cmpy_id) {
		this.cmpy_id = cmpy_id;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Map<String, Object> getKuser() {
		return kuser;
	}

	public void setKuser(Map<String, Object> kuser) {
		this.kuser = kuser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 日志需要的字段
	 * 
	 * @param action：dispatch
	 * @return
	 */
	public String toString(String action) {
		String f = ",";
		@SuppressWarnings("unchecked")
		List<Map<String, String>> roleList = (List<Map<String, String>>) this.kuser.get("roles");
		StringBuffer sbRole = new StringBuffer();
		if (roleList != null)
			for (int i = 0; i < roleList.size(); i++) {
				Map<String, String> map = roleList.get(i);
				String roleName = map.get("rolename");
				if (i != 0)
					sbRole.append("|");
				sbRole.append(roleName);
			}
		if (action == null) {
			return null;
		}
		if (action.equals("dispatch"))
			return this.kuser.get("uname") + f + sbRole.toString() + f + this.ip + f + this.resource_name + f
					+ this.directory + f + this.outcome + f + this.message + f + this.service_name + f
					+ this.create_time + f + "\r\n";
		else
			return this.kuser.get("uname") + f + this.ip + f + this.outcome + f + this.message + f + this.service_name
					+ f + this.create_time + "\r\n";
	}

	/**
	 * 下载操作日志的头信息
	 * 
	 * @return
	 */
	public static String getHeaderDispatch() {
		StringBuffer sbRole = new StringBuffer();
		sbRole.append("用户名称").append(",");
		sbRole.append("所属角色").append(",");
		sbRole.append("访问ip").append(",");
		sbRole.append("资源名称").append(",");
		sbRole.append("操作").append(",");
		sbRole.append("操作结果").append(",");
		sbRole.append("结果说明").append(",");
		sbRole.append("访问类型").append(",");
		sbRole.append("操作时间").append("\r\n");
		return sbRole.toString();
	}

	/**
	 * 下载访问日志的头信息
	 * 
	 * @return
	 */
	public static String getHeaderAccess() {
		StringBuffer sbRole = new StringBuffer();
		sbRole.append("用户名称").append(",");
		sbRole.append("访问ip").append(",");
		sbRole.append("认证结果").append(",");
		sbRole.append("结果说明").append(",");
		sbRole.append("访问类型").append(",");
		sbRole.append("操作时间").append("\r\n");
		return sbRole.toString();
	}
}

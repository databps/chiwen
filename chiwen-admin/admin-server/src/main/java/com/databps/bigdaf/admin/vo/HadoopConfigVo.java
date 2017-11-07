package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

public class HadoopConfigVo implements Serializable {

	private static final long serialVersionUID = 136262562525L;


	private String webhdfs_url;
	
	private String dataNode;


	public String getWebhdfs_url() {
		return webhdfs_url;
	}

	public void setWebhdfs_url(String webhdfs_url) {
		this.webhdfs_url = webhdfs_url;
	}

	public String getDataNode() {
		return dataNode;
	}

	public void setDataNode(String dataNode) {
		this.dataNode = dataNode;
	}


	
}

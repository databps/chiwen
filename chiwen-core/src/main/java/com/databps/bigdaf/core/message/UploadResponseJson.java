package com.databps.bigdaf.core.message;

import java.io.Serializable;

public class UploadResponseJson implements Serializable{
	
	public static final int SUCCESS=1000;
	
	private static final long serialVersionUID = 1L;

	private String error;
	
	private int[] errorkeys;
	
	private String[] initialPreview;
	
	
	private String url;
	
	public UploadResponseJson(){
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public int[] getErrorkeys() {
		return errorkeys;
	}


	public void setErrorkeys(int[] errorkeys) {
		this.errorkeys = errorkeys;
	}


	public String[] getInitialPreview() {
		return initialPreview;
	}


	public void setInitialPreview(String[] initialPreview) {
		this.initialPreview = initialPreview;
	}
	
}


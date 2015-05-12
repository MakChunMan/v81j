package com.imagsky.v81j.domain;

import com.google.gson.annotations.SerializedName;

public class JsonResponse {

	public static final String STATUS_OK = "OK";
	public static final String STATUS_FAIL = "FAIL";
	
	@SerializedName("_module")
	private String module;
	
	@SerializedName("_status")
	private String status;
	
	@SerializedName("_msg")
	private String message;
	
	@SerializedName("_token")
	private String token;
	
	@SerializedName("_timestamp")
	private long timestamp;
	
	@SerializedName("_savetime")
	private long savetime;

	@SerializedName("_sourceurl")
	private String source_url;
	/****************** BASIC setter / getter *************/
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public java.util.Date getTimestamp() {
		return new java.util.Date(this.timestamp);
	}

	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp.getTime();
	}

	public long getSavetime() {
		return savetime;
	}

	public void setSavetime(long savetime) {
		this.savetime = savetime;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	
	
}

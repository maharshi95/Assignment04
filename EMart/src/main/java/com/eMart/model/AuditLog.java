package com.eMart.model;

/**
 * Created by maharshigor on 11/07/16.
 */
public class AuditLog {

	private String url;
	private String ipAddress;
	private long timestamp;
	private long responseCode;
	private long duration;
	private String requestType;
	private String data;

	public AuditLog() {

	}

	public AuditLog(String url, String ipAddress, long timestamp, long responseCode, long duration, String requestType, String data) {
		this.url = url;
		this.ipAddress = ipAddress;
		this.timestamp = timestamp;
		this.responseCode = responseCode;
		this.duration = duration;
		this.requestType = requestType;
		this.data = data;
	}


	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
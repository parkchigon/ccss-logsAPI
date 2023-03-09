package com.lgu.common.trace.model;

public class TraceInfoVO {
	private String appId;
	private String appCtn;
	private String deviceCtn;
	private String deviceKey;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppCtn() {
		return appCtn;
	}
	public void setAppCtn(String appCtn) {
		this.appCtn = appCtn;
	}
	public String getDeviceCtn() {
		return deviceCtn;
	}
	public void setDeviceCtn(String deviceCtn) {
		this.deviceCtn = deviceCtn;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	
	@Override
	public String toString() {
		return "TraceInfoVO [appId=" + appId + ", appCtn=" + appCtn + ", deviceCtn=" + deviceCtn + ", deviceKey="
				+ deviceKey + "]";
	}
}


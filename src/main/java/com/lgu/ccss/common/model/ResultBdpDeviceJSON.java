package com.lgu.ccss.common.model;

public class ResultBdpDeviceJSON {
	
	private String deviceType;
	private String appType;
	private String appVer;
	private String saVer;
	private String carOem;
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppVer() {
		return appVer;
	}
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	public String getSaVer() {
		return saVer;
	}
	public void setSaVer(String saVer) {
		this.saVer = saVer;
	}
	public String getCarOem() {
		return carOem;
	}
	public void setCarOem(String carOem) {
		this.carOem = carOem;
	}
	@Override
	public String toString() {
		return "{deviceType=" + deviceType + ", appType=" + appType + ", appVer=" + appVer
				+ ", saVer=" + saVer + ", carOem=" + carOem + "}";
	}
	
}

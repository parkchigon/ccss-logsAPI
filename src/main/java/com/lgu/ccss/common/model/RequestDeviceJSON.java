package com.lgu.ccss.common.model;

public class RequestDeviceJSON implements Cloneable{

	private String deviceType;
	private String appType;
	private String appVer;
	private String saVer;
	private String carOem;
	private String devModel;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}	
	
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
	public String getDevModel() {
		return devModel;
	}
	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}
	@Override
	public String toString() {
		return "RequestDeviceJSON [deviceType=" + deviceType + ", appType=" + appType + ", appVer=" + appVer
				+ ", saVer=" + saVer + ", carOem=" + carOem + ", devModel=" + devModel + "]";
	}	

	
}

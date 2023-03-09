package com.lgu.ccss.common.model;

public class ResultElasticJSON {

	private String logNo;
	private String logTime;
	private String deviceType;
	private String appType;
	private String appVer;
	private String saVer;
	private String carOem;
	private String devModel;
	private String ctn;
	private String userCtn;
	private String serial;
	private String useType;
	private String requestTime;
	private String responseTime;
	private String result;
	private String resultCode;
	private int category0;
	private int category1;
	private int category2;
	private String item;
	private String type;
	private String value;
	private String nwType;
	private String posX;
	private String posY;
	private String funcType;
	//private long regTime;
	public String getLogNo() {
		return logNo;
	}
	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
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
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getUserCtn() {
		return userCtn;
	}
	public void setUserCtn(String userCtn) {
		this.userCtn = userCtn;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public int getCategory0() {
		return category0;
	}
	public void setCategory0(int category0) {
		this.category0 = category0;
	}
	public int getCategory1() {
		return category1;
	}
	public void setCategory1(int category1) {
		this.category1 = category1;
	}
	public int getCategory2() {
		return category2;
	}
	public void setCategory2(int category2) {
		this.category2 = category2;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNwType() {
		return nwType;
	}
	public void setNwType(String nwType) {
		this.nwType = nwType;
	}
	public String getPosX() {
		return posX;
	}
	public void setPosX(String posX) {
		this.posX = posX;
	}
	public String getPosY() {
		return posY;
	}
	public void setPosY(String posY) {
		this.posY = posY;
	}
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
/*	public long getRegTime() {
		return regTime;
	}
	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}*/
	@Override
	public String toString() {
		return "ResultElasticJSON [logNo=" + logNo + ", logTime=" + logTime + ", deviceType=" + deviceType
				+ ", appType=" + appType + ", appVer=" + appVer + ", saVer=" + saVer + ", carOem=" + carOem
				+ ", devModel=" + devModel + ", ctn=" + ctn + ", userCtn=" + userCtn + ", serial=" + serial
				+ ", useType=" + useType + ", requestTime=" + requestTime + ", responseTime=" + responseTime
				+ ", result=" + result + ", resultCode=" + resultCode + ", category0=" + category0 + ", category1="
				+ category1 + ", category2=" + category2 + ", item=" + item + ", type=" + type + ", value=" + value
				+ ", nwType=" + nwType + ", posX=" + posX + ", posY=" + posY + ", funcType=" + funcType
				+  "]";
	}

}

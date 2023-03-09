package com.lgu.ccss.common.model;

import java.util.HashMap;

import org.apache.http.client.utils.CloneUtils;

public class RequestDataJSON implements Cloneable {
	
	private String seq;
	private String logTime;
	private RequestDeviceJSON device;
	private String ctn;
	private String userCtn;
	private String serial;
	private String useType;
	private String requestTime;
	private String responseTime;
	private String result;
	private String resultCode;
	private String category0;
	private String category1;
	private String category2;
	private String item;
	private String type;
	private String value;
	private String nwType;
	private String posX;
	private String posY;
	private Object svcDetailInfo;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		RequestDataJSON dataJson = (RequestDataJSON)super.clone();
		dataJson.device = (RequestDeviceJSON)CloneUtils.clone(dataJson.device);
		dataJson.svcDetailInfo = ((HashMap<String, String>)dataJson.svcDetailInfo).clone();
		
		return dataJson;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public RequestDeviceJSON getDevice() {
		return device;
	}
	public void setDevice(RequestDeviceJSON device) {
		this.device = device;
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
	public String getCategory0() {
		return category0;
	}
	public void setCategory0(String category0) {
		this.category0 = category0;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
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
	public Object getSvcDetailInfo() {
		return svcDetailInfo;
	}
	public void setSvcDetailInfo(Object svcDetailInfo) {
		this.svcDetailInfo = svcDetailInfo;
	}
	@Override
	public String toString() {
		return "RequestDataJSON [seq=" + seq + ", logTime=" + logTime + ", device=" + device.toString() + ", ctn=" + ctn
				+ ", userCtn=" + userCtn + ", serial=" + serial + ", useType=" + useType + ", requestTime="
				+ requestTime + ", responseTime=" + responseTime + ", result=" + result + ", resultCode=" + resultCode
				+ ", category0=" + category0 + ", category1=" + category1 + ", category2=" + category2 + ", item="
				+ item + ", type=" + type + ", value=" + value + ", nwType=" + nwType + ", posX=" + posX + ", posY="
				+ posY + ", svcDetailInfo=" + svcDetailInfo + "]";
	}
	

}

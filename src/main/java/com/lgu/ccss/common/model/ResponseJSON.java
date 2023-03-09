package com.lgu.ccss.common.model;

public class ResponseJSON {
	private String resultCode;
	private String resultMsg;
	private Object resultData;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	
	@Override
	public String toString() {
		return "ResponseJSON [resultCode=" + resultCode + ", resultMsg=" + resultMsg + ", resultData=" + resultData + "]";
	}
}

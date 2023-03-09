package com.lgu.common.model;

public class ResultCode {
	private String code;
	private String msg;
	
	public ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResultCode [code=" + code + ", msg=" + msg + "]";
	}	
}
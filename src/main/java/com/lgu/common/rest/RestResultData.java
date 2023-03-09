package com.lgu.common.rest;

import java.util.LinkedHashMap;
import java.util.Map;

public class RestResultData {
	private int statusCode;
	private Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private String json;

	public RestResultData(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setHeader(String name, String value) {
		headerMap.put(name, value);
	}
	
	public String getHeader(String name) {
		return headerMap.get(name);
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "RestResultData [statusCode=" + statusCode + ", headerMap=" + headerMap + ", json=" + json + "]";
	}
}

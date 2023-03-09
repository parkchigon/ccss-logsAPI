package com.lgu.common.rest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RestRequestData {
	private String url;
	private Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private String json;
	
	private String traceId;
	private String source;
	private String target;
	
	private int connectionTimeout;
	private int timeout;
	
	public RestRequestData(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHeader(String name, String value) {
		headerMap.put(name, value);
	}
	
	public String getHeader(String name) {
		return headerMap.get(name);
	}
	
	public Set<String> getHeaders() {
		return headerMap.keySet();
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "RestRequestData [url=" + url + ", headerMap=" + headerMap + ", json=" + json + ", traceId=" + traceId
				+ ", source=" + source + ", target=" + target + ", connectionTimeout=" + connectionTimeout
				+ ", timeout=" + timeout + "]";
	}
}

package com.lgu.common.trace.model;


public class TraceDataJSON {
	private String traceId = "";
	private String hostName = "";
	private String protocol = "";
	private String source = "";
	private String target = "";
	private String content	= "";
	
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "TraceDataJSON [traceId=" + traceId + ", hostName=" + hostName + ", protocol=" + protocol + ", source="
				+ source + ", target=" + target + ", content=" + content + "]";
	}
}

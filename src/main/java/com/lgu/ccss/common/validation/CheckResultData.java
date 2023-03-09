package com.lgu.ccss.common.validation;

public class CheckResultData {
	public boolean status;
	public String reason;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		return "CheckResultData [status=" + status + ", reason=" + reason + "]";
	}
}

package com.lgu.ccss.log.api.model;

public class ResultAuthRegisterJSON {
	public static final String OPEN_STATUS_NORMAL = "00";
	public static final String OPEN_STATUS_NONE = "01";
	
	private String openStatus;

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}

	@Override
	public String toString() {
		return "ResultAuthRegisterJSON [openStatus=" + openStatus + "]";
	}
}

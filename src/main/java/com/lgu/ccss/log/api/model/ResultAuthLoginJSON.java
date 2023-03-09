package com.lgu.ccss.log.api.model;

public class ResultAuthLoginJSON {
	private String ccssToken;
	private String ccssTokenExpiration;
	private String aiDeviceToken;
	private String aiPlatformToken;
	private String aiPlatformTokenExpiration;
	private String aiUrl;
	private String securityKey;
	
	private String joinStatus;
	private String firstJoinStatus;
	private String joinDate;
	private String ratePayment;
	private String ratePaymentStatus;
	private String ratePaymentChangeStatus;
	private String dataStatus;
	
	public String getCcssToken() {
		return ccssToken;
	}
	public void setCcssToken(String ccssToken) {
		this.ccssToken = ccssToken;
	}
	public String getCcssTokenExpiration() {
		return ccssTokenExpiration;
	}
	public void setCcssTokenExpiration(String ccssTokenExpiration) {
		this.ccssTokenExpiration = ccssTokenExpiration;
	}
	public String getAiDeviceToken() {
		return aiDeviceToken;
	}
	public void setAiDeviceToken(String aiDeviceToken) {
		this.aiDeviceToken = aiDeviceToken;
	}
	public String getAiPlatformToken() {
		return aiPlatformToken;
	}
	public void setAiPlatformToken(String aiPlatformToken) {
		this.aiPlatformToken = aiPlatformToken;
	}
	public String getAiPlatformTokenExpiration() {
		return aiPlatformTokenExpiration;
	}
	public void setAiPlatformTokenExpiration(String aiPlatformTokenExpiration) {
		this.aiPlatformTokenExpiration = aiPlatformTokenExpiration;
	}
	public String getAiUrl() {
		return aiUrl;
	}
	public void setAiUrl(String aiUrl) {
		this.aiUrl = aiUrl;
	}
	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public String getJoinStatus() {
		return joinStatus;
	}
	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
	public String getFirstJoinStatus() {
		return firstJoinStatus;
	}
	public void setFirstJoinStatus(String firstJoinStatus) {
		this.firstJoinStatus = firstJoinStatus;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getRatePayment() {
		return ratePayment;
	}
	public void setRatePayment(String ratePayment) {
		this.ratePayment = ratePayment;
	}
	public String getRatePaymentStatus() {
		return ratePaymentStatus;
	}
	public void setRatePaymentStatus(String ratePaymentStatus) {
		this.ratePaymentStatus = ratePaymentStatus;
	}
	public String getRatePaymentChangeStatus() {
		return ratePaymentChangeStatus;
	}
	public void setRatePaymentChangeStatus(String ratePaymentChangeStatus) {
		this.ratePaymentChangeStatus = ratePaymentChangeStatus;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	@Override
	public String toString() {
		return "ResultAuthLoginJSON [ccssToken=" + ccssToken + ", ccssTokenExpiration=" + ccssTokenExpiration
				+ ", aiDeviceToken=" + aiDeviceToken + ", aiPlatformToken=" + aiPlatformToken
				+ ", aiPlatformTokenExpiration=" + aiPlatformTokenExpiration + ", aiUrl=" + aiUrl + ", securityKey="
				+ securityKey + ", joinStatus=" + joinStatus + ", firstJoinStatus=" + firstJoinStatus + ", joinDate="
				+ joinDate + ", ratePayment=" + ratePayment + ", ratePaymentStatus=" + ratePaymentStatus
				+ ", ratePaymentChangeStatus=" + ratePaymentChangeStatus + ", dataStatus=" + dataStatus + "]";
	}
}

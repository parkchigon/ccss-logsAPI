package com.lgu.ccss.log.api.model;

public class AuthStatusResultData {
	public static final String JOIN_STATUS_NONE = "00";
	public static final String JOIN_STATUS_IN = "01";
	public static final String JOIN_STATUS_PAUSE = "02";
	public static final String JOIN_STATUS_ING = "09";
	
	public static final String FIRST_JOIN_STATUS_N = "00";
	public static final String FIRST_JOIN_STATUS_Y = "01";
	
	public static final String RATE_PAYMENT_STATUS_NORMAL = "00";
	public static final String RATE_PAYMENT_STATUS_EXPIRING = "01";
	public static final String RATE_PAYMENT_STATUS_EXPIRE = "02";
	
	public static final String RATE_PAYMENT_CHAGE_STATUS_Y = "01";
	public static final String RATE_PAYMENT_CHAGE_STATUS_N = "00";
	
	public static final String DATA_STATUS_REMAIN = "00";
	public static final String DATA_STATUS_EMPTY = "01";
	
	private String joinStatus;
	private String firstJoinStatus;
	private String joinDate;
	private String ratePayment;
	private String ratePaymentStatus;
	private String ratePaymentChangeStatus;
	private String dataStatus;
	
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
		return "AuthStatusResultData [joinStatus=" + joinStatus + ", firstJoinStatus=" + firstJoinStatus
				+ ", joinDate=" + joinDate + ", ratePayment=" + ratePayment + ", ratePaymentStatus=" + ratePaymentStatus
				+ ", ratePaymentChangeStatus=" + ratePaymentChangeStatus + ", dataStatus=" + dataStatus + "]";
	}
}

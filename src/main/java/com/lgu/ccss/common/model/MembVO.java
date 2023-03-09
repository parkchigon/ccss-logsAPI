package com.lgu.ccss.common.model;

import java.util.Date;

public class MembVO {
	public static final String USE_STATUS_JOIN = "01";
	public static final String USE_STATUS_SUSPEND = "02";
	public static final String USE_STATUS_FIRE = "09";
	
	public static final String USE_Y = "Y";
	public static final String USE_N = "N";
	
	public static final String NEW_REJOIN_Y = "Y";
	public static final String NEW_REJOIN_N = "N";
	
	public static final String PAY_RESV_Y = "Y";
	public static final String PAY_RESV_N = "N";

	public static final String EXP_SMS_SEND_Y = "Y";
	public static final String EXP_SMS_SEND_N = "N";
	
	public static final String MARKET_AM = "AM";
	
	private String membId;
	private String membNo;
	private String membCtn;
	private String useStatus;
	private String useYn;
	private String newRejoinYn;
	private String productNm;
	private Date joinDt;
	private Integer loginFailCnt;
	private Date latestLoginDt;
	private String transToken;
	private String serverId;
	private String payResvYn;
	private Date payResvDt;
	private String marketType;
	private String expSmsSendYn;
	private Date expSmsSendDt;
	private String birthYear;
	private String gender;
	
	private String regId;
	private Date regDt;
	private String updId;
	private Date updDt;
	public String getMembId() {
		return membId;
	}
	public void setMembId(String membId) {
		this.membId = membId;
	}
	public String getMembNo() {
		return membNo;
	}
	public void setMembNo(String membNo) {
		this.membNo = membNo;
	}
	public String getMembCtn() {
		return membCtn;
	}
	public void setMembCtn(String membCtn) {
		this.membCtn = membCtn;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getNewRejoinYn() {
		return newRejoinYn;
	}
	public void setNewRejoinYn(String newRejoinYn) {
		this.newRejoinYn = newRejoinYn;
	}
	public String getProductNm() {
		return productNm;
	}
	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}
	public Date getJoinDt() {
		return joinDt;
	}
	public void setJoinDt(Date joinDt) {
		this.joinDt = joinDt;
	}
	public Integer getLoginFailCnt() {
		return loginFailCnt;
	}
	public void setLoginFailCnt(Integer loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}
	public Date getLatestLoginDt() {
		return latestLoginDt;
	}
	public void setLatestLoginDt(Date latestLoginDt) {
		this.latestLoginDt = latestLoginDt;
	}
	public String getTransToken() {
		return transToken;
	}
	public void setTransToken(String transToken) {
		this.transToken = transToken;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getPayResvYn() {
		return payResvYn;
	}
	public void setPayResvYn(String payResvYn) {
		this.payResvYn = payResvYn;
	}
	public Date getPayResvDt() {
		return payResvDt;
	}
	public void setPayResvDt(Date payResvDt) {
		this.payResvDt = payResvDt;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getExpSmsSendYn() {
		return expSmsSendYn;
	}
	public void setExpSmsSendYn(String expSmsSendYn) {
		this.expSmsSendYn = expSmsSendYn;
	}
	public Date getExpSmsSendDt() {
		return expSmsSendDt;
	}
	public void setExpSmsSendDt(Date expSmsSendDt) {
		this.expSmsSendDt = expSmsSendDt;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getUpdDt() {
		return updDt;
	}
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}
	
	@Override
	public String toString() {
		return "MembVO [membId=" + membId + ", membNo=" + membNo + ", membCtn=" + membCtn + ", useStatus=" + useStatus
				+ ", useYn=" + useYn + ", newRejoinYn=" + newRejoinYn + ", productNm=" + productNm + ", joinDt="
				+ joinDt + ", loginFailCnt=" + loginFailCnt + ", latestLoginDt=" + latestLoginDt + ", transToken="
				+ transToken + ", serverId=" + serverId + ", payResvYn=" + payResvYn + ", payResvDt=" + payResvDt
				+ ", marketType=" + marketType + ", expSmsSendYn=" + expSmsSendYn + ", expSmsSendDt=" + expSmsSendDt
				+ ", birthYear=" + birthYear + ", gender=" + gender + ", regId=" + regId + ", regDt=" + regDt
				+ ", updId=" + updId + ", updDt=" + updDt + "]";
	}
}
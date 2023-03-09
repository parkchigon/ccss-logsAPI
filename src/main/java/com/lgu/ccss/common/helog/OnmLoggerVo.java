package com.lgu.ccss.common.helog;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnmLoggerVo {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String logTag			= "";
	private String transactionID	= "";
	private String logType			= "";
	private String processName		= "";
	private String hostName			= "";
	private String hostIp			= "";
	private String reqInterface		= "";
	private String reqTime			= "";
	private String reqParam			= "";
	private String respTime			= "";
	private String resultCode		= "";
	private String responseCode		= "";
	private String userAgent		= "";
	private String clientVersion	= "";
	private String clientIp			= "";
	private String userId			= "";
	private String sessionId		= "";
	private String camSessionId		= "";
	private String ctn				= "";
	private String streamingVid		= "";
	private String streamingCid		= "";
	private String serverName		= "";

	public OnmLoggerVo() {
		try {
			this.hostName = InetAddress.getLocalHost().getHostName();
			this.hostIp = InetAddress.getLocalHost().getHostAddress();
			this.serverName = System.getProperty(OnmConst.SERVER_NAME);
		} catch (Exception ex) {
			logger.error("ONMLoggerVo.init", ex);
		}
	}

	public String getLogTag() {
		return logTag;
	}

	/**
	 * Log 용도 구분용 Tag, [NEWCCTV]
	 */
	public void setLogTag(String logTag) {
		if (logTag != null)
			this.logTag = logTag;
	}

	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * transactionID, 로그 Unique ID
	 */
	public void setTransactionID(String transactionID) {
		if (transactionID != null)
			this.transactionID = transactionID;
	}

	public String getLogType() {
		return logType;
	}
	
	/**
	 * Request, Response ,구분
	 */
	public void setLogType(String logType) {
		if (logType != null)
			this.logType = logType;
	}

	public String getProcessName() {
		return processName;
	}

	/**
	 * 처리 method name
	 */
	public void setProcessName(String processName) {
		if (processName != null)
			this.processName = processName;
	}

	public String getHostName() {
		return hostName;
	}

	/**
	 * host name
	 */
	public void setHostName(String hostName) {
		if (hostName != null)
			this.hostName = hostName;
	}

	public String getHostIp() {
		return hostIp;
	}

	/**
	 * host ip
	 */
	public void setHostIp(String hostIp) {
		if (hostIp != null)
			this.hostIp = hostIp;
	}

	public String getReqInterface() {
		return reqInterface;
	}

	/**
	 * Client 호출 interface name, interface_ID
	 */
	public void setReqInterface(String reqInterface) {
		if (reqInterface != null)
			this.reqInterface = reqInterface;
	}

	public String getReqTime() {
		return reqTime;
	}

	/**
	 * YYYY-MM-DD hh:mm:ss.SSS
	 */
	public void setReqTime(String reqTime) {
		if (reqTime != null)
			this.reqTime = reqTime;
	}

	public String getReqParam() {
		return reqParam;
	}

	/**
	 * Request Parameters
	 */
	public void setReqParam(String reqParam) {
		if (reqParam != null)
			this.reqParam = reqParam;
	}

	public String getRespTime() {
		return respTime;
	}

	/**
	 * YYYY-MM-DD hh:mm:ss.SSS
	 */
	public void setRespTime(String respTime) {
		if (respTime != null)
			this.respTime = respTime;
	}

	public String getResultCode() {
		return resultCode;
	}

	/**
	 * HTTP(S) Result Code
	 */
	public void setResultCode(String resultCode) {
		if (resultCode != null)
			this.resultCode = resultCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * HE별 상세 Result Code, CCTV 상세 에러 코드
	 */
	public void setResponseCode(String responseCode) {
		if (responseCode != null)
			this.responseCode = responseCode;
	}

	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * HTTP 브라우져 및 Client의 User-Agent 정보
	 */
	public void setUserAgent(String userAgent) {
		if (userAgent != null)
			this.userAgent = userAgent;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	/**
	 * APP 클라이언트 Version정보
	 */
	public void setClientVersion(String clientVersion) {
		if (clientVersion != null)
			this.clientVersion = clientVersion;
	}

	public String getClientIp() {
		return clientIp;
	}

	/**
	 * Source IP
	 */
	public void setClientIp(String clientIp) {
		if (clientIp != null)
			this.clientIp = clientIp;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * 회원 ID
	 */
	public void setUserId(String userId) {
		if (userId != null)
			this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 사용자 로그인 세션 ID
	 */
	public void setSessionId(String sessionId) {
		if (sessionId != null)
			this.sessionId = sessionId;
	}

	public String getCamSessionId() {
		return camSessionId;
	}

	/**
	 * 카메라 세션 아이디
	 */
	public void setCamSessionId(String camSessionId) {
		if (camSessionId != null)
			this.camSessionId = camSessionId;
	}

	public String getCtn() {
		return ctn;
	}

	/**
	 * CTN 암호화 필요
	 */
	public void setCtn(String ctn) {
		if (ctn != null)
			this.ctn = ctn;
	}

	public String getStreamingVid() {
		return streamingVid;
	}

	/**
	 * 스트리밍 APP Viewer ID
	 */
	public void setStreamingVid(String streamingVid) {
		if (streamingVid != null)
			this.streamingVid = streamingVid;
	}

	public String getStreamingCid() {
		return streamingCid;
	}

	/**
	 * 스트리밍 APP CAM ID
	 */
	public void setStreamingCid(String streamingCid) {
		if (streamingCid != null)
			this.streamingCid = streamingCid;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(logTag).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(transactionID).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(logType).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(processName).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(hostName).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(hostIp).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(reqInterface).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(reqTime).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(reqParam).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(respTime).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(resultCode).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(responseCode).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(userAgent).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(clientVersion).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(clientIp).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(userId).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(sessionId).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(camSessionId).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(ctn).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(streamingVid).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(streamingCid).append(OnmConst.ELEMENT_VERTICAL_BAR)
			.append(serverName).append(OnmConst.ELEMENT_VERTICAL_BAR);
		
		return sb.toString();
	}
}

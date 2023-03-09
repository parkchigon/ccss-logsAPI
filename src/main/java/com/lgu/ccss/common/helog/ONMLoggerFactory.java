package com.lgu.ccss.common.helog;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.common.util.StringUtils;


public class ONMLoggerFactory {
	private static final Logger logger = LoggerFactory.getLogger(ONMLoggerFactory.class);
	
	public static OnmLoggerVo createONMLogger(HttpServletRequest request, HttpServletResponse response, Map respHeader, String memId, String startTime)
	{
		OnmLoggerVo loggerVo = new OnmLoggerVo();
		
		try
		{
			//Request
			String interfaceId 		= StringUtils.nvl(request.getHeader("interface_id"));
			String sessionId 		= StringUtils.nvl(request.getHeader("session_id")); //카메라 세션 ID가 있을 경우
			String camSessionId 	= StringUtils.nvl(request.getHeader("cam_session_id")); //카메라 세션 ID가 있을 경우
			String userAgent	 	= request.getHeader(OnmConst.USER_AGENT);
			
			//Response
			String resultStatus 	= (String)respHeader.get("result_status");
			
			loggerVo.setLogTag(OnmConst.LOG_TAG_NEWCCTV);
			loggerVo.setTransactionID(UUID.randomUUID().toString());
			loggerVo.setLogType(OnmConst.LOG_TYPE_RESP);
			loggerVo.setProcessName(request.getRequestURL().toString());
			
			if( interfaceId != null && interfaceId.length() > 0)
				loggerVo.setReqInterface(interfaceId);
			
			if( startTime != null && startTime.length() > 0)
				loggerVo.setReqTime(startTime);
			
			if( request.getMethod().equalsIgnoreCase(RequestMethod.POST.toString()) ){
				loggerVo.setReqParam("");
			}
			else{
				loggerVo.setReqParam(request.getPathInfo());
			}
			
			loggerVo.setRespTime(StringUtils.getCurrentDateSec());
			
			if( resultStatus != null && resultStatus.length() > 0){
				loggerVo.setResultCode(resultStatus);
				loggerVo.setResponseCode(resultStatus);
			}
			
			if (userAgent != null && userAgent.length() > 0) {
				loggerVo.setUserAgent(userAgent);
				loggerVo.setClientVersion(userAgent);
			}
			
			loggerVo.setClientIp(request.getRemoteAddr());
			
			if (memId != null && memId.length() > 0)
				loggerVo.setUserId(memId);
			
			if(sessionId != null && sessionId.length() > 0)
				loggerVo.setSessionId(sessionId);
			
			if(camSessionId != null && camSessionId.length() > 0)
				loggerVo.setCamSessionId(camSessionId);
			
			loggerVo.setCtn("");
			loggerVo.setStreamingCid("");
			loggerVo.setStreamingVid("");
			
		} catch( Exception ex )
		{
			logger.error("ONMLoggerFactory", ex);
		}
		
		return loggerVo;
	}

//	public static OnmLoggerVo createONMLogger(HttpServletRequest request, HttpServletResponse response, String interfaceId, String sessionId, String camSessionId, long reqStartTime, String userId, String streamingCID, String streamingVID)
//	{
//		OnmLoggerVo loggerVo = new OnmLoggerVo();
//		
//		try
//		{
//			loggerVo.setLogTag(OnmConst.LOG_TAG_NEWCCTV);
//			loggerVo.setTransactionID(UUID.randomUUID().toString());
//			loggerVo.setLogType(OnmConst.LOG_TYPE_RESP);
//			loggerVo.setProcessName(request.getRequestURL().toString());
//			loggerVo.setReqInterface(interfaceId);
//			loggerVo.setReqTime(StringUtils.getCurrentDateSec());
//			
//			if( request.getMethod().equalsIgnoreCase(RequestMethod.POST.toString()) ){
//				loggerVo.setReqParam("");
//			}
//			else{
//				loggerVo.setReqParam(request.getPathInfo());
//			}
//			
//			loggerVo.setRespTime(StringUtils.getCurrentDateSec());
//			
//			//TODO
//			loggerVo.setResultCode("");
//			loggerVo.setResponseCode("");
//			
//			loggerVo.setUserAgent(request.getHeader(OnmConst.USER_AGENT));
//			loggerVo.setClientVersion(request.getHeader(OnmConst.USER_AGENT));
//			
//			loggerVo.setClientIp(request.getRemoteAddr());
//			
//			if(userId != null && userId.length() > 0)
//				loggerVo.setUserId(userId);
//			
//			
//			if(sessionId != null && sessionId.length() > 0)
//				loggerVo.setSessionId(sessionId);
//			
//			if(camSessionId != null && camSessionId.length() > 0)
//				loggerVo.setCamSessionId(camSessionId);
//			
//			loggerVo.setCtn("");
//			
//			if(streamingCID != null && streamingCID.length() > 0)
//				loggerVo.setStreamingCid(streamingCID);
//			
//			if(streamingVID != null && streamingVID.length() > 0)
//				loggerVo.setStreamingVid(streamingVID);
//			
//		} catch( Exception ex )
//		{
//			logger.error("ONMLoggerFactory", ex);
//		}
//		
//		return loggerVo;
//	}
}

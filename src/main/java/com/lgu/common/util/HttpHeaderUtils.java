package com.lgu.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import com.lgu.common.trace.TraceConst;



public class HttpHeaderUtils {
	public static void setHeaderInfo (String resStatus, HttpServletRequest req, HttpServletResponse res){	
	    res.setHeader("interface_id", req.getHeader("interface_id"));
	    res.setHeader("result_status", resStatus);
	    res.setCharacterEncoding("UTF-8");
		res.setHeader("Cache-Control", "no-cache");
		MDC.put("RESULT_STATUS", resStatus);
	}
	
	public static String makeRemoteSvrName(String svrName, String remoteIp){
		String srcName = null;
		
		if( svrName != null && svrName.length() > 0 ){
			srcName = svrName + TraceConst.TRACE_LEFT_PARENTHESIS + remoteIp + TraceConst.TRACE_RIGHT_PARENTHESIS;
		}
		else{
			srcName = TraceConst.TRACE_LEFT_PARENTHESIS + remoteIp + TraceConst.TRACE_RIGHT_PARENTHESIS;
		}
		
		return srcName;
	}
	
	public static String makeLocalSvrName(String svrName, String hostIp){
		String dstName = null;
		
		if( svrName != null && svrName.length() > 0 ){
			dstName = svrName + TraceConst.TRACE_LEFT_PARENTHESIS + hostIp + TraceConst.TRACE_RIGHT_PARENTHESIS;
		}else{
			dstName = TraceConst.TRACE_LEFT_PARENTHESIS + hostIp + TraceConst.TRACE_RIGHT_PARENTHESIS;
		}
		
		return dstName;
	}
	
	public static String getClientIPAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR"); 
	     
/*	     if (ip == null || ip.length() == 0) {
	         ip = request.getHeader("Proxy-Client-IP");
	     }

	     if (ip == null || ip.length() == 0) {
	         ip = request.getHeader("WL-Proxy-Client-IP"); 
	     }*/

	     if (ip == null || ip.length() == 0) {
	         ip = request.getRemoteAddr() ;
	     }
		return ip;
	}
	
}

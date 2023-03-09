package com.lgu.ccss.common.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.common.http.HttpRequestWrapper;
import com.lgu.common.tlo.TloWriter;
import com.lgu.common.trace.TraceConst;
import com.lgu.common.trace.TraceWriter;
import com.lgu.common.util.StringUtils;

@Component
public class HttpLogService {

	@Autowired
	private TloWriter tloWriter;

	@Autowired
	private TraceWriter traceWriter;

	public void setRequestLog(HttpServletRequest request) {
		HttpRequestWrapper reqWrapper = (HttpRequestWrapper) request;

		byte[] body = reqWrapper.getBodyData();
		String content = new String(body, 0, body.length);
		
		Gson gson = new Gson();
/*		Gson gson = new GsonBuilder()
				.setLenient()
				.create();*/
		try {
			RequestJSON apiReq = gson.fromJson(content, RequestJSON.class);
			//String requestUrl = StringUtils.nvl(request.getRequestURI());
			
			setRequestTrace(reqWrapper, apiReq);
			//setRequestTloData(reqWrapper, apiReq);
		} catch (Exception e) {
		}
	}
	
/*	public void setResponseLog(HttpServletRequest request, HttpServletResponse response) {
		HttpResponseWrapper resWrapper = (HttpResponseWrapper) response;

		String content = resWrapper.getResponseText();

		Gson gson = new Gson();
		ResponseJSON apiRes = gson.fromJson(content, ResponseJSON.class);

		setResponseTrace(resWrapper);
		setResponseTloData(apiRes);

		tloWriter.write(TloUtil.getTloData());
	}*/
	
/*	private void setRequestTloData(HttpRequestWrapper reqWrapper, RequestJSON apiReq) {
		RequestCommonJSON common = apiReq.getCommon();

		Map<String, String> tlo = new HashMap<String, String>();
		tlo.put(TloData.LOG_TYPE, "SVC");
		tlo.put(TloData.SID, CCSSUtil.getCtn());

		tlo.put(TloData.REQ_TIME, TloData.getNowDate());

		tlo.put(TloData.CLIENT_IP, reqWrapper.getRemoteAddr());
		tlo.put(TloData.DEV_INFO, common.getLogData().getDevInfo());
		tlo.put(TloData.OS_INFO, common.getLogData().getOsInfo());
		tlo.put(TloData.NW_INFO, common.getLogData().getNwInfo());
		tlo.put(TloData.SVC_NAME, common.getLogData().getSvcName());
		tlo.put(TloData.DEV_MODEL, common.getLogData().getDevModel());
		tlo.put(TloData.CARRIER_TYPE, common.getLogData().getCarrierType());
		tlo.put(TloData.SESSION_ID, CCSSUtil.getCcssToken());
		tlo.put(TloData.SERVER_ID, serverId);
		tlo.put(TloData.MEMB_ID, CCSSUtil.getMembId());
		tlo.put(TloData.MEMB_NO, CCSSUtil.getMembNo());
		tlo.put(TloData.DEVICE_TYPE, common.getDevice().getDeviceType());
		tlo.put(TloData.APP_TYPE, common.getDevice().getAppType());
		tlo.put(TloData.CAR_OEM, common.getDevice().getCarOem());
		tlo.put(TloData.CLIENT_ID, CCSSUtil.getSerial());

		tlo.put(TloData.SVC_CLASS, TloUtil.getSvcClass(common.getApiId()));

		TloUtil.setTloData(tlo);
	}*/
	
/*	private void setResponseTloData(ResponseJSON apiRes) {
		Map<String, String> tlo = new HashMap<String, String>();
		tlo.put(TloData.RSP_TIME, TloData.getNowDate());
		tlo.put(TloData.RESULT_CODE, apiRes.getResultCode());

		TloUtil.setTloData(tlo);
	}*/
	
	private void setRequestTrace(HttpRequestWrapper reqWrapper, RequestJSON apiReq) {

		//String source = apiReq.getLog()..getCommon().getDevice().getDeviceType() + ":"
		//		+ apiReq.getCommon().getDevice().getAppType();

		String source = "source";
		String ctn = "ctn";
		traceWriter.trace(ctn, source, TraceConst.NODE_ID_WAS, reqWrapper);

		//MDC.put(MDCConst.TRACE_SOURCE, source);
	}
	
/*	private void setResponseTrace(HttpResponseWrapper resWrapper) {

		traceWriter.trace(CCSSUtil.getCtn(), TraceConst.NODE_ID_WAS, MDC.get(MDCConst.TRACE_SOURCE), resWrapper);
	}*/
}
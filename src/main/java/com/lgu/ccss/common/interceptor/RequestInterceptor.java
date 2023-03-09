package com.lgu.ccss.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.lgu.ccss.common.constant.CCSSConst;
import com.lgu.ccss.common.constant.HeaderConst;
import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.ccss.common.model.ResponseJSON;
import com.lgu.ccss.common.log.HttpLogService;
import com.lgu.ccss.common.tlo.TloData;
import com.lgu.ccss.common.tlo.TloUtil;
import com.lgu.common.http.HttpRequestWrapper;
import com.lgu.common.http.HttpResponseWrapper;
import com.lgu.common.tlo.TloWriter;
import com.lgu.common.util.StringUtils;


/**
 * 컨트롤러의 전반적인 전후 처리를 위한 인터셉터 클래스. <br/>
 * 시스템 점검, 요청 정보 등을 다룬다.
 * 
 */
public class RequestInterceptor extends HandlerInterceptorAdapter implements MessageSourceAware {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

	@Value("#{systemProperties.SERVER_ID}")
	private String serverId;	
	
	@Autowired
	private TloWriter tloWriter;
	
	@Autowired
	private HttpLogService httpLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("requestURL:" + request.getRequestURI());
		
		try {
			String requestUrl = StringUtils.nvl(request.getRequestURI());
			
			if (requestUrl.startsWith("/api/internal")) {
				return true;
			}
			
			// TLO Write
			HttpRequestWrapper reqWrapper = (HttpRequestWrapper) request;
			httpLogService.setRequestLog(request);
			
			Map<String, String> tlo = new HashMap<String, String>();
			tlo.put(TloData.CLIENT_IP, reqWrapper.getRemoteAddr());
			tlo.put(TloData.SLOG_REQ_TIME, TloData.getNowDate());
			
			TloUtil.setTloData(tlo);
		}
		catch (Exception e) {
			logger.error("Exception({})", e);
		}

		return super.preHandle(request, response, handler);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		
		logger.debug("requestURL:" + request.getRequestURI());
		
		try {
			String requestUrl = StringUtils.nvl(request.getRequestURI());
			
			if (requestUrl.startsWith("/api/internal")) {
				return;
			}
			
			HttpResponseWrapper resWrapper = (HttpResponseWrapper) response;
			String content = resWrapper.getResponseText();

			Gson gson = new Gson();
			ResponseJSON apiRes = gson.fromJson(content, ResponseJSON.class);

			Map<String, String> tlo = new HashMap<String, String>();
			tlo.put(TloData.RSP_TIME, TloData.getNowDate());
			tlo.put(TloData.RESULT_CODE, apiRes.getResultCode());
			tlo.put(TloData.SLOG_RES_TIME, TloData.getNowDate());

			TloUtil.setTloData(tlo);
			
			tloWriter.write(TloUtil.getTloData());

		}
		catch (Exception e) {
			logger.error("Exception({})", e);
			
		} finally {
			MDC.clear();
		}		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void setMessageSource(MessageSource arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
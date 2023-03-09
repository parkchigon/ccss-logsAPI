package com.lgu.common.trace.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgu.common.trace.model.TraceInfoVO;
import com.lgu.common.trace.service.TraceService;

@Controller
@RequestMapping(value = "/api/internal/trace")
public class TraceInfoController {
	private static final Logger logger = LoggerFactory.getLogger(TraceInfoController.class);

	@Resource(name = "traceService")
	private TraceService traceService;

	@RequestMapping(value = "/traceInfoList", method = RequestMethod.POST)
	@ResponseBody
	public List<TraceInfoVO> traceInfoList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody TraceInfoVO requestBody) throws Exception {

		List<TraceInfoVO> resJson = new ArrayList<TraceInfoVO>();

		try {
			List<TraceInfoVO> list = traceService.getTraceInfo();
			if (list != null && list.size() > 0) {
				resJson = list;
			} 
			
		} catch (Exception e) {
			logger.error("Exception", e);

		} 

		return resJson;
	}
}

package com.lgu.ccss.log.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.ccss.common.model.ResponseJSON;
import com.lgu.ccss.log.api.service.LogService;

@Controller
@RequestMapping(value = "/api")
public class LogController {
	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	
	@Resource(name = "logService")
	private LogService logService;

	@RequestMapping(value = "/log", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJSON reqLogin(@RequestHeader HttpHeaders headers, @RequestBody RequestJSON reqJson) throws Exception {
		
		return logService.doService(headers, reqJson);
	}

}

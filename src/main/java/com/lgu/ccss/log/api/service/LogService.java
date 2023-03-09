package com.lgu.ccss.log.api.service;

import org.springframework.http.HttpHeaders;

import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.ccss.common.model.ResponseJSON;

public interface LogService {
	public ResponseJSON doService(HttpHeaders headers, RequestJSON reqJson);
}

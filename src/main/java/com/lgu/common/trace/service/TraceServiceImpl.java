package com.lgu.common.trace.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.lgu.common.trace.model.TraceInfoVO;

import devonframe.dataaccess.CommonDao;

@Service("traceService")
public class TraceServiceImpl implements TraceService {
		
	@Resource(name = "commonDao_oracle")
	private CommonDao commonDao_oracle;
	
	public List<TraceInfoVO> getTraceInfo(){
		return commonDao_oracle.selectList("Trace.getTraceInfo");
	}

}

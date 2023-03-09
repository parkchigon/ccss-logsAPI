package com.lgu.common.trace;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.lgu.common.trace.model.TraceInfoVO;
import com.lgu.common.trace.service.TraceService;


@Controller
public class TraceManager {
	private static final Logger logger = LoggerFactory.getLogger(TraceManager.class);
	private static ConcurrentHashMap<String, TraceInfoVO> traceMap = new ConcurrentHashMap<String, TraceInfoVO>();
	
	@Resource(name = "traceService") 
	private TraceService traceService;
	
	@Scheduled(fixedDelay=300 * 1000)
	public void checkTraceInfo(){
		
		logger.info("CHECK TRACE LOG");
		
		List<TraceInfoVO> traceInfoList = traceService.getTraceInfo();
		
		if (traceInfoList != null && traceInfoList.size() > 0){
			for (TraceInfoVO traceInfoVo : traceInfoList){
				logger.debug("[TraceManager.checkTraceInfo] traceInfo  " + traceInfoVo.toString());
				
				if( traceInfoVo.getDeviceCtn() != null ){
					TraceManager.putTraceInfoByCtn(traceInfoVo.getDeviceCtn(), traceInfoVo);
				}
			}
		}
		else{
			logger.info("[TraceManager.checkTraceInfo] trace is null or size is zero");
		}
	}
	
	public static TraceInfoVO putTraceInfoByCtn(String key, TraceInfoVO traceVo) {
		return traceMap.put(key, traceVo);
	}
	
	public static TraceInfoVO getTraceInfo(String key) {
		return traceMap.get(key);
	}
	
	public static TraceInfoVO removeTraceInfoByCtn(String key) {
		return traceMap.remove(key);
	}
}

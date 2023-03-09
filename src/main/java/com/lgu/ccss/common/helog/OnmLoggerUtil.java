package com.lgu.ccss.common.helog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnmLoggerUtil {
	private static final Logger logger = LoggerFactory.getLogger("onmlogger"); 
	
	public static void write(String text){
		logger.info(text);
	}
}

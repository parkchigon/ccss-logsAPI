package com.lgu.common.util;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MathUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(MathUtils.class);
		
	/**
	 * String value -> Kbyte, Mbyte, Gbyte, Tbyte 로 변환
	 * @param String value
	 * @param String type : K,M,G,T
	 * @return int
	 */
	public static int convByteStrToHumenFileSize(String value, String type){
		int result = -1;
		int loopCnt = 0;
		BigInteger bigInt = new BigInteger(StringUtils.nvl(value, "0"));
		
		try {
			if("K".equals(type) || "k".equals(type)){
				loopCnt = 1;
			}else if("M".equals(type) || "m".equals(type)){
				loopCnt = 2;
			}else if("G".equals(type) || "g".equals(type)){
				loopCnt = 3;
			}else if("T".equals(type) || "t".equals(type)){
				loopCnt = 4;
			}
			
			for(int i = 0; i < loopCnt ; i++){
				bigInt = bigInt.divide(BigInteger.valueOf(1024));
				result = bigInt.intValue();
			}
			
		} catch (Exception e) {
			logger.error("Exception", e);
			result = -1;
		}
		
		return result;
	}
}

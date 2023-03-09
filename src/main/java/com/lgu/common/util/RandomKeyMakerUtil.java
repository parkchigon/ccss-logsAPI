package com.lgu.common.util;

import java.util.GregorianCalendar;

import sun.misc.BASE64Encoder;

public class RandomKeyMakerUtil {
	public static String sessionIdGen(String ctn, String memNo){
    	String sessionId = "";
    	String key1 = ctn.substring(ctn.length() - 4, ctn.length());
    	
    	GregorianCalendar gc = new GregorianCalendar ();
    	String millis = String.valueOf(gc.getTimeInMillis());
    	String key2 = millis.substring(millis.length()-11, millis.length());
    	
    	String key = key1 + key2;
    	
    	BASE64Encoder bse = new BASE64Encoder();
    	String basekey = bse.encode(key.getBytes());
    	basekey = basekey.substring(basekey.length() - 15, basekey.length());
    	
    	String zero = "0000";

   		memNo = memNo.length() < 5 ? zero.substring(0, 5-memNo.length())+memNo : memNo;
   		   		
   		if((memNo.length() + basekey.length()) <= 20) {
   			sessionId = basekey+memNo;
   		}else{
   			sessionId = basekey+memNo.substring(basekey.length() + memNo.length() - 20, memNo.length());
   		}
   		return sessionId;
    }
	
	public static String securityKeyGen(String ctn, String memNo){
    	String securityKey = "";
    	String key1 = ctn.substring(ctn.length() - 8, ctn.length());
    	
    	GregorianCalendar gc = new GregorianCalendar ();
    	String millis = String.valueOf(gc.getTimeInMillis());
    	String key2 = millis.substring(millis.length()-11, millis.length());
    	
    	String key = key1 + key2;
    	
    	BASE64Encoder bse = new BASE64Encoder();
    	String basekey = bse.encode(key.getBytes());
    	basekey = basekey.substring(basekey.length() - 28, basekey.length());
    	String zero = "0000";

   		memNo = memNo.length() < 5 ? zero.substring(0, 5-memNo.length())+memNo : memNo;
   		if((memNo.length() + basekey.length()) <= 32) {
   			securityKey = basekey+memNo;
   		}else{
   			securityKey = basekey+memNo.substring(basekey.length() + memNo.length() - 32, memNo.length());
   		}
   		return securityKey;
    }
	
	public static String generate(String ctn){
    	String key1 = ctn.substring(ctn.length() - 8, ctn.length());
    	
    	GregorianCalendar gc = new GregorianCalendar ();
    	String millis = String.valueOf(gc.getTimeInMillis());
    	String key2 = millis.substring(millis.length()-12, millis.length());
    	String key = key1 + key2;
    	
    	BASE64Encoder bse = new BASE64Encoder();
    	String basekey = bse.encode(key.getBytes());
    	basekey = basekey.substring(basekey.length() - 15, basekey.length());

    	return basekey;
    }
	
	public static void main(String[] args) {
		System.out.println("sessionIdGen : " + sessionIdGen("01041747365", "011110001"));
		System.out.println("generate : " + generate("01012341234"));
		System.out.println("securityKey : " + securityKeyGen("01041747365", "012345678912"));
		System.out.println("securityKey : " + securityKeyGen("01041747365", "0"));
	}
}

package com.lgu.common.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public class CommMessageUtil implements MessageSourceAware {
	private static final Logger logger = LoggerFactory.getLogger(CommMessageUtil.class);
	
	private static MessageSource messageSource;
	private static boolean isUseChar = false;
	private static String preFixChar = "";
	private static String postFixChar = "_";
	
	public CommMessageUtil(){}
	
	CommMessageUtil(boolean isDebugMode) {
		CommMessageUtil.isUseChar = isDebugMode;
	}
	
	/**
	 * return message by msgId
	 *
	 * @author cp92148
	 * @param msgId
	 * @return message
	 */
	public static String getMessage(String msgId){
		try{
			System.out.println("#########################################");
			System.out.println("msgID : "+msgId);
			System.out.println("Locale.getDefault() : "+Locale.getDefault());
			System.out.println("messageSource : "+messageSource);
			String retMsg = messageSource.getMessage(msgId, null, Locale.getDefault());
			return addFixMsg(retMsg);
		}catch(Exception e){
			logger.error("Exception", e);
			return excetionMsg(msgId);
		}
	}

	/**
	 * return message by msgId, Locale
	 *
	 * @param msgId
	 * @param locale
	 * @return message
	 */
	public static String getMessage(String msgId, Locale locale){
		try{
			String retMsg = messageSource.getMessage(msgId, null, locale); 
			return addFixMsg(retMsg);
		}catch(Exception e){
			logger.error("Exception", e);
			
			return excetionMsg(msgId);
		}
	}

	/**
	 * return message by msgId, arguments
	 * if message is null, Return a message replace the defaultValue parameter.
	 *
	 * @param msgId Message ID
	 * @param defaultValue Message to use if the value of the message is null
	 * @param args String array for argments in message.
	 * @return message
	 */
	public static String getMessage(String msgId, String defaultValue, Object... args){
		try{
			String retMsg = messageSource.getMessage(msgId, args, defaultValue, Locale.getDefault());
			
			return addFixMsg(retMsg);
		}catch(Exception e){
			logger.error("Exception", e);
			return excetionMsg(msgId);
		}
	}

	/**
	 * return message by msgId, locale, arguments
	 * if message is null, Return a message replace the defaultValue parameter.
	 *
	 * @param msgId Message ID
	 * @param defaultValue Message to use if the value of the message is null
	 * @param locale Any locale you want to use.
	 * @param args String array for argments in message.
	 * @return message
	 */
	public static String getMessage(String msgId, String defaultValue, Locale locale, Object... args){
		try{
			String retMsg = messageSource.getMessage(msgId, args, defaultValue, locale);
			return addFixMsg(retMsg);
		}catch(Exception e){
			logger.error("Exception", e);
			return excetionMsg(msgId);
		}
	}
	
	/**
	 * add prefix or postfix char to paramater
	 * 
	 * @param msg
	 * @return 
	 */
	private static String addFixMsg(String msg){
		if(!isUseChar) return msg;
		
		StringBuilder sb = new StringBuilder();
		if(!preFixChar.equals(""))sb.append(preFixChar);
		sb.append(msg);
		if(!postFixChar.equals(""))sb.append(postFixChar);
		
		return sb.toString();
	}
	
	public static String excetionMsg(String msgId){
		return "[*"+msgId+"]";
	}

	public void setMessageSource(MessageSource messageSrc){
		CommMessageUtil.messageSource = messageSrc;
	}

}

package com.lgu.common.trace;

public class TraceConst
{
	public static String defaultCharset = "UTF-8";
	
	/**
	 * Common Header NodeID Value
	 */
	
	public static final String NODE_ID_WAS = "WAS";
	public static final String NODE_ID_ESB = "ESB";
	public static final String NODE_ID_NCAS = "NCAS";
	public static final String NODE_ID_AI_AUTH = "AI_AUTH";
	public static final String NODE_ID_AI_WEATHER = "AI_WEATHER";
	
	public static final String PROTOCOL_HTTP = "HyperText Transfer Protocol";
	public static final String PROTOCOL_SOAP = "Simple Object Access Protocol";
	
	public static final String ELEMENT_VERTICAL_BAR	= "|";
	public static final int TRACE_SPLITT_SIZE		= 6;
	public static final int SUB_STR_LENGTH			= 6;
	
	public static String TRACE_PATH					= null;
	
	public static final String TRACE_HYPHEN		= "-------------------------------------------------"; 
	public static final String TRACE_LEFT_BRAKET		= " [ "; 
	public static final String TRACE_RIGHT_BRAKET		= " ] "; 
	public static final String TRACE_TRIPLE_GT			= " >>> ";
	
	public static final String TRACE_VERTICAL_BAR		= "|";
	public static final String TRACE_COLON				= " : ";
	public static final String COLON					= ":";
	public static final String TRACE_NEXT_LINE			= "\r\n";
	
	public static final String TRACE_STRING_FORMAT		= "%15s";
	
	public static String TRACE_SERVER_INFO					= null;
	
	public static final String TRACE_LEFT_PARENTHESIS		= "(";
	public static final String TRACE_RIGHT_PARENTHESIS		= ")";
}
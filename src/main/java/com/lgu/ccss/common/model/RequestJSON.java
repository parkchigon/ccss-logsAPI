package com.lgu.ccss.common.model;

import java.util.Arrays;


public class RequestJSON {
	public static final String USE_Y = "Y";
	public static final String USE_N = "N";
	
	public static final String PARAM_SERIAL = "serial";
	public static final String PARAM_CTN = "ctn";
	public static final String PARAM_AI_TOKEN = "aiToken";
	public static final String PARAM_CCSS_TOKEN = "ccssToken";
	public static final String PARAM_IS_AGREE = "isAgree";
	public static final String PARAM_VERSION = "version";
	public static final String PARAM_BODY = "body";
	public static final String PARAM_ESN = "esn";
	public static final String PARAM_USIM_MODEL = "usimModel";
	public static final String PARAM_USIM_SN = "usimSn";
	public static final String PARAM_FIRMWARE_INFO = "firmwareInfo";
	public static final String PARAM_TERMS_NO = "termsNo";
	public static final String PARAM_SERVICE_CODE = "serviceCode";
	public static final String PARAM_LOGIN_TYPE = "loginType";
	public static final String PARAM_AUTH_PARAMETER1 = "authParameter1";
	public static final String PARAM_AUTH_PARAMETER2 = "authParameter2";
	public static final String PARAM_AUTH_PARAMETER3 = "authParameter3";
	public static final String PARAM_AUTH_PARAMETER4 = "authParameter4";
	public static final String PARAM_AUTH_PARAMETER5 = "authParameter5";
	public static final String PARAM_AUTH_PARAMETER6 = "authParameter6";
	public static final String PARAM_AUTH_PARAMETER7 = "authParameter7";
	public static final String PARAM_AUTH_PARAMETER8 = "authParameter8";
	public static final String PARAM_AUTH_PARAMETER9 = "authParameter9";
	public static final String PARAM_AUTH_PARAMETER10 = "authParameter10";

	private RequestDataJSON[] log;
	

	public RequestDataJSON[] getLog() {
		return log;
	}

	public void setLog(RequestDataJSON[] log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return "RequestJSON [log=" + Arrays.toString(log) + "]";
	}


}

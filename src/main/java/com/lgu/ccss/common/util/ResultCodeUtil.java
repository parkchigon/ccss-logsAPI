package com.lgu.ccss.common.util;

import com.google.gson.Gson;
import com.lgu.ccss.common.model.ResponseJSON;
import com.lgu.common.model.ResultCode;


public final class ResultCodeUtil {
	
	public final static ResultCode RC_2S000000 = new ResultCode("2S000000", "성공");
	public final static ResultCode RC_3L000000 = new ResultCode("3L000000", "파라미터 오류");
	public final static ResultCode RC_4L000000 = new ResultCode("4L000000", "서버 내부 오류");
	public final static ResultCode RC_4L000003 = new ResultCode("4L000003", "네크워크 접속 오류");	

	public static ResponseJSON createResultMsg(ResultCode resultCode) {
		ResponseJSON resp = new ResponseJSON();
		resp.setResultCode(resultCode.getCode());
		resp.setResultMsg(resultCode.getMsg());
		
		return resp;
	}
	
	
	public static ResponseJSON createResultMsg(ResultCode resultCode, String subReason) {
		ResponseJSON resp = new ResponseJSON();
		resp.setResultCode(resultCode.getCode());
		
		if( subReason != null )
			resp.setResultMsg(resultCode.getMsg() + " | " + subReason);
		else
			resp.setResultMsg(resultCode.getMsg());
		
		return resp;
	}
	
	public static ResponseJSON createResultMsg(ResultCode resultCode, String subReason, Object resultData) {
		ResponseJSON resp = new ResponseJSON();
		resp.setResultCode(resultCode.getCode());
		resp.setResultData(resultData);
		
		if( subReason != null )
			resp.setResultMsg(resultCode.getMsg() + " | " + subReason);
		else
			resp.setResultMsg(resultCode.getMsg());
		
		return resp;
	}
}

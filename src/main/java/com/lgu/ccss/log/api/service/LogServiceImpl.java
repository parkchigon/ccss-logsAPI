package com.lgu.ccss.log.api.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.client.utils.CloneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lgu.ccss.common.constant.CCSSConst;
import com.lgu.ccss.common.constant.HeaderConst;
import com.lgu.ccss.common.constant.MDCConst;
import com.lgu.ccss.common.model.RequestDataJSON;
import com.lgu.ccss.common.model.RequestDeviceJSON;
import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.ccss.common.model.ResponseJSON;
import com.lgu.ccss.common.model.ResultElasticJSON;
import com.lgu.ccss.common.tlo.TloData;
import com.lgu.ccss.common.tlo.TloUtil;
import com.lgu.ccss.common.util.ResultCodeUtil;
import com.lgu.ccss.common.validation.CheckResultData;
import com.lgu.ccss.common.validation.ValidationChecker;
import com.lgu.common.util.AES256Util;
import com.lgu.common.http.HttpRequestWrapper;
import com.lgu.common.model.ResultCode;
import com.lgu.common.util.AES256LogUtil;
import com.lgu.common.util.StringUtils;

@Service("logService")
public class LogServiceImpl implements LogService {
	private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	private static final Logger log_logger = LoggerFactory.getLogger("serviceLoglogger");
	private static final Logger bdp_logger = LoggerFactory.getLogger("bdpLoglogger");
	private static final Logger es_logger = LoggerFactory.getLogger("esLoglogger");
	private static final Logger log_err_logger = LoggerFactory.getLogger("serviceErrLoglogger");

	//private ValidationChecker checker = new ValidationChecker();
	
	@Value("#{systemProperties.SERVER_ID}")
	private String serverId;	
	
	@Autowired
	private ValidationChecker validationChecker;
	
/*	
	@Resource(name = "commonDao_oracle")
	private CommonDao commonDao_oracle;*/
	
	@Override
	public ResponseJSON doService(HttpHeaders headers, RequestJSON reqJson) {
		
		CheckResultData check;
		String resonList = "";

		//serviceLog = serviceLog.replaceAll("\\p{Space}", "");
		//serviceLog = serviceLog.replaceAll("\r\n", "");
		
		logger.debug("serviceLog\r\n" + reqJson.toString());
		
		
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();   // Unicode ???????????? ('=' -> '\u003d' ??????)
		String json = gson.toJson(reqJson);
		
		// request log ???????????? 
		log_logger.debug(json);
		
		// ?????? ??? check
		check = validationChecker.checkHTTPHeaderValidation(headers);
		
		// ?????? Key
		String enckey = headers.getFirst(HeaderConst.HTTP_HEADER_CCSS_TOKEN);
		
		if (check.isStatus() == false) {
			logger.error("serviceLog\r\n" + reqJson.toString());
			logger.error("checkHTTPHeaderValidation! " + ResultCodeUtil.RC_3L000000 + " [HttpHeaders:] "+headers.toString());
			logger.error("checkHTTPHeaderValidation! {} ##HttpHeaders:{} ##JSON:{}",ResultCodeUtil.RC_3L000000,headers.toString(),json);
			return ResultCodeUtil.createResultMsg(ResultCodeUtil.RC_3L000000, check.getReason());
		}

		// log[..] list
		RequestDataJSON[] logList = reqJson.getLog();

		// log????????? ?????? ??????
		if (logList.length == 0) {
			check = validationChecker.checkValue(null,"empty log{}");
			logger.error("empty log {} ", ResultCodeUtil.RC_3L000000);
			return ResultCodeUtil.createResultMsg(ResultCodeUtil.RC_3L000000, check.getReason());
		}
		
		
		RequestDataJSON log = null;
		RequestDeviceJSON device = null;
		String realLatx;
		String realLaty;
		String logJson;
		boolean bFail;
		// ????????? Json : tlo??? ????????? ??????
		RequestDataJSON failLog = null;
				
		for (int i=0; i<logList.length; i++) 
		{
			bFail = false;
			log = logList[i];

			device = log.getDevice();

			// body[log] Json ??? check
			try {
				check = validationChecker.checkLogValidation(log);
				if (check.isStatus() == false) {	
					logger.error("Log[" + i + "] " + check.getReason() + " " + ResultCodeUtil.RC_3L000000 + " [JSON:] "+logList[i].toString());
					logger.error("Log[{}] {} {} ##JSON:{}", i, check.getReason(), ResultCodeUtil.RC_3L000000, logList[i].toString());
					resonList = resonList + " Log[" + i + "] " + check.getReason() + ",";
					bFail = true;
					//continue;		//?????? ????????? ???????????? ?????? ??????
				}
				
				if (device.getSaVer().trim().equals(CCSSConst.EMPTY) || device.getSaVer().trim().equals("N/A")) {
					device.setSaVer("N/A");
					log.setDevice(device);
				}
				
			} catch (Exception e) {
				logger.error(" Log[{}] Request Check Exception!! ##Exception{} ##JSON:{}", i, e, logList[i].toString());
				resonList = resonList + " Log[" + i + "] Request Check Exception!!,";
				bFail = true;
				//continue;		//?????? ????????? ???????????? ?????? ??????
			}
			
			// position x, y ?????????
			try {
				/*String realLatx2 = AES256Util.AESEncode(CCSSConst.LOCATION_ENC_KEY, log.getPosX());
				String realLaty2 = AES256Util.AESEncode(CCSSConst.LOCATION_ENC_KEY, log.getPosY());
				System.out.println(realLatx2 + ":" + realLaty2);*/
				
				// poxX,poxY ???????????? ?????? ?????? "0"(???????????????)?????? ??????????????? ??????????????? ?????????
				if (log.getPosX().equals("0") || log.getPosY().equals("0")) {
					log.setPosX("0");
					log.setPosY("0");
				} else {
					boolean fixedKey16 = false; 
					// 16byte ?????? ??? [?????? ????????????]
					if (fixedKey16) {
						realLatx = AES256Util.AESDecode(CCSSConst.LOCATION_ENC_KEY, log.getPosX());
						realLaty = AES256Util.AESDecode(CCSSConst.LOCATION_ENC_KEY, log.getPosY());
					} else {
						
						//TEST
						/*String ccss_token = "jU0MTY2NzczMjM516289";
						//String ccss_token = "jUzNTg4MDQ0MTM0a50c3000000000000";
						String posx = "ZvRrJUIOYXvsfWIVCDPPLw==";
						String posy = "S4/NX84QkfBMh+5HsdwReg==";
						System.out.println("###################################################################");
						String desx1 = AES256LogUtil.AESDecode(ccss_token, posx);
						String desy1 = AES256LogUtil.AESDecode(ccss_token, posy);
						System.out.println("desx1:"+desx1);
						System.out.println("desy1:"+desy1);
						System.out.println("###################################################################");*/
						/*System.out.println("###################################################################");
						String desx2 = AES256LogUtil.AESDecode(ccss_token, posx);
						String desy2 = AES256LogUtil.AESDecode(ccss_token, posy);
						System.out.println("desx2:"+desx2);
						System.out.println("desy2:"+desy2);
						System.out.println("###################################################################");*/

						/*realLatx = AES256LogUtil.AESEncode(enckey, log.getPosX());
						realLaty = AES256LogUtil.AESEncode(enckey, log.getPosY());
						System.out.println("realLatx1: "+realLatx);
						System.out.println("realLaty1: "+realLaty);
						realLatx = AES256LogUtil.AESDecode(enckey, realLatx);
						realLaty = AES256LogUtil.AESDecode(enckey, realLaty);
						System.out.println("realLatx2: "+realLatx);
						System.out.println("realLaty2: "+realLaty);*/
						
						
						realLatx = AES256LogUtil.AESDecode(enckey, log.getPosX());
						realLaty = AES256LogUtil.AESDecode(enckey, log.getPosY());
					}
					
					//System.out.println("log.getPosX():"+realLatx);	//127.110756
					//System.out.println("log.getPosY():"+realLaty);	//37.402966
					// ?????????????????????
					//System.out.println("realLatx::"+realLatx+"::");
					//System.out.println("realLaty::"+realLaty+"::");
					
					log.setPosX(realLatx);
					log.setPosY(realLaty);
				}
			} catch (Exception e) {
				System.out.println(e);
				logger.error(" Log[{}] Position Encryption Error!! ##JSON:{}", i, logList[i].toString());
				resonList = resonList + " Log[" + i + "] Position Encryption Error!!;";
				bFail = true;
				//continue;		//?????? ????????? ?????????????????? ??????
			}
			
			check = validationChecker.checkDetailValidation(log.getSvcDetailInfo(), device.getAppType());	
			if (check.isStatus() == false) {
				logger.error("Log[{}] {} {} ##JSON:{}", i, check.getReason(), ResultCodeUtil.RC_3L000000, logList[i].toString());
				resonList = resonList + " Log[" + i + "] " + check.getReason() + ";";
				bFail = true;
				//continue;
			}

			if (!bFail) {
				////////////////////////////
				// ????????? ?????? ?????? bdp log??? ??????//
				////////////////////////////
				logJson = gson.toJson(log);
				bdp_logger.trace(logJson);
				
				//////////////////////////////////////
				// ????????? ?????? ?????? elasticsearch log??? ??????//
				//////////////////////////////////////
				if (!createElasticLog(headers, log, i)) {
					logger.error(" Log[{}] createElasticLog() Exception!! ##JSON:{}", i, logList[i].toString());
					continue;
				}
			} 
			
		}
		
		// ?????? ?????? ?????? ?????? ???,
		if (!resonList.isEmpty()) {
			// ????????? ?????? log TLO??????
			setRequestTloData(log, ResultCodeUtil.RC_3L000000);
			
			log_err_logger.trace("# response message:{}\nRequestJSON:{}",resonList,json);
			return ResultCodeUtil.createResultMsg(ResultCodeUtil.RC_3L000000, resonList);
		// ?????? ?????????
		} else {
			// ????????? ?????? log TLO??????
			setRequestTloData(log, ResultCodeUtil.RC_2S000000);
		}
		
		return ResultCodeUtil.createResultMsg(ResultCodeUtil.RC_2S000000, null);
	}
	
	// Elasticsearch ????????? ?????? ?????????
	public boolean createElasticLog(HttpHeaders headers, RequestDataJSON log, int logNo) {
	
		CheckResultData check = null;
		
		try {
			ResultElasticJSON resultElasticJSON = new ResultElasticJSON();

			//String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			long millsTime = System.currentTimeMillis();
			
			Gson gson = new Gson();
			RequestDeviceJSON device = log.getDevice();
			
			resultElasticJSON.setLogTime(log.getLogTime());
			// elasticsearch??? ?????? ????????? ???????????? ?????? log??? ?????? ??????
			resultElasticJSON.setLogNo(StringUtils.LPAD(logNo+"", 3, '0'));			
			resultElasticJSON.setDeviceType(device.getDeviceType());
			resultElasticJSON.setAppType(device.getAppType());
			resultElasticJSON.setAppVer(device.getAppVer());
			resultElasticJSON.setSaVer(device.getSaVer());
			resultElasticJSON.setCarOem(device.getCarOem());
			resultElasticJSON.setCtn(log.getCtn());
			resultElasticJSON.setUserCtn(log.getUserCtn());
			resultElasticJSON.setSerial(log.getSerial());
			resultElasticJSON.setUseType(log.getUseType());
			// Optional '?????????????????????' null ????????? ????????? ?????? '??????????????????'?????? ????????????.
			// Elasticsearch?????? '?????????????????????'??? index key??? ????????? ???????????? ????????? ??????!!!!!!!!!!!!!!!!
			if (log.getRequestTime() == null || log.getRequestTime().trim().equals(CCSSConst.EMPTY)) {
				resultElasticJSON.setRequestTime(log.getLogTime());
			} else {
				resultElasticJSON.setRequestTime(log.getRequestTime());
			}
			// Optional '?????????????????????' null ????????? ????????? ?????? '??????????????????'?????? ????????????.
			if (log.getResponseTime() == null || log.getResponseTime().trim().equals(CCSSConst.EMPTY)) {
				resultElasticJSON.setResponseTime(log.getLogTime());
			} else {
				resultElasticJSON.setResponseTime(log.getResponseTime());
			}
			resultElasticJSON.setResult(log.getResult());
			resultElasticJSON.setResultCode(log.getResultCode());
			resultElasticJSON.setCategory0(NumberUtils.toInt(log.getCategory0(),-1));
			resultElasticJSON.setCategory1(NumberUtils.toInt(log.getCategory1(),-1));
			resultElasticJSON.setCategory2(NumberUtils.toInt(log.getCategory2(),-1));
			resultElasticJSON.setItem(log.getItem());
			resultElasticJSON.setType(log.getType());
			resultElasticJSON.setValue(log.getValue());
			resultElasticJSON.setNwType(log.getNwType());
			// ????????? ?????????
			resultElasticJSON.setPosX(AES256Util.AESEncode(CCSSConst.LOCATION_ENC_KEY, log.getPosX()));
			resultElasticJSON.setPosY(AES256Util.AESEncode(CCSSConst.LOCATION_ENC_KEY, log.getPosY()));
			
			String json = gson.toJson(resultElasticJSON);
			
			JsonElement jElem = gson.fromJson(json, JsonElement.class);
			JsonObject jsonObj = jElem.getAsJsonObject();

			// svcDetailInfo array?????? ???????????? ??????
			if (log.getSvcDetailInfo() != null) {
				Map<String,String> svcDetailInfo = (HashMap<String, String>)log.getSvcDetailInfo();
				for(Map.Entry<String, String> entry : svcDetailInfo.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    jsonObj.addProperty(key, value);
				}
			}
			
			//System.out.println("jObj.toString():"+jsonObj.toString());

			es_logger.trace(jsonObj.toString());

		} catch (Exception e) {
			logger.error("createElasticLog() Exception:{}",e);

			return false;
		} 
		
		return true;
	}	

	private void setRequestTloData(RequestDataJSON log, ResultCode resultCode) {
		RequestDeviceJSON device = log.getDevice();

		Map<String, String> tlo = new HashMap<String, String>();
		tlo.put(TloData.LOG_TYPE, "SVC");
		if (device.getAppType().equals(CCSSConst.APP_TYPE_MANAGER_APP)) {
			tlo.put(TloData.SID, log.getUserCtn());
			tlo.put(TloData.DEV_INFO, "PHONE");
			tlo.put(TloData.SLOG_SVC_CLASS, MDCConst.SLOG_SVC_CLASS_MA);
		} else {
			tlo.put(TloData.SID, log.getCtn());
			tlo.put(TloData.DEV_INFO, "PAD");
			tlo.put(TloData.SLOG_SVC_CLASS, MDCConst.SLOG_SVC_CLASS_CA);
		}
		tlo.put(TloData.REQ_TIME, TloData.getNowDate());
		tlo.put(TloData.OS_INFO, "NONE");
		tlo.put(TloData.NW_INFO, log.getNwType());
		tlo.put(TloData.SVC_NAME, "CCS");
		tlo.put(TloData.DEV_MODEL, device.getDevModel());
		tlo.put(TloData.CARRIER_TYPE, "L");
		tlo.put(TloData.SESSION_ID, "NONE");
		tlo.put(TloData.SERVER_ID, serverId);
		tlo.put(TloData.DEVICE_TYPE, device.getDeviceType());
		tlo.put(TloData.APP_TYPE, device.getAppType());
		tlo.put(TloData.CAR_OEM, device.getCarOem());
		tlo.put(TloData.CLIENT_ID, log.getSerial());
		tlo.put(TloData.SVC_CLASS, "L001");
		tlo.put(TloData.SLOG_RESULT_CODE, resultCode.getCode());
		

		TloUtil.setTloData(tlo);
	}
}


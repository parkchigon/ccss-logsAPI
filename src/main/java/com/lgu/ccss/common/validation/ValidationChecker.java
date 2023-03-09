package com.lgu.ccss.common.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.lgu.ccss.common.constant.CCSSConst;
import com.lgu.ccss.common.constant.HeaderConst;
import com.lgu.ccss.common.model.RequestDataJSON;
import com.lgu.ccss.common.model.RequestDeviceJSON;


@Component
public class ValidationChecker {

	@Value("#{config['service.appType']}")
	private String appTypeList;
	
	@Value("${spring.profiles.active}")
	private String profiles;

	private static final Logger logger = LoggerFactory.getLogger(ValidationChecker.class);

	public CheckResultData checkValue(String value, String reason) {
		CheckResultData result = new CheckResultData();
		
		if (value == null || value.length() == 0) {
			result.setStatus(false);
			result.setReason(reason);
			return result;
		}

		result.setStatus(true);
		
		return result;
	}
	
	public CheckResultData checkHTTPHeaderValidation(HttpHeaders headers) {
		CheckResultData result = checkValue(headers.getFirst(HeaderConst.HTTP_HEADER_CTN), "HTTP_HEADER_CTN");
		if (result.isStatus() == false) {
			return result;
		}

		result = checkValue(headers.getFirst(HeaderConst.HTTP_HEADER_CCSS_TOKEN), "HTTP_HEADER_CCSS_TOKEN");
		if (result.isStatus() == false) {
			return result;
		}

		return result;
	}	
	
	public CheckResultData checkLogValidation(RequestDataJSON log) {

		CheckResultData result = null;

		try{
			// log[i].seq 데이타 없을 경우
			result = checkValue(log.getSeq(),"seq is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].logTime 데이터가 없을 경우
			result = checkValue(log.getLogTime(),"logTime is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			RequestDeviceJSON device = log.getDevice();
			
			// log[i].device.deviceType 데이터가 없을 경우
			result = checkValue(device.getDeviceType(),"device.deviceType is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].device.appType 데이터가 없을 경우
			result = checkValue(device.getAppType(),"device.appType is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			

			// appType 값 유효성 check	
			StringTokenizer appTypeToken = new StringTokenizer(appTypeList, ",");
			boolean isVaildApp = false;
			for (int i=0; appTypeToken.hasMoreTokens(); i++) {
				if (device.getAppType().equals(appTypeToken.nextToken().trim())) {
					isVaildApp = true;
					break;
				}
			}
			if (!isVaildApp) {
				result = checkValue(null, "device.appType not Valid");
				return result;
			}
		
			// log[i].device.appVer 데이터가 없을 경우
			/*result = checkValue(device.getAppVer(),"log["+order+"].device.appVer is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}*/
			
			// log[i].device.saVer 데이터가 없을 경우
			result = checkValue(device.getSaVer(),"device.saVer is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// MANAGER_APP에는 car_oem 값이 안들어올 수 있음
			if (!device.getAppType().equals(CCSSConst.APP_TYPE_MANAGER_APP)) {
				// log[i].device.carOem 데이터가 없을 경우
				result = checkValue(device.getCarOem(),"device.carOem is Mandatory");
				if (result.isStatus() == false) {
					return result;
				}
			}			
			
			// log[i].device.devModel 데이터가 없을 경우
			result = checkValue(device.getDevModel(),"device.devModel is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].ctn 데이터가 없을 경우
			result = checkValue(log.getCtn(),"ctn is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].serial 데이터가 없을 경우
			result = checkValue(log.getSerial(),"serial is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].useType 데이터가 없을 경우
			/*result = .checkValue(log.getUseType(),"log["+order+"].useType is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}*/
			
			// log[i].category0 데이터가 없을 경우
			result = checkValue(log.getCategory0(),"category0 is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].item 데이터가 없을 경우
			result = checkValue(log.getItem(),"item is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].type 데이터가 없을 경우
			result = checkValue(log.getType(),"type is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].value 데이터가 없을 경우
			result = checkValue(log.getValue(),"value is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].nwType 데이터가 없을 경우
			result = checkValue(log.getNwType(),"nwType is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].posX 데이터가 없을 경우
			result = checkValue(log.getPosX(),"posX is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].posY 데이터가 없을 경우
			result = checkValue(log.getPosY(),"posY is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}
			
			// log[i].funcType 데이터가 없을 경우
			/*result = checkValue(log.getFuncType(),"log["+order+"].funcType is Mandatory");
			if (result.isStatus() == false) {
				return result;
			}*/

		}catch(Exception e){
			throw e;
		}

		return result;
	} 
	
	// BDP규격에 있는 각 서비스앱의 svcDetailInfo의 필수값이 존재하는지 확인
	public CheckResultData checkDetailValidation(Object svcDetailInfo, String appType) {
		
		CheckResultData check = null;
		Resource resource;
		Properties props;
		
		try {
			resource = new ClassPathResource("/config/"+profiles+"/config.properties");
			props = PropertiesLoaderUtils.loadProperties(resource);
		
		} catch (Exception e) {
			logger.error("checkDetailValidation: Not loaded System Properties", e);
			check = checkValue(null,"Not loaded System Properties");
			return check;
		}
		
		String appMandatoryColumnList = props.getProperty("appType.mandatory."+appType.toUpperCase());
		
		if (svcDetailInfo == null) {
			if (appMandatoryColumnList != null) {
				// 해당 app서비스의 필수값이 존재하면 svcDetailInfo 값은 존재하여야 함
				check = checkValue(null,"Mandatory("+appMandatoryColumnList+") not found.");
			} else {
				check = checkValue("OK","");
			}
		} else {
			// Mandatory Param&Value check
			HashMap<String, String> svcDetailList = (HashMap<String, String>)svcDetailInfo;
			if (appMandatoryColumnList != null) {
				check = checkDetailParam(svcDetailList, appMandatoryColumnList);
			} else {
				check = checkValue("OK","");
			}
		}
		
		return check;
		
	}	

	// BDP규격에 있는 각 서비스앱의 svcDetailInfo의 필수값이 존재하는지 확인
	public CheckResultData checkDetailParam(Map<String,String> svcDetailInfo, String arrParam) {
		CheckResultData result = null;
		
		StringTokenizer paramToken = new StringTokenizer(arrParam, ",");
		for (int i=0; paramToken.hasMoreTokens(); i++) {

			String token = paramToken.nextToken().trim();
			// key가 존재하는지 확인
			if (!svcDetailInfo.containsKey(token)) {		
				result = checkValue(null,"log.svcDetailInfo."+token+" is Mandatory");
				break;
			}
			// value가 존재하는지 확인
			result = checkValue(svcDetailInfo.get(token),"log.svcDetailInfo."+token+"(value) is Empty");
			if (result.isStatus() == false) break;
		}
		
		if (result == null) {
			result = checkValue("OK","");
		}
		
		return result;
	}
}


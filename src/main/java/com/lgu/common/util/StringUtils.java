/**
 * @(#)StringUtils.java
 */
package com.lgu.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 문자열에 대한 공통 utils
 */
public class StringUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    /**
     * 
     * ex) String tmp = StringUtil.cutString("ThisIsTestCase", 5);
     * System.out.println("TMP=" + tmp);
     * -------------------------------------------------------- ==> TMP=ThisI...
     * 
     * @param str
     * @param limit
     * @return
     */
    public static String cutString(String str, int limit) {
        if (str == null || limit < 4)
            return str;

        int len = str.length();
        int cnt = 0, index = 0;

        while (index < len && cnt < limit) {
            if (str.charAt(index++) < 256)
                cnt++;
            else {
                cnt += 2;
            }
        }

        if (index < len && limit >= cnt)
            str = str.substring(0, index);
        else if (index < len && limit < cnt)
            str = str.substring(0, index - 1);

        return str + "...";
    }

    /**
     * ex) String tmp = StringUtil.cutString("ThisIsTestCase", 5);
     * System.out.println("TMP=" + tmp);
     * -------------------------------------------------------- ==> TMP=ThisI...
     * 
     * @param str
     * @param limit
     * @return
     */
    public static String cutStringLimit(String str, int limit) {
        if (str == null || limit < 4)
            return str;

        int len = str.length();
        int cnt = 0, index = 0;

        while (index < len && cnt < limit) {
            if (str.charAt(index++) < 256)
                cnt++;
            else {
                cnt += 2;
            }
        }

        if (index < len && limit >= cnt)
            str = str.substring(0, index);
        else if (index < len && limit < cnt)
            str = str.substring(0, index - 1);

        if (len > index) {
            return str + "...";
        } else {
            return str;
        }
    }

    /**
     * ex) String tmp = StringUtil.cutString("ThisIsTestCase", 5);
     * System.out.println("TMP=" + tmp);
     * -------------------------------------------------------- ==> TMP=ThisI...
     * 
     * @param str
     * @param limit
     * @return
     */
    public static String cutStringLimit(String str, int limit, String addStr) {
        if (str == null || limit < 4)
            return str;

        int len = str.length();
        int cnt = 0, index = 0;

        while (index < len && cnt < limit) {
            if (str.charAt(index++) < 256)
                cnt++;
            else {
                cnt += 2;
            }
        }

        if (index < len && limit >= cnt)
            str = str.substring(0, index);
        else if (index < len && limit < cnt)
            str = str.substring(0, index - 1);

        if (len > index) {
            return str + addStr;
        } else {
            return str;
        }
    }

	
	public static String cutStrByte(String str, int endIndex)
	{
		StringBuffer sb = new StringBuffer(endIndex);
		int iTotal = 0;
		for( char c: str.toCharArray() )
		{
			iTotal += String.valueOf(c).getBytes().length;
			if(iTotal > endIndex)
				break;
			sb.append(c);
		}
		return sb.toString();
	}
	
    /**
     * Return stack of exception message by character at exception appearance.<br>
     * --> printStackTrace() Return message displayed at method use by string.
     * 
     * @param e
     * @return exception trace string
     */
    public static String getStackMessage(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append("Class==>" + e.getClass() + "\n");
        sb.append("Message==>" + e.getMessage() + "\n");

        StackTraceElement[] ste = e.getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            sb.append("Trace==>" + i + "||" + ste[i].toString() + "\n");
        }

        return sb.toString();
    }

    /**
     * ex) String tmp = StringUtil.extractPathName(
     * "E:\\temp\\ibatis_config\\sql\\CommCode_SqlMap.xml");
     * System.out.println("TMP=" + tmp);
     * ---------------------------------------------------------------- ==>
     * TMP=E:\temp\ibatis_config\sql
     * 
     * @param fileFullPath
     * @return path name
     */
    public static String extractPathName(String fileFullPath) {
        String returnStr = null;
        if (fileFullPath != null && fileFullPath.length() > 0) {
            String fileSep = System.getProperty("file.separator");
            int pp = fileFullPath.lastIndexOf(fileSep);
            if (pp != -1) {
                returnStr = fileFullPath.substring(0, pp);
            } else {
                int pps = fileFullPath.lastIndexOf("/");
                if (pps != -1) {
                    returnStr = fileFullPath.substring(0, pps);
                }
            }

        }
        return returnStr;
    }

    /**
     * ex) String tmp = StringUtil.extractPathName(
     * "E:\\temp\\ibatis_config\\sql\\CommCode_SqlMap.xml");
     * System.out.println("TMP=" + tmp);
     * ---------------------------------------------------------------- ==>
     * TMP=CommCode_SqlMap.xml
     * 
     * @param fileFullPath
     * @return file name
     */
    public static String extractFileName(String fileFullPath) {
        String returnStr = null;
        if (fileFullPath != null && fileFullPath.length() > 0) {
            String fileSep = System.getProperty("file.separator");
            int pp = fileFullPath.lastIndexOf(fileSep);
            if (pp != -1) {
                returnStr = fileFullPath.substring(pp + 1);
            } else {
                int pps = fileFullPath.lastIndexOf("/");
                if (pps != -1) {
                    returnStr = fileFullPath.substring(pps + 1);
                }
            }

        }
        return returnStr;
    }

    /**
     * ex) String xml =
     * "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
     * "<sqlmap><simplexml>TestValue</simplexml> </sqlmap>"; String tmp =
     * StringUtil.extractTagValue(xml, "simplexml"); System.out.println("TMP=" +
     * tmp);
     * --------------------------------------------------------------------
     * ------------ ===> TMP=TestValue
     * 
     * 
     * @param tagName
     *            tag name
     * @return tag value
     */
    public static String extractTagValue(String xmlStr, String tagName) {
        String preTag = "<" + tagName + ">";
        String postTag = "</" + tagName + ">";
        int preStr = xmlStr.indexOf(preTag);
        int postStr = xmlStr.indexOf(postTag);

        if (xmlStr.indexOf(preTag, preStr + 1) != -1) {
            StringBuffer tagValueBuf = new StringBuffer();
            preStr = 0;
            postStr = 0;

            while (true) {
                preStr = xmlStr.indexOf(preTag, preStr);
                postStr = xmlStr.indexOf(postTag, postStr);

                if (preStr == -1 || postStr == -1) {
                    break;
                }

                String tagValue = xmlStr.substring(preStr + preTag.length(), postStr).trim();

                preStr = postStr + postTag.length();
                postStr = preStr;

                tagValueBuf.append(tagValue);
                tagValueBuf.append(";");
            }
            String resultStr = tagValueBuf.toString();
            if (resultStr.length() > 0) {
                resultStr = resultStr.substring(0, (resultStr.length() - 1));
            }
            return resultStr.trim();
        } else {
            if (preStr == -1 || postStr == -1) {
                return "";
            } else {
                String tagValue = xmlStr.substring(preStr + preTag.length(), postStr).trim();

                return tagValue.trim();
            }
        }
    }

    /**
     * ex) 9 -> "09"
     * 
     * @param digit
     *            number
     * @return attach "0" number
     */
    public static String makeTwoDigit(int digit) {

        String digitStr = new Integer(digit).toString();
        if (digitStr.length() == 1) {
            digitStr = "0" + digitStr;
        }
        return digitStr;
    }

    /**
     * @param tmp
     *            string that trim
     * @return string
     */
    public static String setTrim(String tmp) {
        if (tmp != null && tmp.length() > 0) {
            tmp = tmp.trim();
        } else {
            tmp = "";
        }
        return tmp;
    }

    /**
     * @param tmp
     *            "Y" or "N"
     * @return boolean
     */
    public static boolean getString2Boolean(String tmp) {
        boolean returnStr = false;
        if (tmp != null && tmp.length() > 0) {
            tmp = tmp.trim();
            if (tmp.equals("Y")) {
                returnStr = true;
            }
        }
        return returnStr;
    }

    /**
     * @param tmp
     *            string
     * @return number
     */
    public static int getString2Int(String tmp) {
        int returnStr = 0;
        if (tmp != null && tmp.length() > 0) {
            tmp = tmp.trim();
            try {
                returnStr = Integer.parseInt(tmp);
            } catch (Exception e) {
            	logger.error("Exception", e);
            }
        }
        return returnStr;
    }

    /**
     * @return year
     */
    public static String getNowYear() {
        Calendar cal = Calendar.getInstance();
        return new Integer(cal.get(Calendar.YEAR)).toString();
    }

    /**
     * @return month
     */
    public static String getNowMonth() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.makeTwoDigit(cal.get(Calendar.MONTH) + 1);
    }

    /**
     * @return day
     */
    public static String getNowDay() {
        Calendar cal = Calendar.getInstance();
        return StringUtils.makeTwoDigit(cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param fullText
     *            full text string
     * @param searchText
     *            search string
     * @return return exist search string
     */
    public static boolean isStringExist(String fullText, String searchText) {
        boolean isExist = false;
        if (fullText != null && fullText.length() > 0) {
            if (searchText != null && searchText.length() > 0) {
                int indx = fullText.indexOf(searchText);
                if (indx != -1) {
                    isExist = true;
                }
            }
        }
        return isExist;
    }
    
    /**
     * <PRE>
     * src가 null거나 ""이면 tgt값으로 치환하여 return한다.
     *
     * @param String 입력받은 String
     * @return 치환된 String
     *         </PRE>
     */
    public static String nvl(String src, String tgt) {
        String res = tgt;
        if (tgt == null) res = "";
        if (src == null) return res;
        else if (src.equals("")) return res;
        else return src;
    }
    
    public static String nvl(String src) {
        String res = "";
        if (src == null) return res;
        else if (src.equals("")) return res;
        else return src;
    }  

    /**
     * @param orgStr
     *            original string
     * @param initStr
     *            initial string
     * @return init string
     */
    public static String nvlStr(String orgStr, String initStr) {
        if (orgStr == null || orgStr.equals("") || orgStr.equals("null"))
            return initStr;
        else
            return orgStr;
    }

    /**
     * object to string
     * 
     * @param orgStr
     * @param initStr
     * @return
     */
    public static String nvlObjectStr(Object orgStr, String initStr) {
        if (orgStr == null)
            return initStr;
        else
            return (String) orgStr;
    }

    /**
     * null 널이 아니면 prifix를 붙여 반환한다.
     * 
     * @param orgStr
     * @param prifix
     * @return
     */
    public static String nvlPrefixStr(String orgStr, String prifix) {
        if (orgStr == null || orgStr.equals(""))
            return "";
        else
            return prifix + orgStr;
    }

    /**
     * null 이 아니면, postfix를 붙여 반환한다.
     * 
     * @param orgStr
     * @param prifix
     * @return
     */
    public static String nvlPostfixStr(String orgStr, String postfix) {
        if (orgStr == null || orgStr.equals(""))
            return "";
        else
            return orgStr + postfix;
    }

    /**
     * null 이 아니면, prefix를 붙이고, null 이면 default 이미지로 대체함.
     * 
     * @param orgStr
     * @param prifix
     * @param defaultImage
     * @return
     */
    public static String nvlPrefixDefaultStr(String orgStr, String prifix, String defaultImage) {
        if (orgStr == null || orgStr.equals(""))
            return defaultImage;
        else
            return prifix + orgStr;
    }

    public static String getCurrentDateMilli() {

        String pattern = "yyyyMMddHHmmssSSS";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date();
        String result = formatter.format(date);

        return result;
    }
    
    public static String getCurrentDateMicroSec() {

		String pattern = "yyyyMMddHHmmss.SSSSSS";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date();
		String result = formatter.format(date);

		return result;
	}

	public static String getCurrentDateSec() {

		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date();
		String result = formatter.format(date);

		return result;
	}

	public static String getCurrentDateSec(long currentTime) {

		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date(currentTime);
		String result = formatter.format(date);

		return result;
	}

	public static String getCurrentDateMin() {

		String pattern = "yyyyMMddHHmm";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date();
		String result = formatter.format(date);

		return result;
	}
	
	public static String getCurrentDateHour() {

		String pattern = "yyyyMMddHH";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date();
		String result = formatter.format(date);

		return result;
	}
	
	public static String getCurrentDate() {

		String pattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = new Date();
		String result = formatter.format(date);

		return result;
	}

    /**
     * ex) String tmp = StringUtil.dateMask(StringUtil.getCurrentDate(),
     * "yyyy/MM/dd"); System.out.println("TMP=" + tmp);
     * -----------------------------------------------------------------------
     * TMP=2009/03/30
     * 
     * @param str
     * @param mask
     * @return
     * @throws Exception
     */
    public static String dateMask(String str, String mask) {
        if (str == null || str.length() == 0)
            return "";
        String pattern = "";
        switch (str.length()) {
        case 8:
            pattern = "yyyyMMdd";
            break;
        case 10:
            pattern = "yyyyMMddHH";
            break;
        case 14:
            pattern = "yyyyMMddHHmmss";
            break;
        default : 
            pattern = "yyyyMMddHHmm";
            break;
        }

        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        java.util.Date d = null;
        try {
            d = fmt.parse(str);
        } catch (ParseException e) {
        	logger.error("Exception", e);
            return "";
        }
        fmt.applyPattern(mask);
        return fmt.format(d);
    }

    /**
     * ex) String tmp = StringUtil.RPAD("TEST", 10, '#');
     * System.out.println("TMP=" + tmp);
     * --------------------------------------------------- TMP=TEST######
     * 
     * @param str
     *            String
     * @param iLen
     * @param cPad
     * @return String
     */
    public static String RPAD(String str, int iLen, char cPad) {
        String result = str;
        int iTempLen = iLen - result.getBytes().length;
        for (int i = 0; i < iTempLen; i++) {
            result = result + cPad;
        }
        return result;
    }

    /**
     * 
     * ex) String tmp = StringUtil.LPAD("TEST", 10, '#');
     * System.out.println("TMP=" + tmp);
     * --------------------------------------------------- TMP=######TEST
     * 
     * @param str
     *            String
     * @param iLen
     * @param cPad
     * @return String
     */
    public static String LPAD(String str, int iLen, char cPad) {
        String result = str;
        int iTempLen = iLen - result.getBytes().length;
        for (int i = 0; i < iTempLen; i++)
            result = cPad + result;
        return result;
    }

    /**
     * @param str
     * @return String
     */
    public static String getHTML(String str) {
        String res = str;
        res = replace(res, "<", "&lt;");
        res = replace(res, ">", "&gt;");
        res = replace(res, "\n", "<br>");
        res = replace(res, "\t", "<tab>");
        res = replace(res, " ", "&nbsp;");
        return res;
    }

    /**
     * ex) String tmp = StringUtil.replace("<xml>", "<", "&lt;");
     * System.out.println("TMP=" + tmp);
     * --------------------------------------------------------- TMP=&lt;xml>
     * 
     * @param str
     * @param src
     * @param tgt
     * @return String
     */
    public static String replace(String str, String src, String tgt) {
        StringBuffer buf = new StringBuffer();
        String ch = null;
        if (str == null || str.length() == 0)
            return "";
        int i = 0;
        int len = src.length();
        while (i < str.length() - len + 1) {
            ch = str.substring(i, i + len);
            if (ch.equals(src)) {
                buf.append(tgt);
                i = i + len;
            } else {
                buf.append(str.substring(i, i + 1));
                i++;
            }
        }
        if (i < str.length())
            buf.append(str.substring(i, str.length()));
        return buf.toString();
    }

    /**
     * String tmp = StringUtil.makeMoneyType("123456789", ",");
     * System.out.println("TMP=" + tmp);
     * -------------------------------------------------------- TMP=123,456,789
     * 
     * @param str
     * @param del
     * @return String
     */
    public static String makeMoneyType(String str, String del) {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(del.charAt(0));
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);
        return (df.format(Double.parseDouble(str))).toString();
    }

    public static String makeMoneyType(String str) {
        return makeMoneyType(str, ",");
    }

    /**
     * String tmp = StringUtil.makeMoneyType("123456789", ",");
     * System.out.println("TMP=" + tmp);
     * -------------------------------------------------------- TMP=123,456,789
     * 
     * 12345678 --> 12,345,678
     * 
     * @param iAmt
     * @param del
     * @return String
     */
    public static String makeMoneyType(int iAmt, String del) {
        return (makeMoneyType(Integer.toString(iAmt), del));
    }

    /**
     * @param str
     * @param del
     * @return String
     */
    public static String makeNoMoneyType(String str, String del) {
        StringTokenizer st = new StringTokenizer(str, del);
        StringBuffer convert = new StringBuffer();
        while (st.hasMoreTokens()) {
            convert.append(st.nextToken());
        }
        return convert.toString();
    }

    /**
     * @param input
     *            the input
     * 
     * @return true, if checks if is empty
     */
    public static boolean isEmpty(String input) {
        return (input == null || input.trim().equals(""));
    }
    
    /**
     * @param input
     *            the input
     * 
     * @return true, if checks if is Not empty
     */
    public static boolean isNotEmpty(String input) {
    	return isEmpty(input)?false:true;
    }

    /**
     * KSC5601
     * 
     * @param str
     * @return
     */
    public static String asc2ksc(String str) {
        String result = null;
        if (str == null)
            return null;

        try {
            result = new String(str.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException e) {
            result = new String(str);
            logger.error("Exception", e);
        }
        return result;
    }

    /**
     * URF-8
     * 
     * @param str
     * @return
     */
    public static String asc2utf8(String str) {
        String result = null;
        if (str == null)
            return null;

        try {
            result = new String(str.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = new String(str);
            logger.error("Exception", e);
        }
        return result;
    }

    /**
     * 
     * TODO String
     * <P/>
     * 
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null)
            return "";
        else
            return (String) obj;
    }

    /**
     * @param param
     * @return
     */
    public static String removeSpecial(String param) {
        if (param == null)
            param = "";
        else {
            param = param.replaceAll("<", "");
            param = param.replaceAll(">", "");
            param = param.replaceAll("\"", "");
            param = param.replaceAll("'", "");
            param = param.replaceAll("&", "");
            param = param.replaceAll("%", "");
            param = param.replaceAll("!", "");
            param = param.replaceAll("--", "");
            param = param.replaceAll("#", "");
            param = param.replaceAll("[/*]", "");
            param = param.replaceAll("[*/]", "");
        }

        return param;
    }

    /**
     * @param param
     * @return
     */
    public static String removeSpecial4Url(String param) {
        if (param == null)
            param = "";
        else {
            param = param.replaceAll("<", "");
            param = param.replaceAll(">", "");
            param = param.replaceAll("'", "");
            param = param.replaceAll("%", "");
            param = param.replaceAll("!", "");
            param = param.replaceAll("--", "");
            param = param.replaceAll("#", "");
            param = param.replaceAll("[/*]", "");
            param = param.replaceAll("[*/]", "");
        }

        return param;
    }

    /*public static void jsonPrintFilter(JSONObject jsonObj, Logger log) {
        jsonResultPrint(jsonObj, log);
    }

    *//**
     * 
     * example source
     * 
     * Logger log = Logger.getLogger(StringUtilsTest.class); ArrayList<String>
     * arrList= new ArrayList<String>(); arrList.add("result");
     * arrList.add("marIfIdImg"); StringUtils.jsonPrintFilter(obj, log,
     * arrList);
     * 
     * 
     * @param jsonObj
     * @param log
     * @param arrStr
     *//*
    public static void jsonPrintFilter(JSONObject tmpObj, Logger log, String[] arrStr) {

        // JSONObject jsonObj = JSONObject.fromObject(tmpObj);
        //
        // if(jsonObj!=null && arrStr!=null){
        // for(int i=0;i<arrStr.length;i++){
        // String discardStr = arrStr[i];
        // String discardValue = "";
        //
        // Object discardObj = jsonObj.get(discardStr);
        // if(discardObj!=null){
        // discardValue = jsonObj.get(discardStr).toString();
        //
        // if(discardValue.length()>70){
        // discardValue = discardValue.substring(0, 70) +
        // "~~~~[binary data skip]~~~~";
        // jsonObj.put(discardStr,discardValue);
        // }
        // }
        //
        // }
        // }
        jsonResultPrint(tmpObj, log);

    }

    private static void jsonResultPrint(JSONObject jsonObj, Logger log) {
        log.info("################## JSON Result #######################");
        log.info("jsonResult:\n" + jsonObj.toString(2));
        log.info("######################################################");

    }*/

    /**
     * <PRE>
     * EX) double d = CommonUtil.stod("10");<BR>
     * </PRE>
     */
    public static double stod(String str) {
        if (str == null)
            return 0;
        return (Double.parseDouble(str));
    }

    public static int object2int(Object obj) {
        int resultCnt = 0;
        if (obj != null) {
            resultCnt = Integer.parseInt(obj.toString());
        }
        return resultCnt;
    }

    public static String timeGap(String endTime) {
        String tiemGap = "";
        GregorianCalendar calendar = new GregorianCalendar();

        long startDate = System.currentTimeMillis();

        int eyear = Integer.parseInt(endTime.substring(0, 4));
        int emonth = Integer.parseInt(endTime.substring(4, 6));
        int eday = Integer.parseInt(endTime.substring(6, 8));
        int ehour = Integer.parseInt(endTime.substring(8, 10));
        int eminute = Integer.parseInt(endTime.substring(10, 12));

        calendar.set(eyear, emonth - 1, eday, ehour, eminute);
        long endDate = calendar.getTimeInMillis();

        long millis = endDate - startDate;

        long day = millis / 86400000; // 1000*60*60*24
        long dayMin = millis % 86400000;
        long hour = dayMin / (60000 * 60);
        long hourMin = dayMin % (60000 * 60);
        long minute = hourMin / 60000;

        tiemGap = day + "." + hour + "." + minute;

        return tiemGap;
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static long timeGap(String startTime, String endTime) {

        GregorianCalendar calendar = new GregorianCalendar();

        int syear = Integer.parseInt(startTime.substring(0, 4));
        int smonth = Integer.parseInt(startTime.substring(4, 6));
        int sday = Integer.parseInt(startTime.substring(6, 8));
        int shour = Integer.parseInt(startTime.substring(8, 10));
        int sminute = Integer.parseInt(startTime.substring(10, 12));
        int ssecond = Integer.parseInt(startTime.substring(12, 14));

        calendar.set(syear, smonth - 1, sday, shour, sminute, ssecond);
        long startDate = calendar.getTimeInMillis();

        int eyear = Integer.parseInt(endTime.substring(0, 4));
        int emonth = Integer.parseInt(endTime.substring(4, 6));
        int eday = Integer.parseInt(endTime.substring(6, 8));
        int ehour = Integer.parseInt(endTime.substring(8, 10));
        int eminute = Integer.parseInt(endTime.substring(10, 12));
        int esecond = Integer.parseInt(endTime.substring(12, 14));

        calendar.set(eyear, emonth - 1, eday, ehour, eminute, esecond);
        long endDate = calendar.getTimeInMillis();

        long millisTimeGap = (endDate - startDate) / 1000;

        return millisTimeGap;

    }

    public static String nullToString(String str) {
        return (str == null) ? "" : str.trim();
    }

    /**
     * 주어진 String 을 MD5로 암호화 하여 반환
     * 
     * @param orginal
     * @return
     * @throws Exception
     */
    public static String getMD5String(String orginal) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(orginal.getBytes());
        byte[] orgByte = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orgByte.length; i++) {
            sb.append(orgByte[i]);
        }

        String digested = sb.toString();
        digested = digested.replaceAll("-", "");

        return digested;
    }

    /**
     * Exceptoin to stacktrace
     * 
     * @param e
     * @return
     */
    public static String getStackTraceString(Exception e) {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        return w.toString();
    }
    
    /**
     * str 을 받아 Integer.parseInt 한 결과를 리턴한다. 비어있거나 숫자가 아닌경우는 default_int 를 반환한다.
     * @param str
     * @param default_int
     * @return
     */
    public static int isEmptyDefault(String str, int default_int){
        int returnInt = 0;
        if(str != null){
            try{
                returnInt = Integer.parseInt(str);
            }catch(Exception e){
                returnInt = default_int;
                logger.error("Exception", e);
            }
        }else{
            returnInt = default_int;
        }
        return returnInt;
    }
    
    /**
     * str 을 입력받은 빈값인지 체크하여 빈값이면 default 값을 세팅하여 반환한다.
     * @param str
     * @param defaultStr
     * @return
     */
    public static String isEmptyDefault(String str, String defaultStr){
        String returnStr = null;
        if(str == null || str.length() ==0){
            returnStr = defaultStr;
        }else{
            returnStr = str;
        }
        return returnStr;
    }
    
    /**
     * 스트링을 고정된 길이의 바이트로 컨버팅
     * @param s
     * @param len
     * @return
     */
  	public static byte[] string2FixedBytes(String s , int len)
      {
          byte[] b = new byte[len];
          byte[] b1 = s.getBytes();
          for(int i = 0; i < b.length; i++) b[i] = 0x00;
          int copyLength = b.length > b1.length ? b1.length : b.length ;

          System.arraycopy(b1,0,b,0,copyLength);
          return b;
      }
  	/**
  	 * 바이트 배열을 십육진수 스트링으로 컨버팅
  	 * @param b
  	 * @return
  	 * @throws Exception
  	 */
  	public static String getHexString(byte[] b) throws Exception {
    	String result = "";
    	for (int i=0; i < b.length; i++) {
    		result +=
    			Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
    	}
    	return result;
	}
  	public static String getHexString(byte[] b, int len) throws Exception {
    	String result = "";
    	for (int i=0; i < len; i++) {
    		result +=
    			Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
    	}
    	return result;
	}
  	
  	
  	public static String getDateFormat(int year, int month, int date, int hour, int minute){
  		
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		
		return sdf.format(cal.getTime());
  	}
  	
  	public static String getPaddingResult(String header, String seq, int seqLength, String padStr){
  		
  		
  		
  		String pad = header + org.apache.commons.lang.StringUtils.leftPad(seq, seqLength - header.length(), padStr);
  		
  		return pad;
  	}
  	
  	
  	
  	public static String getLoginUserNo(HttpServletRequest request) throws Exception{
  		 String cookieName = "loginUserNo";
	        String cookieValue = "";
	        
	        Cookie[] cookies = request.getCookies();
	    	if (cookies != null && cookies.length > 0) {
	    		for (Cookie cookie : cookies) {
	    			if (cookieName.equals(cookie.getName())) {
	    				
	    				cookieValue = cookie.getValue();
	    			}
	    		}
	    	}        
	    	
	    	return cookieValue;
  	}
  	
  	/**
  	 * 쿠키 에서 name 에 해당하는 value가져 오기
  	 * @param name
  	 * @param request
  	 * @return
  	 * @throws Exception
  	 */
  	public static String getCookieVal(String name, HttpServletRequest request) throws Exception{
  		String resultVal = null;
  		
  		Cookie[] cookies = request.getCookies();
  		if (cookies != null && cookies.length > 0) {
    		for (Cookie cookie : cookies) {
    			if (name.equals(cookie.getName())) {
    				resultVal = cookie.getValue();
    				break;
    			}
    		}
    	}
  		
  		return nvl(resultVal, "");
  	}
  	
  	
	public static String getExceptionStackTrace(Throwable e){
		StringBuilder sb = new StringBuilder();
		StackTraceElement[]	stackTraces = e.getStackTrace();
		for (int i = 0; i < stackTraces.length; i++) {
			sb.append(stackTraces[i]).append("\n");
		}
		return sb.toString();
	}
	
	
	private static final char[] TempPassWordUsedCharList = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};	
	public static String getTempPassword(){
		int rndVal = 0;
		
		StringBuilder tempPassword = new StringBuilder();		
		for(int i=0; i < 8; i++){
			rndVal = (int)(Math.random() * TempPassWordUsedCharList.length);
			tempPassword.append(TempPassWordUsedCharList[rndVal]);
		}		
		
		return tempPassword.toString();
	}
  	
	
	public static String encodeUtf8(String str) {
		String rslt = "";
		if (str != null) {
			try {
				rslt = URLEncoder.encode(str, "UTF-8");
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		}
		return rslt;
	}
	
	
	public static void replaceXSSHtmlTag(Object object) throws IllegalArgumentException, IllegalAccessException {
		if(object instanceof List<?>){
			
			for(Object tmpObj : (List<?>) object){
				replaceXSSHtmlTagValue(tmpObj);
			}
			
		}else{
			replaceXSSHtmlTagValue(object);
		}
	}
	
	public static void replaceXSSHtmlTagValue(Object object) throws IllegalArgumentException, IllegalAccessException{
		Object obj=object;
		for (Field field : obj.getClass().getDeclaredFields()){
			field.setAccessible(true);
			Object value = null;
			value = field.get(obj);
			if(value !=null && value instanceof String){
			field.set(obj, (String) value.toString().replace("&lt;", "<")
					.replace("&gt;", ">")
					.replace("&#39;", "'")
					.replace("&#40;", "(")
					.replace("&#41;", ")")
					);
			}
		}
	}
}

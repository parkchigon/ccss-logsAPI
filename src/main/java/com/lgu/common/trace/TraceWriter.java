package com.lgu.common.trace;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lgu.ccss.log.api.service.LogServiceImpl;
import com.lgu.common.http.HttpRequestWrapper;
import com.lgu.common.http.HttpResponseWrapper;
import com.lgu.common.trace.model.TraceDataJSON;
import com.lgu.common.util.DateUtils;

@Component
public class TraceWriter {
	private static final Logger logger = LoggerFactory.getLogger("tracelogger");
	
	private String hostAddress = "";
	
	public TraceWriter() {
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkTraceId(String traceId) {
		if (traceId != null && TraceManager.getTraceInfo(traceId) != null) {
			return true;
		}
		
		return false;
	}
	
	public void trace(String traceId, String source, String target, String protocol, String content) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(protocol);
		traceData.setContent(content);
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpGet get) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(get));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpPost post) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(post));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpResponse response, String body) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(response, body));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpServletRequest req) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(req));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpServletResponse res) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(res));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpRequestWrapper reqWrapper) {
		/*if (checkTraceId(traceId) == false) {
			return;
		}*/
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(reqWrapper));
		trace(traceData);
	}
	
	public void trace(String traceId, String source, String target, HttpResponseWrapper resWrapper) {
		if (checkTraceId(traceId) == false) {
			return;
		}
		
		TraceDataJSON traceData = new TraceDataJSON();
		traceData.setTraceId(traceId);
		traceData.setSource(source);
		traceData.setTarget(target);
		traceData.setProtocol(TraceConst.PROTOCOL_HTTP);
		traceData.setContent(TraceHttpFormat.getTraceStr(resWrapper));
		trace(traceData);
	}
	
	private void trace(TraceDataJSON traceData) {
		traceData.setHostName(hostAddress);
		
		MDC.put("fileName", traceData.getTraceId());
		
		logger.trace(TraceFormat.makeTrace(traceData));
	}
}

class TraceFormat {
	public static String makeTrace(TraceDataJSON traceData) {
		String str = "";

		str += makeSubject(traceData);
		str += makeContent(traceData);
		str += makeFooter();

		return str;
	}
	
	private static String makeSubject(TraceDataJSON traceData) {		
		String str = "";
		str += "===============================================================================" + TraceConst.TRACE_NEXT_LINE;
		str += " SYSTEM   : " + traceData.getHostName() + "\t" + DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + TraceConst.TRACE_NEXT_LINE
			+  " PROTOCOL : " + traceData.getProtocol() + TraceConst.TRACE_NEXT_LINE
			+  " FLOW     : " + traceData.getSource() + "  -->  " + traceData.getTarget() + TraceConst.TRACE_NEXT_LINE;
		str += "-------------------------------------------------------------------------------" + TraceConst.TRACE_NEXT_LINE;
		return str;
	}
	
	private static String makeContent(TraceDataJSON traceData) {
		return traceData.getContent() + TraceConst.TRACE_NEXT_LINE;
	}
	
	private static String makeFooter() {
		String str = "";
		str += "===============================================================================" + TraceConst.TRACE_NEXT_LINE;
		
		return str;
	}
}

class TraceHttpFormat {
	private static Logger logger = LoggerFactory.getLogger(TraceHttpFormat.class);
	
	public static String getTraceStr(HttpGet get) {
		if (get == null) {
			return "";
		}
		
		String str = "";
		str += get.getRequestLine().toString() + TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(get.getAllHeaders()) + TraceConst.TRACE_NEXT_LINE;

		return str;
	}
	
	public static String getTraceStr(HttpPost post) {
		if (post == null) {
			return "";
		}

		String str = "";
		str += post.getRequestLine().toString() + TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(post.getAllHeaders()) + TraceConst.TRACE_NEXT_LINE;
		
		String body = "";
		try {
			body = EntityUtils.toString(post.getEntity(), StandardCharsets.UTF_8.toString());
			body = getPrettyJson(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		str += body;

		return str;
	}

	public static String getTraceStr(HttpResponse response, String bodyStr) {
		if (response == null) {
			return "";
		}
		
		String str = "";
		str += response.getStatusLine().toString() + TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(response.getAllHeaders()) + TraceConst.TRACE_NEXT_LINE;
		
		String body = "";
		try {
			//body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
			body = getPrettyJson(bodyStr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		str += body;

		return str;
	}
	
	public static String getTraceStr(HttpServletRequest req) {
		if (req == null) {
			return "";
		}

		String str = "";
		str += req.getMethod() + " " + req.getRequestURI() + " " 
				+ req.getProtocol() + TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(req) + TraceConst.TRACE_NEXT_LINE;
		
		return str;
	}
	
	public static String getTraceStr(HttpServletResponse res) {
		if (res == null) {
			return "";
		}

		String str = "";
		str += res.getStatus() + " " + HttpStatus.getStatusText(res.getStatus()) 
			+ TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(res) + TraceConst.TRACE_NEXT_LINE;
		
		return str;
	}
	
	public static String getTraceStr(HttpRequestWrapper reqWrapper) {
		if (reqWrapper == null) {
			return "";
		}

		String str = "";
		str += reqWrapper.getMethod() + " " + reqWrapper.getRequestURI() + " " 
				+ reqWrapper.getProtocol() + TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(reqWrapper) + TraceConst.TRACE_NEXT_LINE;
		
		String body = new String(reqWrapper.getBodyData());
		body = getPrettyJson(body);
		str += body;

		return str;
	}
	
	public static String getTraceStr(HttpResponseWrapper resWrapper) {
		if (resWrapper == null) {
			return "";
		}

		String str = "";
		str += resWrapper.getStatus() + " " + HttpStatus.getStatusText(resWrapper.getStatus()) 
			+ TraceConst.TRACE_NEXT_LINE;
		str += makeHeaderStr(resWrapper) + TraceConst.TRACE_NEXT_LINE;
		
		String body = new String(resWrapper.getResponseText());
		body = getPrettyJson(body);
		str += body;

		return str;
	}
	
	public static String makeHeaderStr(Header[] headers) {
		if (headers == null) {
			return "";
		}
		
		String str = "";
		for (Header header : headers) {
			str += header.getName() + " : " + header.getValue() + TraceConst.TRACE_NEXT_LINE;
		}
		
		return str;
	}
	
	public static String makeHeaderStr(HttpServletRequest httpRequest) {
		if (httpRequest == null) {
			return "";
		}
		
		String str = "";
		Enumeration<String> names = httpRequest.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Enumeration<String> values = httpRequest.getHeaders(name); // support multiple values
			if (values != null) {
				while (values.hasMoreElements()) {
					String value = (String) values.nextElement();
					str += name + " : " + value + TraceConst.TRACE_NEXT_LINE;
				}
			}
		}
		
		return str;
	}
	
	public static String makeHeaderStr(HttpServletResponse httpResponse) {
		if (httpResponse == null) {
			return "";
		}
		
		String str = "";
		Collection<String> names = httpResponse.getHeaderNames();
		for (String name : names) {
			Collection<String> values = httpResponse.getHeaders(name); // support multiple values
			if (values != null) {
				for (String value : values) {
					str += name + " : " + value + TraceConst.TRACE_NEXT_LINE;
				}
			}
		}
		
		return str;
	}
	
	public static String getPrettyJson(String jsonStr) {
		if (jsonStr == null || jsonStr.length() == 0) {
			return null;
		}
		
		JsonParser parser = new JsonParser();
	    JsonObject json = parser.parse(jsonStr).getAsJsonObject();
	    
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		return gson.toJson(json);
	}
}
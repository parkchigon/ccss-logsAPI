package com.lgu.ccss.common.xssFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;




public class RequestWrapper extends HttpServletRequestWrapper {
	
	private byte[] bodyData;
	
	public RequestWrapper(HttpServletRequest servletRequest) throws IOException {
		super(servletRequest);
		InputStream is = super.getInputStream();
		bodyData = IOUtils.toByteArray(is);
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bodyData);
		return new ServletImpl(bis);
	}
	
	
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;

		}

		int count = values.length;

		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}

		return encodedValues;
	}

	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);

		if (value == null) {

			return null;

		}

		return cleanXSS(value);

	}

	public String getHeader(String name) {

		String value = super.getHeader(name);

		if (value == null) {
			return null;
		}

		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below

		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");

		value = value.replaceAll("'", "&#39;");

		value = value.replaceAll("eval\\((.*)\\)", "");

		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");

//		value = value.replaceAll("script", "");
		value = value.replaceAll("--", "");

		return value;

	}

	public byte[] getBodyData() {
		return bodyData;
	}
	
}

class ServletImpl extends ServletInputStream {

	private InputStream is;

	public ServletImpl(InputStream bis) {
		is = bis;
	}

	@Override
	public int read() throws IOException {
		return is.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return is.read(b);
	}

}

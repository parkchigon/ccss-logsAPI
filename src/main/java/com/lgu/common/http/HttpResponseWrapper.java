package com.lgu.common.http;

import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseWrapper extends HttpServletResponseWrapper {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private int statusCode=200;

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintWriter writer = null;

    private Map<String, String> headerMaps = new LinkedHashMap<String, String>();

    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaderMaps() {
    	return headerMaps;
    }

    public String getResponseText(){
    	if ( writer != null ) writer.flush();
        return buffer.toString();
    }

    public byte[] getResponseBinary(){
    	if ( writer != null ) writer.flush();
        return buffer.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
    	ServletOutputStream sos = new JoServletOutputStream(buffer);
        return sos;
    }

    @Override
    public PrintWriter getWriter(){
    	
        try {
            writer = new PrintWriter(new OutputStreamWriter(buffer, "utf-8"));
            return writer;
        } catch (Exception e) {
	        logger.error("Exception", e);	
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addIntHeader(String name, int value) {
        headerMaps.put(name, String.valueOf(value));
        super.addIntHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        headerMaps.put(name, value);
        super.setHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        headerMaps.put(name, String.valueOf(value));
        super.setIntHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        headerMaps.put(name, value);
        super.addHeader(name, value);
    }

    @Override
    public void sendError(int sc) throws IOException {
        statusCode=sc;
        super.sendError(sc);
    }

    @Override
    public void setStatus(int sc) {
        statusCode=sc;
        super.setStatus(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        statusCode=sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc, String sm) {
        statusCode=sc;
        super.setStatus(sc, sm);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        setHeader("location", location);
        setStatus(302);
        super.sendRedirect(location);
    }

    public class JoServletOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream buffer;
        private ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.LocalStrings");

        public JoServletOutputStream(ByteArrayOutputStream buffer) {
            this.buffer = buffer;
        }

        @Override
        public void close() throws IOException {
            buffer.close();
        }

        @Override
        public void write(int b) throws IOException {
            buffer.write(b);
        }

        @Override
        public void print(String s) throws IOException {
            if (s == null)
                s = "null";
            int len = s.length();
            for (int i = 0; i < len; i++) {
                char c = s.charAt(i);
                if ((c & 65280) != 0) {
                    String errMsg = lStrings.getString("err.not_iso8859_1");
                    Object errArgs[] = new Object[1];
                    errArgs[0] = new Character(c);
                    errMsg = MessageFormat.format(errMsg, errArgs);
                    throw new CharConversionException(errMsg);
                }
                write(c);
            }

        }

        @Override
        public void println() throws IOException {
        	print("rn");
        }

        @Override
        public void flush() throws IOException {
            buffer.flush();
        }

        @Override
        public void write(byte[] abyte0, int i, int j) throws IOException {
            buffer.write(abyte0, i, j);
        }

        @Override
        public void write(byte[] abyte0) throws IOException {
            buffer.write(abyte0);
        }

    }
}
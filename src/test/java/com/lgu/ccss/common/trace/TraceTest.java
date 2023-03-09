package com.lgu.ccss.common.trace;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgtel.mmdb.CasCrypto;
import com.lgu.ccss.common.model.RequestJSON;
import com.lgu.common.trace.model.TraceInfoVO;
import com.lgu.common.trace.TraceConst;
import com.lgu.common.trace.TraceManager;
import com.lgu.common.trace.TraceWriter;
import com.lgu.common.trace.model.TraceDataJSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/test/resources/spring/context-trace.xml")
public class TraceTest {
	
	@Autowired
	TraceWriter traceWriter;
	
	@Ignore
	@Test
	public void testTrace() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("?CPTYPE=" + "I");
		sb.append("&CPID=" + "mdu_CCSS");
		sb.append("&CPPWD=" + URLEncoder.encode(CasCrypto.casCryptoEncode("E645919BADBAD0D9", "D076AEABE5BC7585",
				"DF89BCE93B70CD13", "D91C3245767F1C0E", "2577seen!!")));
		sb.append("&CASECODE=" + "TF1251");
		sb.append("&CTN=" + "010012341234");
		
		HttpGet get = new HttpGet("http://112.172.129.170:9955/NIF/CASInterface.jsp" + sb.toString());
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				Integer.parseInt("1000"));
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.parseInt("5000"));
		get.setParams(params);
		
		TraceManager.putTraceInfoByCtn("01012341234", new TraceInfoVO());
		
		traceWriter.trace("01012341234", TraceConst.NODE_ID_WAS, TraceConst.NODE_ID_NCAS, get);
	}
}

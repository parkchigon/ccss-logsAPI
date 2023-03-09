package com.lgu.ccss.common.util;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lgu.ccss.common.model.MembVO;
import com.lgu.common.util.TtlHashMap;

@Component
@Scope("singleton")
public class MembCacheFactory implements Serializable {
	private TtlHashMap<String, MembVO> membHash = null;

	@Value("#{config['app.memb.cache.timeMin']}")
    public String membCacheTimeMin;
	
	public TtlHashMap<String, MembVO> getMembCacheFactory() {
		if (membHash == null) {
			membHash = new TtlHashMap<String, MembVO>(TimeUnit.MINUTES, Long.parseLong(membCacheTimeMin));
		}
		return membHash;
	}
}
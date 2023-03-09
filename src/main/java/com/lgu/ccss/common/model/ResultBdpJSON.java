package com.lgu.ccss.common.model;

import java.util.Arrays;

public class ResultBdpJSON {

	private ResultBdpDataJSON[] log;

	public ResultBdpDataJSON[] getLog() {
		return log;
	}

	public void setLog(ResultBdpDataJSON[] log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return "[log=" + Arrays.toString(log) + "]";
	}
	
}

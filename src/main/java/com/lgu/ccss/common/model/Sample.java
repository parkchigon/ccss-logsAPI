package com.lgu.ccss.common.model;

public class Sample {
	private int value1;
	private String values2;
	private transient int value3;
	public int getValue1() {
		return value1;
	}
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	public String getValues2() {
		return values2;
	}
	public void setValues2(String values2) {
		this.values2 = values2;
	}
	public int getValue3() {
		return value3;
	}
	public void setValue3(int value3) {
		this.value3 = value3;
	}
	
}

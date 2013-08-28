package com.primitive.library.helper.http;

public class HttpHeader {
	private final String name;
	private final String value;
	/**
	 * @param name
	 * @param value
	 */
	public HttpHeader(String name, String value){
		this.name = name;
		this.value =value;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
}

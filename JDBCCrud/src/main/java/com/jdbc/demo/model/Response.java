package com.jdbc.demo.model;

public class Response {

	private int ResponseCode;

	private String ResponseMessage;

	private Object Jdata;

	private String Data;

	public int getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(int responseCode) {
		ResponseCode = responseCode;
	}

	public String getResponseMessage() {
		return ResponseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}

	public Object getJdata() {
		return Jdata;
	}

	public void setJdata(Object jdata) {
		Jdata = jdata;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

}

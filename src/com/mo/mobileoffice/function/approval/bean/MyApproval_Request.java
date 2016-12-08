package com.mo.mobileoffice.function.approval.bean;

public class MyApproval_Request {
	private String token;
	private String user_id;
	public MyApproval_Request(String token, String user_id) {
		super();
		this.token = token;
		this.user_id = user_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}

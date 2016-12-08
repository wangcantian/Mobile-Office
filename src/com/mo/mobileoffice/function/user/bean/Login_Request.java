package com.mo.mobileoffice.function.user.bean;

public class Login_Request {

	public String user_name;
	public String user_pass;

	public Login_Request(String user_name, String user_pass) {
		super();
		this.user_name = user_name;
		this.user_pass = user_pass;
	}

}

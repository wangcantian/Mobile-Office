package com.mo.mobileoffice.function.user.bean;

public class Register_Request {

	public String user_name;
	public String user_pass;
	public String email;

	public Register_Request(String user_name, String user_pass, String email) {
		super();
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.email = email;
	}

}

package com.mo.mobileoffice.function.user.bean;

public class UpdateUserInfo_Request {

	public String user_id;
	public String token;

	public UpdateUserInfo_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

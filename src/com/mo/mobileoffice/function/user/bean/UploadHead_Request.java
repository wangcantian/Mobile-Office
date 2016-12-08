package com.mo.mobileoffice.function.user.bean;

public class UploadHead_Request {

	public String user_id;
	public String token;

	public UploadHead_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

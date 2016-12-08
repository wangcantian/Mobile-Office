package com.mo.mobileoffice.function.user.bean;

public class ModifyUser_Request {

	public String user_id;
	public String token;
	public String name;
	public String sex;
	public String mobile;
	public String email;
	public String birthday;

	public ModifyUser_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

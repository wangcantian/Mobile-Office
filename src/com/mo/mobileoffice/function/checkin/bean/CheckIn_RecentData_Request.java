package com.mo.mobileoffice.function.checkin.bean;

public class CheckIn_RecentData_Request {
	private String user_id;
	private String token;
	private String date;

	public CheckIn_RecentData_Request(String user_id, String token, String date) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.date = date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

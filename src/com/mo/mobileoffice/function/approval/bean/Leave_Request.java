package com.mo.mobileoffice.function.approval.bean;

public class Leave_Request {
	private String user_id;
	private String token;
	private String app_type;
	private String day;
	private String reason;
	private String start;
	private String end;
	private String type;

	public Leave_Request(String user_id, String token, String app_type,
			String day, String reason, String start, String end, String type) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.app_type = app_type;
		this.day = day;
		this.reason = reason;
		this.start = start;
		this.end = end;
		this.type = type;
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

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

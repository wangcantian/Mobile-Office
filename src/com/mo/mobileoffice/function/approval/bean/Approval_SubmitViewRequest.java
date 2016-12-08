package com.mo.mobileoffice.function.approval.bean;

public class Approval_SubmitViewRequest {
	private String user_id;
	private String token;
	private String app_id;
	private String view;
	private String state;

	public Approval_SubmitViewRequest(String user_id, String token, String app_id,
			String view, String state) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.app_id = app_id;
		this.view = view;
		this.state = state;
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

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

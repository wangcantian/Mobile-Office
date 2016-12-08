package com.mo.mobileoffice.function.checkin.bean;

import java.io.Serializable;

public class CheckIn_HistoryDate_Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3289837773898384156L;
	private String user_id;
	private String token;

	public CheckIn_HistoryDate_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
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

}

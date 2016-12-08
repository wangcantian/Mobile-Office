package com.mo.mobileoffice.function.announce.bean;

public class DelAnno_Request {
	private String user_id;
	private String announce_id;
	private String token;

	public DelAnno_Request(String user_id, String announce_id, String token) {
		super();
		this.user_id = user_id;
		this.announce_id = announce_id;
		this.token = token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAnnounce_id() {
		return announce_id;
	}

	public void setAnnounce_id(String announce_id) {
		this.announce_id = announce_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

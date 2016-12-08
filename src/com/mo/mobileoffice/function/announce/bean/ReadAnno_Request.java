package com.mo.mobileoffice.function.announce.bean;

public class ReadAnno_Request {

	public String user_id;
	public String announce_id;
	public String token;

	public ReadAnno_Request(String user_id, String announce_id, String token) {
		super();
		this.user_id = user_id;
		this.announce_id = announce_id;
		this.token = token;
	}

}

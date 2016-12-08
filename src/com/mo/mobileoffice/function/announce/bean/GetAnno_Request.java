package com.mo.mobileoffice.function.announce.bean;

public class GetAnno_Request {

	public String user_id;
	public String token;

	public GetAnno_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

package com.mo.mobileoffice.function.meeting.bean;

public class GetWaitAppr_Request {

	public String user_id;
	public String token;

	public GetWaitAppr_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

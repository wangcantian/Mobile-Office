package com.mo.mobileoffice.function.meeting.bean;

public class ApprovalMeeting_Request {

	public String user_id;
	public String token;
	public int app_id;
	public int state;
	public String view;

	public ApprovalMeeting_Request(String user_id, String token, int app_id,
			int state, String view) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.app_id = app_id;
		this.state = state;
		this.view = view;
	}

}

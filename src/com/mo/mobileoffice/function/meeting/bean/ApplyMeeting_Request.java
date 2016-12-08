package com.mo.mobileoffice.function.meeting.bean;

/** 申请会议请求的请求参数bean **/
public class ApplyMeeting_Request {

	public String user_id;
	public String token;
	public int app_type = 4;
	public int room_id;
	public String start;
	public String end;
	public String title;
	public String content;

	public ApplyMeeting_Request(String user_id, String token, int room_id,
			String start, String end, String title, String content) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.room_id = room_id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.content = content;
	}

}

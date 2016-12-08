package com.mo.mobileoffice.function.meeting.bean;

public class JudgeRoom_Request {

	public String user_id;
	public String token;
	public int room_id;
	public String start;
	public String end;

	public JudgeRoom_Request(String user_id, String token, int room_id,
			String start, String end) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.room_id = room_id;
		this.start = start;
		this.end = end;
	}

}

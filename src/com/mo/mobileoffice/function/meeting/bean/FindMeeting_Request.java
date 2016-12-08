package com.mo.mobileoffice.function.meeting.bean;

/** 请求某个房间包含的会议信息的请求参数bean **/
public class FindMeeting_Request {

	public String user_id;
	public String token;
	public int room_id;

	public FindMeeting_Request(String user_id, String token, int room_id) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.room_id = room_id;
	}

}

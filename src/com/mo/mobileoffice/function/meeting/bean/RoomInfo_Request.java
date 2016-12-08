package com.mo.mobileoffice.function.meeting.bean;

/** 请求所有房间的物理信息的请求参数bean **/
public class RoomInfo_Request {

	public String user_id;
	public String token;

	public RoomInfo_Request(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}

}

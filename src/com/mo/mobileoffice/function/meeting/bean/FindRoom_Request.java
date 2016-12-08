package com.mo.mobileoffice.function.meeting.bean;

/** 通过条件请求筛选房间的请求参数bean **/
public class FindRoom_Request {

	public String user_id;
	public String token;
	public int floor_id;
	public String start;
	public int limit;

	public FindRoom_Request(String user_id, String token, int floor_id,
			String start, int limit) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.floor_id = floor_id;
		this.start = start;
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindRoom_Request [user_id=" + user_id + ", token=" + token
				+ ", floor_id=" + floor_id + ", start=" + start + ", limit="
				+ limit + "]";
	}

}

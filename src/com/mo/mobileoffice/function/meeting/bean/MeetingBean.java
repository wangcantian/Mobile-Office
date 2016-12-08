package com.mo.mobileoffice.function.meeting.bean;

import java.io.Serializable;

/** 会议信息bean **/
public class MeetingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3654253856236080644L;
	private int id;
	private String user_name;
	private String approver;
	private int state;
	private String app_time;
	private String view;
	private String approval_time;
	private int room_id;
	private String start;
	private String end;
	private String title;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getApp_time() {
		return app_time;
	}

	public void setApp_time(String app_time) {
		this.app_time = app_time;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getApproval_time() {
		return approval_time;
	}

	public void setApproval_time(String approval_time) {
		this.approval_time = approval_time;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MeetingBean [id=" + id + ", user_name=" + user_name
				+ ", approver=" + approver + ", state=" + state + ", app_time="
				+ app_time + ", view=" + view + ", approval_time="
				+ approval_time + ", room_id=" + room_id + ", start=" + start
				+ ", end=" + end + ", title=" + title + ", content=" + content
				+ "]";
	}

}

package com.mo.mobileoffice.function.approval.bean;

import java.util.ArrayList;

public class Evection_Request {
	private String user_id;
	private String token;
	private String app_type;
	private String day;
	private String reason;
	private ArrayList<EvectionData> explain;
	
	public class EvectionData{
		private String place;
		private String start;
		private String end;
		public String getPlace() {
			return place;
		}
		public void setPlace(String place) {
			this.place = place;
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
		public EvectionData(String place, String start, String end) {
			super();
			this.place = place;
			this.start = start;
			this.end = end;
		}
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}


	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ArrayList<EvectionData> getExplain() {
		return explain;
	}

	public void setExplain(ArrayList<EvectionData> explain) {
		this.explain = explain;
	}

	public Evection_Request(String user_id, String token, String app_type, String day, String reason,
			ArrayList<EvectionData> explain) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.app_type = app_type;
		this.day = day;
		this.reason = reason;
		this.explain = explain;
	}
	public Evection_Request(){}
	
	
}

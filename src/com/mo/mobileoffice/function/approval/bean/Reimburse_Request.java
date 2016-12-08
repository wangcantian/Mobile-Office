package com.mo.mobileoffice.function.approval.bean;

import java.util.ArrayList;

public class Reimburse_Request {
	private String user_id;
	private String token;
	private String app_type;
	private String total;
	private ArrayList<ReData> explain;

	public Reimburse_Request(String user_id, String token, String app_type,
			String total, ArrayList<ReData> explain) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.app_type = app_type;
		this.total = total;
		this.explain = explain;
	}
	
	public Reimburse_Request(){}

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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public ArrayList<ReData> getExplain() {
		return explain;
	}

	public void setExplain(ArrayList<ReData> explain) {
		this.explain = explain;
	}

	public class ReData {
		private String money;
		private String type;
		private String detail;

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public ReData(String money, String type, String detail) {
			super();
			this.money = money;
			this.type = type;
			this.detail = detail;
		}

	}
}

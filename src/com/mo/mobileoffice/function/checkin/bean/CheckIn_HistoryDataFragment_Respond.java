package com.mo.mobileoffice.function.checkin.bean;

import java.util.ArrayList;

public class CheckIn_HistoryDataFragment_Respond {
	private String flag;
	private String msg;
	private ArrayList<Data> data;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<Data> getList() {
		return data;
	}

	public void setList(ArrayList<Data> data) {
		this.data = data;
	}

	public class Data {
		private String time;
		private String week;
		private String x;
		private String y;
		private String place;
		private String remarks;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getWeek() {
			return week;
		}

		public void setWeek(String week) {
			this.week = week;
		}

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

	}
}

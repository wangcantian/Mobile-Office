package com.mo.mobileoffice.function.checkin.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckIn_HistoryData_Respond implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4830606319638203730L;
	private int flag;
	private String msg;
	private ArrayList<HistoryDate> data;

	public ArrayList<HistoryDate> getData() {
		return data;
	}

	public void setData(ArrayList<HistoryDate> data) {
		this.data = data;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class HistoryDate {
		private String time;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

	}
}

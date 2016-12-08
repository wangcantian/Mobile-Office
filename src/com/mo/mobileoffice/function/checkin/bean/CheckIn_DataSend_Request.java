package com.mo.mobileoffice.function.checkin.bean;

import java.io.Serializable;

/**
 * guo
 * 
 * @author Administrator2016/5/8
 * 
 */
public class CheckIn_DataSend_Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 32354085026546807L;
	private String user_id;
	private String token;
	private double x;
	private double y;
	private String type;
	private String remarks;
	private String place;

	public CheckIn_DataSend_Request(String user_id, String token, double x,
			double y, String type, String remarks, String place) {
		super();
		this.user_id = user_id;
		this.token = token;
		this.x = x;
		this.y = y;
		this.type = type;
		this.remarks = remarks;
		this.place = place;
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "CheckIn_DataSend_Request [user_id=" + user_id + ", token="
				+ token + ", x=" + x + ", y=" + y + ", type=" + type
				+ ", remarks=" + remarks + ", place=" + place + "]";
	}

}

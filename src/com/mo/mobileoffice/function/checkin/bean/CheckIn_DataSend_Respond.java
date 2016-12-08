package com.mo.mobileoffice.function.checkin.bean;

import java.io.Serializable;

public class CheckIn_DataSend_Respond implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7529738511622490180L;
	private int flag;
	private String msg;

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

}

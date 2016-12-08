package com.mo.mobileoffice.common.base;

import java.io.Serializable;
import java.util.List;

public class CommBeanList<T> implements Serializable {

	private static final long serialVersionUID = 6927062872108854673L;

	private int flag;
	private String msg;
	private List<T> data;

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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BaseBeanList [flag=" + flag + ", msg=" + msg + ", data=" + data
				+ "]";
	}

}

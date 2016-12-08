package com.mo.mobileoffice.common.base;

import java.io.Serializable;

public class CommBean<T> implements Serializable {

	private static final long serialVersionUID = -4612123379055068955L;

	private int flag;
	private String msg;
	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BaseBean [flag=" + flag + ", msg=" + msg + ", data=" + data
				+ "]";
	}

}

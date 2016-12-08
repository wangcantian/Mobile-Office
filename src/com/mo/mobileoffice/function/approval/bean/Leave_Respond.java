package com.mo.mobileoffice.function.approval.bean;

public class Leave_Respond {
	private String flag;
	private String msg;

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

	public Leave_Respond(String flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}

}

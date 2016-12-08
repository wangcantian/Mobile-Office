package com.mo.mobileoffice.function.approval.bean;

public class WaitApproval {
	private String resumerName;
	private String resumerType;
	private String resumerState;
	private String resumerTime;

	public WaitApproval(String resumerName, String resumerType,
			String resumerState, String resumerTime) {
		super();
		this.resumerName = resumerName;
		this.resumerType = resumerType;
		this.resumerState = resumerState;
		this.resumerTime = resumerTime;
	}

	public String getResumerName() {
		return resumerName;
	}

	public void setResumerName(String resumerName) {
		this.resumerName = resumerName;
	}

	public String getResumerType() {
		return resumerType;
	}

	public void setResumerType(String resumerType) {
		this.resumerType = resumerType;
	}

	public String getResumerState() {
		return resumerState;
	}

	public void setResumerState(String resumerState) {
		this.resumerState = resumerState;
	}

	public String getResumerTime() {
		return resumerTime;
	}

	public void setResumerTime(String resumerTime) {
		this.resumerTime = resumerTime;
	}

}

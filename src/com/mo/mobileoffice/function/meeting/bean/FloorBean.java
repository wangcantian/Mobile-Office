package com.mo.mobileoffice.function.meeting.bean;

/** 楼层物理信息bean **/
public class FloorBean {
	private int id;
	private int floor_num;
	private String floor_name;

	public FloorBean() {
		super();
	}

	public FloorBean(int id, int floor_num, String floor_name) {
		super();
		this.id = id;
		this.floor_num = floor_num;
		this.floor_name = floor_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloor_num() {
		return floor_num;
	}

	public void setFloor_num(int floor_num) {
		this.floor_num = floor_num;
	}

	public String getFloor_name() {
		return floor_name;
	}

	public void setFloor_name(String floor_name) {
		this.floor_name = floor_name;
	}

	@Override
	public String toString() {
		return "FloorBean [id=" + id + ", floor_num=" + floor_num
				+ ", floor_name=" + floor_name + "]";
	}

}

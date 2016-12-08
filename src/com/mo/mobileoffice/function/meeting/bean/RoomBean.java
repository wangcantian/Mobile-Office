package com.mo.mobileoffice.function.meeting.bean;

/** 房间物理信息bean **/
public class RoomBean {
	private int id;
	private int floor_id;
	private String room_num;
	private int wifi;
	private int projector;
	private int air_con;
	private int seat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloor_id() {
		return floor_id;
	}

	public void setFloor_id(int floor_id) {
		this.floor_id = floor_id;
	}

	public String getRoom_num() {
		return room_num;
	}

	public void setRoom_num(String room_num) {
		this.room_num = room_num;
	}

	public int getWifi() {
		return wifi;
	}

	public void setWifi(int wifi) {
		this.wifi = wifi;
	}

	public int getProjector() {
		return projector;
	}

	public void setProjector(int projector) {
		this.projector = projector;
	}

	public int getAir_con() {
		return air_con;
	}

	public void setAir_con(int air_con) {
		this.air_con = air_con;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "RoomBean [id=" + id + ", floor_id=" + floor_id + ", room_num="
				+ room_num + ", wifi=" + wifi + ", projector=" + projector
				+ ", air_con=" + air_con + ", seat=" + seat + "]";
	}

}

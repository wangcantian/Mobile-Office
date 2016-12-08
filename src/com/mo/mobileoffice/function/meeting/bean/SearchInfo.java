package com.mo.mobileoffice.function.meeting.bean;

import java.util.Calendar;

/** 会议房间筛选条件封装bean(非请求) **/
public class SearchInfo {
	public int floor_id;
	public int floor_num;
	public boolean isAll;
	public Calendar time;
	public int minTime;
	public int isProjector;
	public int isWIFI;
	public int isAir_Conditioner;

	@Override
	public String toString() {
		return "SearchInfo [floor_id=" + floor_id + ", floor_num=" + floor_num
				+ ", isAll=" + isAll + ", time=" + time + ", minTime="
				+ minTime + ", isProjector=" + isProjector + ", isWIFI="
				+ isWIFI + ", isAir_Conditioner=" + isAir_Conditioner + "]";
	}

}

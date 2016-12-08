package com.mo.mobileoffice.function.meeting.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface MeetingDetailContract {

	interface Presenter extends MvpPresenter<View> {
		/** 通过楼层id获得楼名 **/
		public FloorBean getFloorBeanById(String floor_id);
		/** 通过房间id获得会议室数据 **/
		public RoomBean getRoomBeanById(String room_id);
	}
	
	interface View extends MvpView {
		
	}
}

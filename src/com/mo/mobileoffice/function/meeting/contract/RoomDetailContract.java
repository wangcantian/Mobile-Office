package com.mo.mobileoffice.function.meeting.contract;

import java.util.List;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface RoomDetailContract {

	interface Presenter extends MvpPresenter<View> {
		/** 从数据库获取房间信息 **/
		public RoomBean getRoomBeanFromDB(int room_id);
		/** 请求房间包含的会议数据 **/
		public void requestMeetingList(int room_id);
		/** 通过楼层id获得楼层对象 **/
		public FloorBean getFloorBeanById(int floor_id);
	}
	
	interface View extends MvpView {
		/** 通知listview视图更新 **/
		public void updateListView();
		/** 初始化适配器 **/
		public void initAdapter(List<MeetingBean> meetingLists);
		
		public void showDialog();
		public void dismissDialog();
	}
}

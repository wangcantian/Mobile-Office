package com.mo.mobileoffice.function.meeting.contract;

import java.util.List;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface MyMeetingApplyContract {

	interface Presenter extends MvpPresenter<View> {
		public void requestMeetingApprHist(boolean isPullToRefresh);
		public void initAdapter();
		public RoomBean getRoomBeanById(int id);
		public FloorBean getFloorBeanById(int id);
	}
	
	interface View extends MvpView {
		public void notifyDataSetChange();
		public void resetListViewHead();
		public void initAdapter(List<MeetingApplyBean> lists);
	}
}

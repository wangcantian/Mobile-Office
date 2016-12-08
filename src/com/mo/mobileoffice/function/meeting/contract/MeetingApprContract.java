package com.mo.mobileoffice.function.meeting.contract;

import java.util.List;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface MeetingApprContract {

	interface Presenter extends MvpPresenter<View> {
		public void requestWaitAppr(boolean isPullToRefresh);
		public void requestAlreadyAppr(boolean isPullToRefresh);
		public void menuTitleClick(boolean isWait);
		public void initAdapterData();
		public RoomBean getRoomBeanById(int id);
		public FloorBean getFloorBeanById(int id);
	}
	
	interface View extends MvpView {
		public void initAdapter(List<MeetingApprBean> lists);
		public void notifyDataSetChange();
		public void resetListViewHead();
	}
}

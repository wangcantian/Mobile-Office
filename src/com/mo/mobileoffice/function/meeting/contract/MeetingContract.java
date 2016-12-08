package com.mo.mobileoffice.function.meeting.contract;

import java.util.List;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.SearchInfo;

public interface MeetingContract {

	interface Presenter extends MvpPresenter<View> {
		/** 初始化下拉菜单数据 **/
		public void initTabViews();
		
		/** 初始化内容区 **/
		public void initContainerView();
		
		/** 获得tabtext **/
		public List<String> getTabTexts();
		
		/** 通过条件查找会议室  **/
		public void searchByCondition(SearchInfo searchInfo);
		
		public String searchFloorNameById(int floor_id);
	}
	
	interface View extends MvpView {
		/** 初始化TAB **/
		public void initTabViews(List<FloorBean> floorLists);

		/** 初始化内容区listview **/
		public void initContainerView(List<RoomBean> roomLists);

		/** 更新tab的文本 **/
		public void popupViewRefresh(String text);
		
		public void notifyViewUpdate();
		
		public void showDialog();
		
		public void dismissDialog();
	}
}

package com.mo.mobileoffice.function.meeting.contract;

import android.os.Bundle;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface MeetingApprDetailContract {

	interface Presenter extends MvpPresenter<View> {
		public RoomBean getRoomBeanById(int id);
		public FloorBean getFloorBeanById(int id);
		/** 提交审批意见 **/
		public void requestApprSubmit(boolean isAgress, int app_id, String text);
		/** 初始化操作 **/
		public void init(Bundle bundle);
	}
	
	interface View extends MvpView {
		/** 审批完成 **/
		public void apprSuccess();
		public void initMeetingAppr(MeetingApprBean bean);
		public void initMeetingApply(MeetingApplyBean bean);
	}
}

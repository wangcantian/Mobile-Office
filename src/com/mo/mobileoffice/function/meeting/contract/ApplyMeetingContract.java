package com.mo.mobileoffice.function.meeting.contract;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;

public interface ApplyMeetingContract {

	interface Presenter extends MvpPresenter<View> {
		/** 初始化 **/
		public void init(Bundle bundle);
		/** 展示楼层选择对话框 **/
		public void showFloorListDialog();
		/** 展示房间选择对话框 **/
		public void showRoomListDialog();
		/** 弹出开始时间选择器 **/
		public void showStartTimeDialog();
		/** 弹出结束时间选择器 **/
		public void showEndTimeDialog();
		/** 申请会议 **/
		public void applyMeeting(String them, String content);
		/** 网络请求房间状态 **/
		public void requestStateByRoomId();
		/** 检验时间的有效性 **/
		public void checkTimeValidity();
		/** OnItemClick **/
		public void OnItemClick(int position, boolean isFloorList);
	}
	
	interface View extends MvpView {
		/** 弹出楼层选择框 **/
		public void showFloorDialog(List<FloorBean> floorList);
		/** 弹出房间选择框 **/
		public void showRoomDialog(List<RoomBean> roomList);
		/** 成功申请会议室 **/
		public void applyMeetingSuccess();
		/** 弹出时间选择器 **/
		public void showTimeDialog(final Calendar calender, boolean isStartTime);
		/** 显示状态 **/
		public void updateState(int state);
		/** 显示加载对话框 **/
		public void showLoadingDialog(boolean isProgressBar);
		/** 隐藏加载对话框 **/
		public void dismissDialog(boolean isProgressBar);
		/** 更新楼层和房间的显示 **/
		public void updateFloorRoomView(String floor_name, String room_name);
	}
}

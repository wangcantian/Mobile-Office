package com.mo.mobileoffice.function.checkin.contract;

import java.util.ArrayList;
import java.util.Date;

import android.widget.TextView;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryData_Respond;

public interface CheckInContract {

	interface Presenter extends MvpPresenter<View> {
		// 点击进行签到网络操作
		public int doCheckInHttpRequest(String address, double lat, double lng);

		// 获取历史签到数据
		public CheckIn_HistoryData_Respond getCheckInHistory();

		// 初始化地图操作
		public void initLocation();

		// 清除定位操作
		public void destroyLocationClient();

		public void doPageChange();

		public void doDateSet();

		// 开始定位
		public void startLocation();

		// 停止定位操作
		public void stopLocation();

		// 设置2个显示定位数据textView按钮到控制层
		public void setAddress(TextView storeName, TextView addressName);
	}

	interface View extends MvpView {
		public void changeCalendarUI();

		public void changeToAmap();

		public void dateSet(Date date);

		public void initLatLng(double lat, double lng);

		// 设置签到按钮不可点击事件
		public void setCheckInButtonStyle();

		public void addCalendarHistory(ArrayList<String> dateList);
	}
}

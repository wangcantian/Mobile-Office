package com.mo.mobileoffice.function.checkin.contract;

import java.util.ArrayList;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryDataFragment_Respond.Data;

public interface CheckInHistoryContract {

	interface Presenter extends MvpPresenter<View> {
		public void requestData();

		public void requestRecentData(String date);
	}

	interface View extends MvpView {
		public void setRespondData(ArrayList<Data> list);

		public void notifyData(ArrayList<Data> data);
	}
}

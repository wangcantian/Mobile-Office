package com.mo.mobileoffice.function.approval.contract;

import java.io.File;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface LeaveContract {

	interface Presenter extends MvpPresenter<View> {
		public void showSingleDialog();
		public void showTimeDialog(int id);
		public void doHttpSubmit(String day,String reason,String start,String end, String type,File[] files,String[] fileNames);
	}
	
	interface View extends MvpView {
		public void showSingleChoiceDialog();
		public void showTimeChoiceDialog(int id);
		public boolean checkCanSubmit();
		public void showDialog();
		public void showProgressDialog();
		public void dismissProgressDialog();
		public void showErrorDialog();
	}
}

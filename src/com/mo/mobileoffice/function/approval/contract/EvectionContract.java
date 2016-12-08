package com.mo.mobileoffice.function.approval.contract;

import java.io.File;
import java.util.ArrayList;

import android.widget.EditText;
import android.widget.TextView;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface EvectionContract {

	interface Presenter extends MvpPresenter<View> {
		public void dohttpSubmit(EditText address, TextView startTime,
				TextView endTime, EditText ev_day, EditText ev_cause,
				ArrayList<android.view.View> viewLists,File[] files,String[] fileNames);
		public void showTimeDialog(TextView tv,int id);
	}
	
	interface View extends MvpView {
		public void showTimeChoiceDialog(TextView tv,int id);

		public boolean checkCanSubmit();

		public void showDialog();

		public void showProgressDialog();

		public void dismissProgressDialog();

		public void showErrorDialog();
	}
}

package com.mo.mobileoffice.function.approval.contract;

import java.io.File;
import java.util.ArrayList;

import android.widget.EditText;
import android.widget.TextView;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface ReimburseContract {

	interface Presenter extends MvpPresenter<View> {
		public void doHttpSubmit(EditText cost, EditText type, EditText detail,
				TextView total, ArrayList<android.view.View> viewLists,
				File[] files, String[] fileNames);
	}

	interface View extends MvpView {
		public void showDialog();

		public void showProgressDialog();

		public void dismissProgressDialog();

		public void showErrorDialog();
	}
}

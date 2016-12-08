package com.mo.mobileoffice.function.approval.contract;

import android.widget.EditText;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface ApprovalDetailContract {
	public interface Presenter extends MvpPresenter<View> {
		public void doHttpApprovalSubmit(int id,String app_id,EditText edit);
	}

	public interface View extends MvpView {
	}
}

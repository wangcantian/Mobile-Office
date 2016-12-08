package com.mo.mobileoffice.function.approval.contract;

import android.widget.EditText;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface ApprovalHistoryDetailContract {
	public interface Presenter extends MvpPresenter<View> {
		public void doHttpApprovalSubmit(int id,EditText edit);
	}

	public interface View extends MvpView {
	}
}

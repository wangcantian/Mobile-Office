package com.mo.mobileoffice.function.approval.contract;

import java.util.ArrayList;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond.DetailData;

public interface WaitMeApprovalContract {
	public interface Presenter extends MvpPresenter<View> {
		public void doHttpGet();
	}

	public interface View extends MvpView {
		public void doHttpRespond(ArrayList<DetailData> list);

		public void removeAllView();
	}
}

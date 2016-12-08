package com.mo.mobileoffice.function.approval.contract;

import java.util.ArrayList;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.approval.bean.HistoryApproval_Respond.HistoryData;

public interface HistoryApprovalContract {
	public interface Presenter extends MvpPresenter<View> {
		public void doHttpGet();
	}

	public interface View extends MvpView {
		public void doHttpRespond(ArrayList<HistoryData> list);
	}
}

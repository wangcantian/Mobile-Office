package com.mo.mobileoffice.function.approval.contract;

import java.util.ArrayList;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond.MyApprovalData;

public interface ApprOfMeContract {

	interface Presenter extends MvpPresenter<View> {
		public void doHttpSubmit();
	}
	
	interface View extends MvpView {
		public void adapterRespondData(ArrayList<MyApprovalData> list);
	}
	
}

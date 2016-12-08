package com.mo.mobileoffice.function.approval.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface WaitMeApprContract {

	interface Presenter extends MvpPresenter<View> {
		public void addChild(android.view.View view);
		public void removeChild(android.view.View view);
	}
	
	interface View extends MvpView {
		public void addChild(android.view.View view);
		public void removeChild(android.view.View view);
	}
}

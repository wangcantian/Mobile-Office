package com.mo.mobileoffice.function.comm.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface SettingContract {

	interface Presenter extends MvpPresenter<View> {
		public void clearCache();
	}
	
	interface View extends MvpView {
		
	}
}

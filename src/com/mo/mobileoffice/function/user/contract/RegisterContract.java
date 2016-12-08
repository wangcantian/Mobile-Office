package com.mo.mobileoffice.function.user.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface RegisterContract {

	interface Presenter extends MvpPresenter<View> {
		/** 注册请求 **/
		public void register(String usename, String pwd, String confirmPwd, String email);
	}
	
	interface View extends MvpView {
		/** 注册成功 **/
		public void registerSuccess();
	}
}

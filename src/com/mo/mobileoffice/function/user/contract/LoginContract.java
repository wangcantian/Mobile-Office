package com.mo.mobileoffice.function.user.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface LoginContract {

	interface Presenter extends MvpPresenter<View> {
		public void login(String usename, String pwd);
		
		public void signUnLogin();
	}
	
	interface View extends MvpView {
		public void loginSuccess();
		
		public void showLoadingDialog();
		
		public void dismissLoadingDialog();
	}
}

package com.mo.mobileoffice.function.comm.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface SplashContract {

	interface Presenter extends MvpPresenter<View> {
		/** 初始化闪屏页，是否跳过登陆检查 **/
		public void initSplash();
		
	}
	
	interface View extends MvpView {

		/** 跳转到登陆页面 **/
		public void openLoginActivity();
		
		/** 跳转到主页面 **/
		public void openMainActivity();
		
		/** 数据加载完成 **/
		public void loadingFinish();
	}
}

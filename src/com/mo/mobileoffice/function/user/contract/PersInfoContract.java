package com.mo.mobileoffice.function.user.contract;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.user.bean.UserBean;

public interface PersInfoContract {

	interface Presenter extends MvpPresenter<View> {
		public void initView();
		public void changeBirthday(String date);
		public void changeSex(String sex);
		public void changeName(String name);
		public void changePhone(String phone);
		public void uploadHead(String path);
	}
	
	interface View extends MvpView {
		/** 刷新显示用户信息的视图 **/
		public void refreshView(UserBean bean);
	}
}

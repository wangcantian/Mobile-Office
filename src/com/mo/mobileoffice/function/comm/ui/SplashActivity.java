package com.mo.mobileoffice.function.comm.ui;

import android.view.View;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpActivity;
import com.mo.mobileoffice.function.comm.contract.SplashContract;
import com.mo.mobileoffice.function.comm.contract.SplashContract.Presenter;
import com.mo.mobileoffice.function.comm.presenter.SplashPresenter;
import com.mo.mobileoffice.function.user.ui.LoginActivity;

/** 闪屏页 **/
public class SplashActivity extends MvpActivity<SplashContract.Presenter> implements SplashContract.View {
	
	@Override
	protected Presenter createPresenter() {
		return new SplashPresenter(this) ;
	}
	
	@Override
	protected void init() {
		mPresenter.initSplash();
	}
	
	@Override
	public void onClick(View v) {
		
	}

	@Override
	protected int setContentViewId() {
		return R.layout.activity_splash;
	}

	@Override
	public void openLoginActivity() {
		openActivity(LoginActivity.class);
		finish();
	}

	@Override
	public void openMainActivity() {
		openActivity(MainActivity.class);
		finish();
	}

	@Override
	public void loadingFinish() {
		openMainActivity();
	}



}

package com.mo.mobileoffice.common.mvp;

import android.os.Bundle;

import com.mo.mobileoffice.common.base.BaseActivity;

@SuppressWarnings("rawtypes")
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {

	protected P mPresenter;
	
	protected abstract P createPresenter();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mPresenter = createPresenter();
		mPresenter.attachView(this);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		mPresenter.detachView(false);
		super.onDestroy();
	}
	
	protected P getPresenter() {
		return mPresenter;
	}
}

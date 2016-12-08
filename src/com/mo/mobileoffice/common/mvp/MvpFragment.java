package com.mo.mobileoffice.common.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mo.mobileoffice.common.base.BaseFragment;

@SuppressWarnings("rawtypes")
public abstract class MvpFragment<P extends MvpPresenter> extends BaseFragment implements MvpView {
	
	protected P mPresenter;
	
	protected abstract P createPresenter();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(setContentViewId(), container, false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (mPresenter == null) {
			mPresenter = createPresenter();
		}
		mPresenter.attachView(this);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		mPresenter.detachView(getRetainInstance());
		super.onDestroy();
	}
	
	protected P getPresenter() {
		return mPresenter;
	}
	
}

package com.mo.mobileoffice.common.mvp;

import android.content.Context;

import com.mo.mobileoffice.common.base.BasePresenter;

public abstract class BaseMvpPresenter<V extends MvpView> extends BasePresenter implements MvpPresenter<V> {

	public BaseMvpPresenter(Context context) {
		super(context);
	}

	private V mView;

	@Override
	public void attachView(V view) {
		this.mView = view;
	}

	protected V getView() {
		return mView;
	}
	
	protected void unBinding() {
		this.mView = null;
	}
	
	
	
}

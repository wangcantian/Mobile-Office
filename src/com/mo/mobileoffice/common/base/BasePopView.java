package com.mo.mobileoffice.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mo.mobileoffice.common.widget.SlipPopWin;
import com.mo.mobileoffice.common.widget.SlipPopWin.OnScrollListener;

/** SlipPopWin控件上滑页面内容区基类 **/
public abstract class BasePopView {
	
	protected Context mContext;
	protected View mRootView;
	protected SlipPopWin mPopWin;
	protected Bundle mBundle;
	
	protected abstract void init();
	
	public BasePopView(Context context, Bundle bundle) {
		this.mContext = context;
		this.mBundle = bundle;
	}
	
	public View getView() {
		mRootView = LayoutInflater.from(mContext).inflate(getContentViewId(), null);
		return mRootView;
	}
	
	public void OnCreate() {
		init();
	}
	
	protected abstract int getContentViewId();
	
	protected void setOnScrollListener(OnScrollListener listener) {
		mPopWin.setOnScrollListener(listener);
	}
	
	public void setSlipPopWin(SlipPopWin popWin) {
		this.mPopWin = popWin;
	}
}

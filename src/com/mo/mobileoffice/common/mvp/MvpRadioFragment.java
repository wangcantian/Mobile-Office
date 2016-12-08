package com.mo.mobileoffice.common.mvp;


/**
 * 4个Radio页面的父类
 */
@SuppressWarnings("rawtypes")
public abstract class MvpRadioFragment<P extends MvpPresenter> extends MvpFragment<P> {
	/**
	 * 标题栏右按钮点击事件
	 */
	public abstract void RightOnClick();
}

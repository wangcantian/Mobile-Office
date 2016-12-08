package com.mo.mobileoffice.common.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.mo.mobileoffice.common.base.IdeaActivity;

@SuppressWarnings("rawtypes")
public abstract class MvpIdeaFragment<P extends MvpPresenter> extends MvpFragment<P> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!(getActivity() instanceof IdeaActivity))
			throw new ClassCastException("getActivity() is not IdeaActivity");
	}

	/**
	 * 标题栏右按钮点击事件
	 */
	public abstract void rightOnClick();

	public void leftOnClick() {
		getActivity().finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	protected IdeaActivity getIdeaActivity() {
		return (IdeaActivity) super.getActivity();
	}

	protected void setTitle(String title) {
		getIdeaActivity().setTitle(title);
	}

	protected void setRight(String right) {
		getIdeaActivity().setRight(right);
	}

	protected Bundle getBundle() {
		return getIdeaActivity().getIntent().getExtras();
	}

	protected Intent getIntent() {
		return getIdeaActivity().getIntent();
	}

}

package com.mo.mobileoffice.function.approval.presenter;

import android.content.Context;
import android.view.View;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprContract;

public class WaitMeApprPresenter extends
		BaseMvpPresenter<WaitMeApprContract.View> implements
		WaitMeApprContract.Presenter {

	public WaitMeApprPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void addChild(View view) {
		getView().addChild(view);
	}

	@Override
	public void removeChild(View view) {
		getView().removeChild(view);
	}

}

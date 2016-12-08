package com.mo.mobileoffice.function.approval.presenter;

import android.content.Context;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.approval.contract.ImageViewShowContract;

public class ShowImagePresenter extends
		BaseMvpPresenter<ImageViewShowContract.View> implements
		ImageViewShowContract.Presenter {

	public ShowImagePresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

}

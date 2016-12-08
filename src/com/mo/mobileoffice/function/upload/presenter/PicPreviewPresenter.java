package com.mo.mobileoffice.function.upload.presenter;

import android.content.Context;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.upload.contract.PicPreviewContract;

public class PicPreviewPresenter extends
		BaseMvpPresenter<PicPreviewContract.View> implements
		PicPreviewContract.Presenter {

	public PicPreviewPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

}

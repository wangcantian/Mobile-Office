package com.mo.mobileoffice.function.approval.presenter;

import android.content.Context;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.approval.contract.ApprovalContract;

public class ApprovalPresenter extends BaseMvpPresenter<ApprovalContract.View>
		implements ApprovalContract.Presenter {

	public ApprovalPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

}

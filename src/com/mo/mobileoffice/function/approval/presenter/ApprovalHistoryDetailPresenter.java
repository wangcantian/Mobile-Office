package com.mo.mobileoffice.function.approval.presenter;

import android.content.Context;
import android.widget.EditText;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.approval.contract.ApprovalHistoryDetailContract;

public class ApprovalHistoryDetailPresenter extends
		BaseMvpPresenter<ApprovalHistoryDetailContract.View> implements
		ApprovalHistoryDetailContract.Presenter {

	public ApprovalHistoryDetailPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void doHttpApprovalSubmit(int id, EditText edit) {
		baseShowProgressDialog();
	}

}

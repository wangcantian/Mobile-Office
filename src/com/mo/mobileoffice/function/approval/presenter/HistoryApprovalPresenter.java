package com.mo.mobileoffice.function.approval.presenter;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetal_Request;
import com.mo.mobileoffice.function.approval.bean.HistoryApproval_Respond;
import com.mo.mobileoffice.function.approval.contract.HistoryApprovalContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class HistoryApprovalPresenter extends
		BaseMvpPresenter<HistoryApprovalContract.View> implements
		HistoryApprovalContract.Presenter {
	private UserModel model;

	public HistoryApprovalPresenter(Context context) {
		super(context);
		model = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void doHttpGet() {
		ApprovalDetal_Request requestBeen = new ApprovalDetal_Request(
				model.getUserId(), model.getUserToken());
		baseShowProgressDialog();
		request(ACTION.ACTION_APPROVAL_HISTORY, requestBeen, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				if (responseStr != null && !responseStr.equals("")) {
					final HistoryApproval_Respond respond = GsonTool.getData(
							responseStr, HistoryApproval_Respond.class);
					if (respond.getFlag() != null
							&& respond.getFlag().equals("200")) {
						if (respond.getData() != null
								&& respond.getData().size() > 0) {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().doHttpRespond(respond.getData());
								}
							});
						}
					}
				}

				getUIHandler().post(new Runnable() {

					@Override
					public void run() {
						baseDismissProgressDialog();
					}
				});
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				baseDismissProgressDialog();
			}
		});
	}

}

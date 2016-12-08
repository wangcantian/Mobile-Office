package com.mo.mobileoffice.function.approval.presenter;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond.DetailData;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetal_Request;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprovalContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class WaitMeApprovalPresenter extends
		BaseMvpPresenter<WaitMeApprovalContract.View> implements
		WaitMeApprovalContract.Presenter {
	private UserModel model;

	public WaitMeApprovalPresenter(Context context) {
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
		request(ACTION.ACTION_APPROVAL_DETAIL, requestBeen, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				if (responseStr != null && !responseStr.equals("")) {
					ApprovalDetail_Respond respond = GsonTool.getData(
							responseStr, ApprovalDetail_Respond.class);
					if (respond.getFlag() != null
							&& respond.getFlag().equals("200")) {
						final ArrayList<DetailData> dataList = respond
								.getData();
						if (dataList != null && dataList.size() > 0) {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().doHttpRespond(dataList);
								}
							});
						} else {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().removeAllView();
								}
							});
						}
					}
				}
				baseDismissProgressDialog();
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				baseDismissProgressDialog();
			}
		});
	}

}

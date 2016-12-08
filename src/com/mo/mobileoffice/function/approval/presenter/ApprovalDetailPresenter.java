package com.mo.mobileoffice.function.approval.presenter;

import java.io.IOException;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.Approval_SubmitViewRequest;
import com.mo.mobileoffice.function.approval.bean.Leave_Respond;
import com.mo.mobileoffice.function.approval.contract.ApprovalDetailContract;
import com.mo.mobileoffice.function.approval.contract.WaitMeApprContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class ApprovalDetailPresenter extends
		BaseMvpPresenter<ApprovalDetailContract.View> implements
		ApprovalDetailContract.Presenter {
	private UserModel model;

	public ApprovalDetailPresenter(Context context) {
		super(context);
		model = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void doHttpApprovalSubmit(int id, String app_id, EditText edit) {
		baseShowProgressDialog();
		if (id == R.id.agree) {
			if (!edit.getText().toString().trim().equals("")) {
				Approval_SubmitViewRequest request = new Approval_SubmitViewRequest(
						model.getUserId(), model.getUserToken(), app_id, edit
								.getText().toString(), "1");
				doAgree(request);
			}
		} else if (id == R.id.refuse) {
			Approval_SubmitViewRequest request = new Approval_SubmitViewRequest(
					model.getUserId(), model.getUserToken(), app_id, edit
							.getText().toString(), "2");
			doRefuse(request);
		}
	}

	private void doAgree(Approval_SubmitViewRequest request) {
		request(ACTION.ACTION_APPROVAL_SUBMIT, request, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				baseDismissProgressDialog();
				if (responseStr != null && !responseStr.equals("")) {
					Leave_Respond respond = GsonTool.getData(responseStr,
							Leave_Respond.class);
					if (respond.getFlag().equals("200")) {
						baseShowDialog();
					} else {
						baseShowErrorDialog();
					}
				}
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				baseDismissProgressDialog();
				baseShowErrorDialog();
			}
		});
		baseDismissProgressDialog();

	}

	private void doRefuse(Approval_SubmitViewRequest request) {
		request(ACTION.ACTION_APPROVAL_SUBMIT, request, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				baseDismissProgressDialog();
				if (responseStr != null && !responseStr.equals("")) {
					Leave_Respond respond = GsonTool.getData(responseStr,
							Leave_Respond.class);
					if (respond.getFlag().equals("200")) {
						baseShowDialog();
					} else {
						baseShowErrorDialog();
					}
				}
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				baseDismissProgressDialog();
				baseShowErrorDialog();
			}
		});
	}

}

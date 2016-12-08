package com.mo.mobileoffice.function.approval.presenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.Leave_Respond;
import com.mo.mobileoffice.function.approval.bean.Reimburse_Request;
import com.mo.mobileoffice.function.approval.bean.Reimburse_Request.ReData;
import com.mo.mobileoffice.function.approval.contract.ReimburseContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class ReimbursePresenter extends
		BaseMvpPresenter<ReimburseContract.View> implements
		ReimburseContract.Presenter {

	private UserModel mUserModel;

	public ReimbursePresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void doHttpSubmit(EditText cost, EditText type, EditText detail,
			TextView total, ArrayList<View> viewLists, File[] files,
			String[] fileNames) {
		ArrayList<ReData> list = new ArrayList<>();
		ReData data = new Reimburse_Request().new ReData(cost.getText()
				.toString().trim(), type.getText().toString().trim(), detail
				.getText().toString().trim());
		list.add(data);
		list.addAll(getReimburseList(viewLists));
		Reimburse_Request requestBeen = new Reimburse_Request(
				mUserModel.getUserId(), mUserModel.getUserToken(), "2", total
						.getText().toString().trim(), list);
		upload(ACTION.ACTION_APPROVAL_REIMBURSE, files, fileNames, requestBeen,
				new IUploadCallBack() {

					@Override
					public void onResponse(String responseStr)
							throws IOException {
						if (responseStr == null || responseStr.equals("")) {
							toastShowOnUI("服务器未响应");
							return;
						}
						Leave_Respond respond = GsonTool.getData(responseStr,
								Leave_Respond.class);
						if (respond.getFlag().equals("200")) {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().dismissProgressDialog();
									getView().showDialog();
								}
							});
						} else {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().dismissProgressDialog();
									getView().showErrorDialog();
								}
							});

						}
					}

					@Override
					public void onFailure(Request request, IOException exception) {
						getUIHandler().post(new Runnable() {

							@Override
							public void run() {
								getUIHandler().post(new Runnable() {

									@Override
									public void run() {
										getView().dismissProgressDialog();
										getView().showErrorDialog();
									}
								});
							}
						});
					}

					@Override
					public void onRequestProgress(long bytesWritten,
							long contentLength, boolean done) {

					}
				});
	}

	private ArrayList<ReData> getReimburseList(ArrayList<View> viewLists) {
		ArrayList<ReData> list = new ArrayList<>();
		for (int i = 0; i < viewLists.size(); i++) {
			View view = viewLists.get(i);
			EditText cost = (EditText) view.findViewById(R.id.cost_reimburse);
			EditText type = (EditText) view.findViewById(R.id.type_reimburse);
			EditText detail = (EditText) view
					.findViewById(R.id.detail_reimburse);
			ReData data = new Reimburse_Request().new ReData(cost.getText()
					.toString(), type.getText().toString(), detail.getText()
					.toString());
			list.add(data);
		}
		return list;
	}

}

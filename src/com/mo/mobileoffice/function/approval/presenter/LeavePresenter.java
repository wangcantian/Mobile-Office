package com.mo.mobileoffice.function.approval.presenter;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.Leave_Request;
import com.mo.mobileoffice.function.approval.bean.Leave_Respond;
import com.mo.mobileoffice.function.approval.contract.LeaveContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class LeavePresenter extends BaseMvpPresenter<LeaveContract.View>
		implements LeaveContract.Presenter {

	private UserModel mUserModel;

	public LeavePresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void showSingleDialog() {
		getView().showSingleChoiceDialog();
	}

	@Override
	public void showTimeDialog(int id) {
		getView().showTimeChoiceDialog(id);
	}

	@Override
	public void doHttpSubmit(String day, String reason, String start,
			String end, String type, File[] files, String[] fileNames) {
		Leave_Request request_data = new Leave_Request(mUserModel.getUserId(),
				mUserModel.getUserToken(), "1", day, reason, start, end, type);
		getView().showProgressDialog();
		upload(ACTION.ACTION_APPROVAL, files, fileNames, request_data,
				new IUploadCallBack() {

					@Override
					public void onResponse(String responseStr)
							throws IOException {
						if(responseStr==null||responseStr.equals("")){
							toastShowOnUI("服务器未响应");
							return;
						}
						final Leave_Respond respond = GsonTool.getData(
								responseStr, Leave_Respond.class);
						
						if (respond.getFlag()!=null&&respond.getFlag().equals("200")) {
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
								getView().dismissProgressDialog();
								getView().showErrorDialog();
							}
						});
					}

					@Override
					public void onRequestProgress(long bytesWritten,
							long contentLength, boolean done) {

					}
				});

	}

}

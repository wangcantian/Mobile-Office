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
import com.mo.mobileoffice.common.net.OkHttpClientManager;
import com.mo.mobileoffice.common.net.RequestArr;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.net.RequestFactory;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.Evection_Request;
import com.mo.mobileoffice.function.approval.bean.Evection_Request.EvectionData;
import com.mo.mobileoffice.function.approval.bean.Leave_Respond;
import com.mo.mobileoffice.function.approval.contract.EvectionContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class EvectionPresenter extends BaseMvpPresenter<EvectionContract.View>
		implements EvectionContract.Presenter {

	private UserModel mUserModel;

	public EvectionPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void dohttpSubmit(EditText address, TextView startTime,
			TextView endTime, EditText ev_day, EditText ev_cause,
			ArrayList<View> viewLists, File[] files, String[] fileNames) {
		ArrayList<EvectionData> list = new ArrayList<>();
		EvectionData data = new Evection_Request().new EvectionData(address
				.getText().toString(), startTime.getText().toString(), endTime
				.getText().toString());
		list.add(data);
		list.addAll(getDataList(viewLists));
		Evection_Request requestBeen = new Evection_Request(
				mUserModel.getUserId(), mUserModel.getUserToken(), "3", ev_day
						.getText().toString(), ev_cause.getText().toString(),
				list);
		getView().showProgressDialog();
		upload(ACTION.ACTION_APPROVAL_EVECTION, files, fileNames, requestBeen,
				new IUploadCallBack() {

					@Override
					public void onResponse(String responseStr)
							throws IOException {
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

	private ArrayList<EvectionData> getDataList(ArrayList<View> viewLists) {
		ArrayList<EvectionData> list = new ArrayList<>();
		for (int i = 0; i < viewLists.size(); i++) {
			View view = viewLists.get(i);
			EditText address = (EditText) view.findViewById(R.id.address);
			TextView startText = (TextView) view.findViewById(R.id.start_time);
			TextView endText = (TextView) view.findViewById(R.id.end_time);
			EvectionData data = new Evection_Request().new EvectionData(address
					.getText().toString(), startText.getText().toString(),
					endText.getText().toString());
			list.add(data);
		}
		return list;
	}

	@Override
	public void showTimeDialog(TextView tv, int id) {
		getView().showTimeChoiceDialog(tv, id);
	}

}

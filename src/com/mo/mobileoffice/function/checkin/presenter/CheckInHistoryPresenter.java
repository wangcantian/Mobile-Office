package com.mo.mobileoffice.function.checkin.presenter;

import java.io.IOException;

import android.content.Context;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryDataFragment_Respond;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryData_Request;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_RecentData_Request;
import com.mo.mobileoffice.function.checkin.contract.CheckInHistoryContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class CheckInHistoryPresenter extends
		BaseMvpPresenter<CheckInHistoryContract.View> implements
		CheckInHistoryContract.Presenter {

	private UserModel mModel;

	public CheckInHistoryPresenter(Context context) {
		super(context);
		mModel = new UserModel(context);
	}

	@Override
	public void requestData() {
		CheckIn_HistoryData_Request requestData = new CheckIn_HistoryData_Request(
				mModel.getUserId(), mModel.getUserToken());
		request(ACTION.ACTION_CHECKIN_HISTORYFRAGMENT, requestData,
				new CallBack() {

					@Override
					public void onResponse(String responseStr)
							throws IOException {
						// 获取回来的数据
						final CheckIn_HistoryDataFragment_Respond respond = GsonTool
								.getData(
										responseStr,
										CheckIn_HistoryDataFragment_Respond.class);
						if (respond != null && respond.getList() != null
								&& respond.getList().size() > 0) {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().setRespondData(respond.getList());
								}
							});
						}
					}

					@Override
					public void onFailure(Request request, IOException exception) {

					}
				});
	}

	@Override
	public void requestRecentData(String date) {

		CheckIn_RecentData_Request dataRequest = new CheckIn_RecentData_Request(
				mModel.getUserId(), mModel.getUserToken(), date);
		request(ACTION.ACTION_CHECKIN_HISTORYFRAGMENT, dataRequest,
				new CallBack() {

					@Override
					public void onResponse(String responseStr)
							throws IOException {
						final CheckIn_HistoryDataFragment_Respond respond = GsonTool
								.getData(
										responseStr,
										CheckIn_HistoryDataFragment_Respond.class);
						if (respond.getFlag().equals("200")) {
							if (respond.getList() != null
									&& respond.getList().size() > 0) {
								getUIHandler().post(new Runnable() {

									@Override
									public void run() {
										getView().notifyData(respond.getList());
									}
								});
							} else {
								getUIHandler().post(new Runnable() {

									@Override
									public void run() {
										getView().notifyData(null);
									}
								});
							}
						} else {
							getUIHandler().post(new Runnable() {

								@Override
								public void run() {
									getView().notifyData(null);
								}
							});
						}
					}

					@Override
					public void onFailure(Request request, IOException exception) {
						getView().notifyData(null);
					}
				});
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

}

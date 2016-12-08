package com.mo.mobileoffice.function.approval.presenter;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.mo.mobileoffice.common.base.BasePresenter.CallBack;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetal_Request;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Request;
import com.mo.mobileoffice.function.approval.bean.ApprovalDetail_Respond.DetailData;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond;
import com.mo.mobileoffice.function.approval.bean.MyApproval_Respond.MyApprovalData;
import com.mo.mobileoffice.function.approval.contract.ApprOfMeContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class ApprOfMePresenter extends BaseMvpPresenter<ApprOfMeContract.View>
		implements ApprOfMeContract.Presenter {
	private UserModel model;
	public ApprOfMePresenter(Context context) {
		super(context);
		model = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

	@Override
	public void doHttpSubmit() {
		MyApproval_Request requestBeen = new MyApproval_Request(
				model.getUserToken(),model.getUserId());
		baseShowProgressDialog();
		request(ACTION.ACTION_MYAPPROVAL, requestBeen, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				Log.e("test", responseStr);
				if (responseStr != null && !responseStr.equals("")) {
					MyApproval_Respond respond=GsonTool.getData(responseStr, MyApproval_Respond.class);
					if(respond.getFlag().equals("200")){
						final ArrayList<MyApprovalData> list=respond.getData();
						if(list!=null&&list.size()>0){
							getUIHandler().post(new Runnable() {
								
								@Override
								public void run() {
									getView().adapterRespondData(list);
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

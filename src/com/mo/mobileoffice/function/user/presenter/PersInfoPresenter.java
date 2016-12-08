package com.mo.mobileoffice.function.user.presenter;

import java.io.IOException;

import android.content.Context;

import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.dialog.ProgressDialog;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.user.bean.ModifyUser_Request;
import com.mo.mobileoffice.function.user.bean.UpdateUserInfo_Request;
import com.mo.mobileoffice.function.user.bean.UploadHead_Request;
import com.mo.mobileoffice.function.user.bean.UserBean;
import com.mo.mobileoffice.function.user.contract.PersInfoContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class PersInfoPresenter extends BaseMvpPresenter<PersInfoContract.View>
		implements PersInfoContract.Presenter {
	private UserModel mUserModel;
	private ProgressDialog mDialog;

	public PersInfoPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(getContext());
		mDialog = new ProgressDialog(getContext());
	}

	@Override
	public void detachView(boolean retainInstance) {
		if (!retainInstance) {
			unBinding();
		}
	}

	@Override
	public void changeBirthday(final String date) {
		if (date.isEmpty()) return;
		showProgressDialog();
		ModifyUser_Request bean = new ModifyUser_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		bean.birthday = date;
		request(ACTION.ACTION_MODIFY_USER_INFO, bean, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				dismissProgressDialog();
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {
					toastShowOnUI(result.getMsg());
					final UserBean bean = mUserModel.getCurrUserInfo();
					bean.setBirthday(date);
					mUserModel.notifyCurrUserInfo(bean);
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().refreshView(bean);
						}
					});
				} else {
					toastShowOnUI(result.getMsg());
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}

	@Override
	public void changeSex(final String sex) {
		if (sex.isEmpty()) return;
		showProgressDialog();
		ModifyUser_Request bean = new ModifyUser_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		bean.sex = sex;
		request(ACTION.ACTION_MODIFY_USER_INFO, bean, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				dismissProgressDialog();
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {
					toastShowOnUI(result.getMsg());
					final UserBean bean = mUserModel.getCurrUserInfo();
					bean.setSex(sex);
					mUserModel.notifyCurrUserInfo(bean);
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().refreshView(bean);
						}
					});
				} else {
					toastShowOnUI(result.getMsg());
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}

	@Override
	public void changeName(final String name) {
		if (name.isEmpty()) return;
		showProgressDialog();
		ModifyUser_Request bean = new ModifyUser_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		bean.name = name;
		request(ACTION.ACTION_MODIFY_USER_INFO, bean, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				dismissProgressDialog();
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {
					toastShowOnUI(result.getMsg());
					final UserBean bean = mUserModel.getCurrUserInfo();
					bean.setName(name);
					mUserModel.notifyCurrUserInfo(bean);
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().refreshView(bean);
						}
					});
				} else {
					toastShowOnUI(result.getMsg());
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}

	@Override
	public void changePhone(final String phone) {
		if (phone.isEmpty()) return;
		showProgressDialog();
		ModifyUser_Request bean = new ModifyUser_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		bean.mobile = phone;
		request(ACTION.ACTION_MODIFY_USER_INFO, bean, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				dismissProgressDialog();
				CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
				if (result.getFlag() == 200) {
					toastShowOnUI(result.getMsg());
					final UserBean bean = mUserModel.getCurrUserInfo();
					bean.setMobile(phone);
					mUserModel.notifyCurrUserInfo(bean);
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().refreshView(bean);
						}
					});
				} else {
					toastShowOnUI(result.getMsg());
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}

	@Override
	public void initView() {
		getView().refreshView(mUserModel.getCurrUserInfo());
	}

	@Override
	public void uploadHead(String path) {
		if (StringTool.isEmpty(path)) {
			return;
		}
		mDialog.show(1, "", false);
		UploadHead_Request request = new UploadHead_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		final String[] paths = FileTool.getPicCompressPaths(new String[] {path});
		upload(ACTION.ACTION_UPLOAD_HEAD, FileTool.getFiles(paths), 
				FileTool.getFilaNames(paths), request, new IUploadCallBack() {
					
					@Override
					public void onResponse(String responseStr) throws IOException {
						CommBean<?> result = GsonTool.getData(responseStr, CommBean.class);
						toastShowOnUI(result.getMsg());
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								upDateUserInfo();
								mDialog.setProgress(100);
								mDialog.dismiss();
							}
						});
					}
					
					@Override
					public void onFailure(Request request, IOException exception) {
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								mDialog.dismiss();
							}
						});
					}
					
					@Override
					public void onRequestProgress(final long bytesWritten, final long contentLength,
							boolean done) {
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								mDialog.setProgress((int) ((100 * bytesWritten) / contentLength));
								mDialog.setText(StringTool.bytes2kb(bytesWritten) + "/"
										+ StringTool.bytes2kb(contentLength));
							}
						});
					}
				});
	}
	
	public void upDateUserInfo() {
		UpdateUserInfo_Request request = new UpdateUserInfo_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_GET_USERINFO, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				final CommBean<UserBean> result = GsonTool.getBaseBeanData(responseStr, UserBean.class);
				if (result.getFlag() == SUCCESS) {
					mUserModel.notifyCurrUserInfo(result.getData());
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							if (getView() != null) {
								getView().refreshView(result.getData());
							}
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				
			}
		});
	}

}

package com.mo.mobileoffice.function.comm.presenter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.function.comm.contract.SplashContract;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.FloorInfo_Request;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.RoomInfo_Request;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.bean.UpdateUserInfo_Request;
import com.mo.mobileoffice.function.user.bean.UserBean;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

/** 闪屏页控制器 **/
public class SplashPresenter extends BaseMvpPresenter<SplashContract.View> implements SplashContract.Presenter {
	// 加载任务数
	private static final int LOADING_TASK_NUM = 2;

	private boolean mIsLogin = false;
	private int mLoadTaskFinishCount = 0;
	private UserModel mUserModel;

	public SplashPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void initSplash() {
		mIsLogin = PreTool.getBoolean(mContext, "is_login", false);
		if (mIsLogin) {
			// 当保存有登陆信息，先加载信息，在跳转到主页面
			upDateUserInfo();
		} else {
			getView().openLoginActivity();
		}
	}

	@Override
	public void detachView(boolean retainInstance) {
		unBinding();
	}
	
	private void upDateUserInfo() {
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
							initFloorInfo();
							initRoomInfo();
						}
					});
				} else {
					Intent intent = new Intent("ForceOfflineBroadCaseReceiver");
					mContext.sendBroadcast(intent);
				}
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						if (getView() != null) {
							getView().loadingFinish();
						}
					}
				});
			}
		});
	}

	private void initFloorInfo() {
		FloorInfo_Request request = new FloorInfo_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_FLOOR_INFO, request, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<FloorBean> cbl = GsonTool.getBaseBeanListData(responseStr, FloorBean.class);
				if (cbl.getFlag() == SUCCESS) {
					List<FloorBean> lists = cbl.getData();
					FloorDao dao = new FloorDao(mContext);
					dao.deleteAllDatas();
					dao.insertDatas(lists);
					dao.close();
				}
				addMarkFinish();
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				addMarkFinish();
			}
		});
	}

	private void initRoomInfo() {
		RoomInfo_Request request = new RoomInfo_Request(mUserModel.getUserId(),
				mUserModel.getUserToken());
		request(ACTION.ACTION_ROOM_INFO, request, new CallBack() {

			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<RoomBean> cbl = GsonTool.getBaseBeanListData(responseStr, RoomBean.class);
				if (cbl.getFlag() == SUCCESS) {
					List<RoomBean> lists = cbl.getData();
					RoomDao dao = new RoomDao(mContext);
					dao.deleteAllDatas();
					dao.insertDatas(lists);
					dao.close();
				}
				addMarkFinish();
			}

			@Override
			public void onFailure(Request request, IOException exception) {
				addMarkFinish();
			}
		});
	}

	private void addMarkFinish() {
		if (LOADING_TASK_NUM == ++mLoadTaskFinishCount) {
			if (getView() != null) {
				getView().loadingFinish();
			}
		}
	}

}

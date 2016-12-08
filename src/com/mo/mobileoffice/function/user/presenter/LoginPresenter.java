package com.mo.mobileoffice.function.user.presenter;

import java.io.IOException;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.FloorInfo_Request;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.RoomInfo_Request;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.bean.Login_Request;
import com.mo.mobileoffice.function.user.bean.UserBean;
import com.mo.mobileoffice.function.user.contract.LoginContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class LoginPresenter extends BaseMvpPresenter<LoginContract.View>
		implements LoginContract.Presenter {

	public LoginPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if (!retainInstance) {
			unBinding();
		}
	}

	@Override
	public void login(final String usename, final String pwd) {
		if (StringTool.isEmpty(usename) || StringTool.isEmpty(pwd)) {
			toastShowOnUI(R.string.username_and_pwd_not_null);
		} else {
			showProgressDialog();
			Login_Request bean = new Login_Request(usename, pwd);
			request(ACTION.ACTION_CHECK_USER_INFO, bean, new CallBack() {
				
				@Override
				public void onResponse(String responseStr) throws IOException {
					System.out.println(responseStr + "这是登陆信息");
					CommBean<UserBean> info = GsonTool.getBaseBeanData(responseStr, UserBean.class);
					if (info.getFlag() == SUCCESS) {// 200表示登陆成功
						PreTool.setString(getContext(), "user_info", GsonTool.toJson(info.getData()));
						PreTool.setBoolean(getContext(), "is_login", true);
						PreTool.setString(getContext(), "use_name", usename);
						PreTool.setString(getContext(), "use_pwd", pwd);
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								initFloorInfo();
								initRoomInfo();
								getView().loginSuccess();
							}
						});
					} 
					toastShowOnUI(info.getMsg());
					dismissProgressDialog();
				}
				
				@Override
				public void onFailure(Request request, IOException exception) {
					dismissProgressDialog();
				}
			});
		}
	}

	@Override
	public void signUnLogin() {
		PreTool.setBoolean(mContext, "is_login", false);
	}
	
	private void initFloorInfo() {
		UserModel userModel = new UserModel(getContext());
		FloorInfo_Request request = new FloorInfo_Request(userModel.getUserId(), userModel.getUserToken());
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
			}

			@Override
			public void onFailure(Request request, IOException exception) {
			}
		});
	}

	private void initRoomInfo() {
		UserModel userModel = new UserModel(getContext());
		RoomInfo_Request request = new RoomInfo_Request(userModel.getUserId(),
				userModel.getUserToken());
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
			}

			@Override
			public void onFailure(Request request, IOException exception) {
			}
		});
	}

}

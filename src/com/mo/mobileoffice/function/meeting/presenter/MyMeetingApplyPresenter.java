package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.MyApplyMeeting_Request;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MyMeetingApplyContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class MyMeetingApplyPresenter extends
		BaseMvpPresenter<MyMeetingApplyContract.View> implements
		MyMeetingApplyContract.Presenter {
	private UserModel mUserModel;
	private FloorDao mFloorDao;
	private RoomDao mRoomDao;
	private List<MeetingApplyBean> mAdapterList;

	public MyMeetingApplyPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
		mFloorDao = new FloorDao(context);
		mRoomDao = new RoomDao(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		if (!retainInstance) {
			mFloorDao.close();
			mRoomDao.close();
			unBinding();
		}
	}

	@Override
	public void requestMeetingApprHist(final boolean isPullToRefresh) {
		MyApplyMeeting_Request request = new MyApplyMeeting_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_MY_MEETING_APPLY, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<MeetingApplyBean> bean = GsonTool.getBaseBeanListData(responseStr, MeetingApplyBean.class);
				if (bean.getFlag() == SUCCESS) {
					mAdapterList.clear();
					mAdapterList.addAll(bean.getData());
				}
				toastShowOnUI(bean.getMsg());
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						if (getView() != null) {
							getView().notifyDataSetChange();
							if (isPullToRefresh) getView().resetListViewHead();
						}
					}
				});
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						if (getView() != null) {
							getView().notifyDataSetChange();
							if (isPullToRefresh) getView().resetListViewHead();
						}
					}
				});
			}
		});
	}

	@Override
	public void initAdapter() {
		if (mAdapterList == null) {
			mAdapterList = new ArrayList<MeetingApplyBean>();
			getView().initAdapter(mAdapterList);
		}
	}
	
	@Override
	public RoomBean getRoomBeanById(int id) {
		return mRoomDao.selectDataById(id + "");
	}

	@Override
	public FloorBean getFloorBeanById(int id) {
		return mFloorDao.selectDataById(id + "");
	}

}

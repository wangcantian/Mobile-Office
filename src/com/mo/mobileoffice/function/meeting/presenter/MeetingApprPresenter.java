package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.meeting.bean.GetAlreadyAppr_Request;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.GetWaitAppr_Request;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class MeetingApprPresenter extends
		BaseMvpPresenter<MeetingApprContract.View> implements
		MeetingApprContract.Presenter {
	private UserModel mUserModel;
	private FloorDao mFloorDao;
	private RoomDao mRoomDao;
	
	private List<MeetingApprBean> mAdapterLists;
	private List<MeetingApprBean> mWaitList;
	private List<MeetingApprBean> mAlreadList;

	public MeetingApprPresenter(Context context) {
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
	public void requestWaitAppr(final boolean isPullToRefresh) {
		GetWaitAppr_Request request = new GetWaitAppr_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_MEETING_WAIT_APPR, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<MeetingApprBean> bean = GsonTool.getBaseBeanListData(responseStr, MeetingApprBean.class);
				if (bean.getFlag() == SUCCESS) {
					mAdapterLists.clear();
					mAdapterLists.addAll(bean.getData());
					mWaitList = bean.getData();
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
				toastShowOnUI(bean.getMsg());
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
	public void requestAlreadyAppr(final boolean isPullToRefresh) {
		GetAlreadyAppr_Request request = new GetAlreadyAppr_Request(mUserModel.getUserId(), mUserModel.getUserToken());
		request(ACTION.ACTION_MEETING_ALREADY_APPR, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBeanList<MeetingApprBean> bean = GsonTool.getBaseBeanListData(responseStr, MeetingApprBean.class);
				if (bean.getFlag() == SUCCESS) {
					mAdapterLists.clear();
					mAdapterLists.addAll(bean.getData());
					mAlreadList = bean.getData();
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
				toastShowOnUI(bean.getMsg());
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
	public void initAdapterData() {
		mAdapterLists = new ArrayList<MeetingApprBean>();
		getView().initAdapter(mAdapterLists);
	}

	@Override
	public RoomBean getRoomBeanById(int id) {
		return mRoomDao.selectDataById(id + "");
	}

	@Override
	public FloorBean getFloorBeanById(int id) {
		return mFloorDao.selectDataById(id + "");
	}

	@Override
	public void menuTitleClick(boolean isWait) {
		if (isWait) {
			if (mWaitList == null) {
				mWaitList = new ArrayList<MeetingApprBean>();
				requestWaitAppr(false);
			}
			mAdapterLists.clear();
			mAdapterLists.addAll(mWaitList);
			getView().notifyDataSetChange();
		} else {
			if (mAlreadList == null) {
				mAlreadList = new ArrayList<MeetingApprBean>();
				requestAlreadyAppr(false);
			}
			mAdapterLists.clear();
			mAdapterLists.addAll(mAlreadList);
			getView().notifyDataSetChange();
		}
	}

}

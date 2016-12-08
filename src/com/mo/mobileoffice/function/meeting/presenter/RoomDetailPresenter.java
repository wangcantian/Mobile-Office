package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mo.mobileoffice.common.base.CommBeanList;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.function.meeting.bean.FindMeeting_Request;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.RoomDetailContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class RoomDetailPresenter extends
		BaseMvpPresenter<RoomDetailContract.View> implements
		RoomDetailContract.Presenter {
	private List<MeetingBean> mMeetingLists = null;
	private UserModel mUserModel;

	public RoomDetailPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(getContext());
	}

	@Override
	public void detachView(boolean retainInstance) {
		if (retainInstance) { // 解除View与Presenter之间的绑定
			unBinding();
		}
	}

	@Override
	public void requestMeetingList(int room_id) {
		if (mMeetingLists == null) {// 如果数据为空，就初始化适配器
			mMeetingLists = new ArrayList<MeetingBean>();
			getView().initAdapter(mMeetingLists);
		}
		getView().showDialog();
		FindMeeting_Request request = new FindMeeting_Request(mUserModel.getUserId(), mUserModel.getUserToken(), room_id);
		request(ACTION.ACTION_FIND_MEETING, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				System.out.println(responseStr);
				CommBeanList<MeetingBean> bean = GsonTool.getBaseBeanListData(responseStr, MeetingBean.class);
				if (bean.getFlag() == SUCCESS) {
					mMeetingLists.clear();
					mMeetingLists.addAll(bean.getData());
				} else {
					toastShowOnUI(bean.getMsg());
				}
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						getView().updateListView();
						getView().dismissDialog();
					}
				});
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						getView().dismissDialog();
					}
				});
			}
		});
	}

	@Override
	public RoomBean getRoomBeanFromDB(int room_id) {
		RoomDao dao = new RoomDao(getContext());
		RoomBean bean = dao.selectDataById(room_id + "");
		dao.close();
		return bean;
	}

	@Override
	public FloorBean getFloorBeanById(int floor_id) {
		FloorDao dao = new FloorDao(getContext());
		FloorBean bean = dao.selectDataById(floor_id + "");
		dao.close();
		return bean;
	}

}

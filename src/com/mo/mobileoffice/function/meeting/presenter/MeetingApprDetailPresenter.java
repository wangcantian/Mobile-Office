package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;

import android.content.Context;
import android.os.Bundle;

import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.ApprovalMeeting_Request;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprDetailContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class MeetingApprDetailPresenter extends
		BaseMvpPresenter<MeetingApprDetailContract.View> implements
		MeetingApprDetailContract.Presenter {
	private FloorDao mFloorDao;
	private RoomDao mRoomDao;
	private UserModel mUserModel;

	public MeetingApprDetailPresenter(Context context) {
		super(context);
		mFloorDao = new FloorDao(context);
		mRoomDao = new RoomDao(context);
		mUserModel = new UserModel(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		mFloorDao.close();
		mRoomDao.close();
		unBinding();
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
	public void requestApprSubmit(boolean isAgress, int app_id, String text) {
		ApprovalMeeting_Request request = null;
		if (isAgress) {
			request = new ApprovalMeeting_Request(mUserModel.getUserId(), mUserModel.getUserToken(),
					app_id, 1, StringTool.isEmpty(text) ? "" : text);
		} else {
			request = new ApprovalMeeting_Request(mUserModel.getUserId(), mUserModel.getUserToken(),
					app_id, 2, StringTool.isEmpty(text) ? "" : text);
		}
		showProgressDialog();
		request(ACTION.ACTION_APPROVAL_MEETING, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				CommBean<?> bean = GsonTool.getData(responseStr, CommBean.class);
				if (bean.getFlag() == SUCCESS) {
					getView().apprSuccess();
				}
				toastShowOnUI(bean.getMsg());
				dismissProgressDialog();
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				dismissProgressDialog();
			}
		});
	}

	private MeetingApprBean mApprBean;
	private MeetingApplyBean mApplyBean;
	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			if (bundle.getBoolean("isFromAppr")) {
				mApprBean = (MeetingApprBean) bundle.getSerializable("meeting_appr");
				getView().initMeetingAppr(mApprBean);
			} else {
				mApplyBean = (MeetingApplyBean) bundle.getSerializable("meeting_apply_info");
				getView().initMeetingApply(mApplyBean);
			}
		}
	}

}

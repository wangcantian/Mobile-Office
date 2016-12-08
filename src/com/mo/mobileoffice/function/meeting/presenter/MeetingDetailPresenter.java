package com.mo.mobileoffice.function.meeting.presenter;

import android.content.Context;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MeetingDetailContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;

public class MeetingDetailPresenter extends
		BaseMvpPresenter<MeetingDetailContract.View> implements
		MeetingDetailContract.Presenter {
	private RoomDao mRoomDao;
	private FloorDao mFloorDao;

	public MeetingDetailPresenter(Context context) {
		super(context);
		mRoomDao = new RoomDao(getContext());
		mFloorDao = new FloorDao(getContext());
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
	public FloorBean getFloorBeanById(String floor_id) {
		return mFloorDao.selectDataById(floor_id);
	}

	@Override
	public RoomBean getRoomBeanById(String room_id) {
		return mRoomDao.selectDataById(room_id);
	}

}

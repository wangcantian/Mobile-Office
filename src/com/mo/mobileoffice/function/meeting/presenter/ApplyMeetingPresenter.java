package com.mo.mobileoffice.function.meeting.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.ApplyMeeting_Request;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.JudgeRoom_Request;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.ApplyMeetingContract;
import com.mo.mobileoffice.function.meeting.dao.FloorDao;
import com.mo.mobileoffice.function.meeting.dao.RoomDao;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class ApplyMeetingPresenter extends
		BaseMvpPresenter<ApplyMeetingContract.View> implements
		ApplyMeetingContract.Presenter {
	private List<FloorBean> mFloorAdapterList;
	private List<RoomBean> mRoomAdapterList;
	private Calendar mStartCalendar;
	private Calendar mEndCalender;
	private RoomDao mRoomDao;
	private FloorDao mFloorDao;
	private UserModel mUserModel;
	// 标记当前的开始时间和结束时间、状态是否通过验证
	private boolean isValidity = false;
	private int mFloorId = -1;
	private int mRoomId = -1;
	private int mLastFloorId = -1;
	private int mLastRoomId = -1;
	private String mFloorName = "";
	private String mRoomName = "";

	public ApplyMeetingPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(getContext());
		mFloorAdapterList = new FloorDao(getContext()).selectAllDatas();
		mRoomDao = new RoomDao(getContext());
		mFloorDao = new FloorDao(getContext());
		mRoomAdapterList = new ArrayList<RoomBean>();
	}

	@Override
	public void showFloorListDialog() {
		getView().showFloorDialog(mFloorAdapterList);
	}

	@Override
	public void showRoomListDialog() {
		if (mFloorId == -1) {
			toastShowOnUI(R.string.please_select_floor);
			return;
		}
		mRoomAdapterList.clear();
		mRoomAdapterList.addAll(mRoomDao.getRoomBeanByFloorId(mFloorId + ""));
		getView().showRoomDialog(mRoomAdapterList);
	}

	@Override
	public void applyMeeting(String them, String content) {
		if (mRoomId == -1) {
			toastShowOnUI(R.string.please_select_room);
			return;
		}
		if (!isValidity) {
			toastShowOnUI(R.string.please_check_time_info);
			return;
		}
		if (StringTool.isEmpty(them) && StringTool.isEmpty(content)) {
			toastShowOnUI(R.string.meeting_title_or_content_not_empty);
			return;
		}
		getView().showLoadingDialog(false);
		String start = StringTool.DateToString1(mStartCalendar.getTime());
		String end = StringTool.DateToString1(mEndCalender.getTime());
		ApplyMeeting_Request request = new ApplyMeeting_Request(mUserModel.getUserId(), mUserModel.getUserToken(),
				mRoomId, start, end, them, content);
		request(ACTION.ACTION_APPLY_MEETING, request, new CallBack() {
			
			@Override
			public void onResponse(String responseStr) throws IOException {
				final CommBean<?> bean = GsonTool.getData(responseStr, CommBean.class);
				toastShowOnUI(bean.getMsg());
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						getView().dismissDialog(false);
						if (bean.getFlag() == SUCCESS) {
							getView().applyMeetingSuccess();
						}
					}
				});
			}
			
			@Override
			public void onFailure(Request request, IOException exception) {
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						getView().dismissDialog(false);
					}
				});
			}
		});
	}

	@Override
	public void requestStateByRoomId() {
		isValidity = false;
			getView().showLoadingDialog(true);
			String start = StringTool.DateToString1(mStartCalendar.getTime());
			String end = StringTool.DateToString1(mEndCalender.getTime());
			JudgeRoom_Request request = new JudgeRoom_Request(mUserModel.getUserId(), mUserModel.getUserToken(),
					mRoomId, start, end);
			request(ACTION.ACTION_JUDGE_ROOM, request, new CallBack() {
				
				@Override
				public void onResponse(String responseStr) throws IOException {
					final CommBean<Integer> bean = GsonTool.getBaseBeanData(responseStr, Integer.class);
					if (bean.getFlag() == SUCCESS) {
						isValidity = true;
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								getView().updateState(bean.getData());
								getView().dismissDialog(true);
							}
						});
					} else {
						getUIHandler().post(new Runnable() {
							
							@Override
							public void run() {
								getView().dismissDialog(true);
							}
						});
					}
				}
				
				@Override
				public void onFailure(Request request, IOException exception) {
					getUIHandler().post(new Runnable() {
						
						@Override
						public void run() {
							getView().dismissDialog(true);
						}
					});
				}
			});
	}
	
	@Override
	public void detachView(boolean retainInstance) {
		if (!retainInstance) {
			mRoomDao.close();
			mFloorDao.close();
			unBinding();
		}
	}

	@Override
	public void showStartTimeDialog() {
		if (mStartCalendar == null) {
			mStartCalendar = Calendar.getInstance();
		}
		getView().showTimeDialog(mStartCalendar, true);
	}

	@Override
	public void showEndTimeDialog() {
		if (mEndCalender == null) {
			mEndCalender = Calendar.getInstance();
			mEndCalender.add(Calendar.MINUTE, 40);
		}
		getView().showTimeDialog(mEndCalender, false);
	}

	@Override
	public void checkTimeValidity() {
		isValidity = false;
		if (mStartCalendar != null && mEndCalender != null) {
			switch (mStartCalendar.compareTo(mEndCalender)) {
			case 0:
				// 开始时间不能等于结束时间
				toastShowOnUI(R.string.start_time_not_equal_end);
				isValidity = false;
				break;
			case -1:
				if (mRoomId != -1 && mRoomId != mLastRoomId) {
					requestStateByRoomId();
				}
				break;
			case 1:
				// 开始时间不能大于结束时间
				toastShowOnUI(R.string.start_time_not_greater_than_end);
				isValidity = false;
				break;
			}
		}
	}

	@Override
	public void init(Bundle bundle) {
		if (bundle != null) {
			int room_id = bundle.getInt("room_id", -1);
			if (room_id != -1) {
				RoomBean roomBean = mRoomDao.selectDataById(room_id + "");
				mRoomName = roomBean.getRoom_num();
				mRoomId = roomBean.getId();
				FloorBean floorBean = mFloorDao.selectDataById(roomBean.getFloor_id() + "");
				mFloorName = floorBean.getFloor_name();
				mFloorId = floorBean.getId();
				mLastFloorId = mFloorId;
				getView().updateFloorRoomView(mFloorName, mRoomName);
			}
		}
	}

	@Override
	public void OnItemClick(int position, boolean isFloorList) {
		if (isFloorList) {
			mFloorId = mFloorAdapterList.get(position).getId();
			mFloorName = mFloorAdapterList.get(position).getFloor_name();
			if (mLastFloorId != mFloorId) {
				mRoomName = "";
				mRoomId = -1;
				mLastFloorId = mFloorId;
			}
		} else {
			mRoomId = mRoomAdapterList.get(position).getId();
			mRoomName = mRoomAdapterList.get(position).getRoom_num();
			checkTimeValidity();
		}
		getView().updateFloorRoomView(mFloorName, mRoomName);
	}

}

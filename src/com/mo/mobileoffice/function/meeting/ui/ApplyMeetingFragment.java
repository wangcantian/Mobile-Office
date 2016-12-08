package com.mo.mobileoffice.function.meeting.ui;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.dialog.ListViewDialog;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.ApplyMeetingContract;
import com.mo.mobileoffice.function.meeting.contract.ApplyMeetingContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.ApplyMeetingPresenter;

/** 会议室申请页 **/
public class ApplyMeetingFragment extends
		MvpIdeaFragment<ApplyMeetingContract.Presenter> implements
		ApplyMeetingContract.View {
	private TextView tv_floor;
	private TextView tv_room;
	private TextView tv_state;
	private TextView tv_start_time;
	private TextView tv_end_time;
	private TextView tv_title;
	private TextView tv_content;
	private ProgressBar pg_loading;
	private LoadingDialog mLoadingDialog;
	private boolean mIsFromRoomDetail;

	@Override
	protected void init() {
		mIsFromRoomDetail = getBundle().getBoolean("isFromRoomDetail", false);
		tv_floor = findViewById(R.id.tv_floor);
		tv_room = findViewById(R.id.tv_room);
		tv_state = findViewById(R.id.tv_state);
		tv_start_time = findViewById(R.id.tv_start_time);
		tv_end_time = findViewById(R.id.tv_end_time);
		pg_loading = findViewById(R.id.pg_loading);
		tv_title = findViewById(R.id.et_meeting_title);
		tv_content = findViewById(R.id.et_meeting_content);
		findViewById(R.id.rl_floor).setOnClickListener(this);
		findViewById(R.id.rl_room).setOnClickListener(this);
		findViewById(R.id.rl_start_time).setOnClickListener(this);
		findViewById(R.id.rl_end_time).setOnClickListener(this);

		tv_floor.setText(getResources().getString(R.string.floor2, ""));
		tv_room.setText(getResources().getString(R.string.room2, ""));
		tv_state.setText(getResources().getString(R.string.state2, ""));
		tv_start_time.setText(getResources().getString(R.string.meeting_start_time, ""));
		tv_end_time.setText(getResources().getString(R.string.meeting_end_time, ""));
		setTitle(getResources().getString(R.string.apply_meeting));
		setRight(getResources().getString(R.string.apply));
		
		getPresenter().init(getBundle());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_floor:
			getPresenter().showFloorListDialog();
			break;
		case R.id.rl_room:
			getPresenter().showRoomListDialog();
			break;
		case R.id.rl_start_time:
			getPresenter().showStartTimeDialog();
			break;
		case R.id.rl_end_time:
			getPresenter().showEndTimeDialog();
			break;
		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {
		String title = tv_title.getText().toString();
		String content = tv_content.getText().toString();
		getPresenter().applyMeeting(title, content);
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_apply_meeting;
	}

	@Override
	public void showFloorDialog(final List<FloorBean> floorList) {
		new ListViewDialog<FloorBean>(getActivity(), floorList,
				R.layout.listitem_floor, new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						getPresenter().OnItemClick(position, true);
					}
				}) {

			@Override
			public void convert(ViewHolder holder, FloorBean item, int position) {
				holder.setText(R.id.tv_floor, item.getFloor_name());
			}
		}.show();
	}

	@Override
	public void showRoomDialog(final List<RoomBean> roomList) {
		new ListViewDialog<RoomBean>(getActivity(), roomList,
				R.layout.listitem_floor, new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						getPresenter().OnItemClick(position, false);
					}
				}) {

			@Override
			public void convert(ViewHolder holder, RoomBean item, int position) {
				holder.setText(R.id.tv_floor, item.getRoom_num());
			}
		}.show();
	}

	@Override
	public void applyMeetingSuccess() {
		if (mIsFromRoomDetail)
			getActivity().setResult(1);
		getActivity().finish();
	}

	@Override
	protected Presenter createPresenter() {
		return new ApplyMeetingPresenter(getActivity());
	}
	
	/** 弹出时间选择器 **/
	@SuppressLint("InflateParams") @Override
	public void showTimeDialog(final Calendar calender, final boolean isStartTime) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_datetimedialog, null);
		final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timepicker);
		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
		datePicker.updateDate(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
		timePicker.setCurrentHour(calender.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calender.get(Calendar.MINUTE));
		timePicker.setIs24HourView(true);
		final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).setView(view)
									.setPositiveButton(R.string.finish, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				calender.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
						timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
				if (isStartTime) {
					tv_start_time.setText(getResources().getString(R.string.meeting_start_time, StringTool.DateToString1(calender.getTime())));
				} else {
					tv_end_time.setText(getResources().getString(R.string.meeting_end_time, StringTool.DateToString1(calender.getTime())));
				}
				dialog.dismiss();
				getPresenter().checkTimeValidity();
			}
		}).create();
		mDialog.show();
	}

	@Override
	public void updateState(int state) {
		if (state == 0) {
			tv_state.setText(getResources().getString(R.string.state2, "空闲"));
		} else if (state == 1) {
			tv_state.setText(getResources().getString(R.string.state2, "占用"));
		}
	}

	@Override
	public void showLoadingDialog(boolean isProgressBar) {
		if (!isProgressBar) {
			if (mLoadingDialog == null) {
				mLoadingDialog = new LoadingDialog(getActivity());
			}
			mLoadingDialog.show(false);
		} else {
			pg_loading.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void dismissDialog(boolean isProgressBar) {
		if (isProgressBar) {
			pg_loading.setVisibility(View.GONE);
		} else if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	@Override
	public void updateFloorRoomView(String floor_name, String room_name) {
		tv_floor.setText(getResources().getString(R.string.floor2, floor_name));
		tv_room.setText(getResources().getString(R.string.room2, room_name));
	}

}

package com.mo.mobileoffice.function.meeting.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.RoomDetailContract;
import com.mo.mobileoffice.function.meeting.contract.RoomDetailContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.RoomDetailPresenter;

public class RoomDetailFragment extends
		MvpIdeaFragment<RoomDetailContract.Presenter> implements
		RoomDetailContract.View, OnItemClickListener {
	
	private LoadingDialog mDialog;
	private ListView lv_meeting;
	
	private CommonAdapter<MeetingBean> mMeetingAdapter;

	@Override
	protected void init() {
		int room_id = getBundle().getInt("room_id");
		setTitle(getResources().getString(R.string.meeting_room));
		setRight(getResources().getString(R.string.refresh));
		lv_meeting = findViewById(R.id.lv_meeting);
		lv_meeting.setOnItemClickListener(this);
		RoomBean bean = getPresenter().getRoomBeanFromDB(room_id);
		initTextViewDrawable((TextView)findViewById(R.id.tv_air), bean.getAir_con() == 1);
		initTextViewDrawable((TextView)findViewById(R.id.tv_wifi), bean.getWifi() == 1);
		initTextViewDrawable((TextView)findViewById(R.id.tv_projector), bean.getProjector() == 1);
		((TextView)findViewById(R.id.tv_max_people)).setText(bean.getSeat() + "");
		((TextView)findViewById(R.id.tv_room)).setText(bean.getRoom_num());
		FloorBean floorbean = getPresenter().getFloorBeanById(bean.getFloor_id());
		((TextView)findViewById(R.id.tv_floor)).setText(floorbean.getFloor_name());
		((TextView)findViewById(R.id.tv_floor_num)).setText(getResources().getString(R.string.num_L, floorbean.getFloor_num()));
		findViewById(R.id.btn_apply).setOnClickListener(this);
		getPresenter().requestMeetingList(room_id);
	}
	
	private void initTextViewDrawable(TextView tv, boolean isHave) {
		tv.setCompoundDrawablesWithIntrinsicBounds(tv.getCompoundDrawables()[0], null,
				isHave ? getResources().getDrawable(R.drawable.ico_green_hook) :
							getResources().getDrawable(R.drawable.ico_red_fork), null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MeetingBean bean = mMeetingAdapter.getItem(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("meeting_info", bean);
		openIdeaActivity(FragmentEnum.FRAGMENT_MEETING_DETAIL, bundle);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_apply:// 申请按钮
			Bundle bundle = new Bundle();
			bundle.putInt("room_id", getBundle().getInt("room_id"));
			bundle.putBoolean("isFromRoomDetail", true);
			openIdeaActivityForResult(FragmentEnum.FRAGMENT_APPLY_MEETING, 2, bundle);
			break;
		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {
		getPresenter().requestMeetingList(getBundle().getInt("room_id"));
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_room_detail;
	}

	@Override
	protected Presenter createPresenter() {
		return new RoomDetailPresenter(getActivity());
	}

	@Override
	public void updateListView() {
		mMeetingAdapter.notifyDataSetChanged();
	}

	@Override
	public void initAdapter(List<MeetingBean> meetingLists) {
		mMeetingAdapter = new CommonAdapter<MeetingBean>(getActivity(), meetingLists, R.layout.listitem_meeting) {
			
			@Override
			public void convert(ViewHolder holder, MeetingBean item, int position) {
				holder.setText(R.id.tv_start_time, getResources().getString(R.string.meeting_start_time, item.getStart()));
				holder.setText(R.id.tv_end_time, getResources().getString(R.string.meeting_end_time, item.getEnd()));
				if (item.getState() == 0) {
					holder.setText(R.id.tv_state, getResources().getString(R.string.already_apply));
				} else if (item.getState() == 1) {
					holder.setText(R.id.tv_state, getResources().getString(R.string.already_approval));
				} else if (item.getState() == 2) {
					holder.setText(R.id.tv_state, getResources().getString(R.string.carried_out));
				} else if (item.getState() == 3) {
					holder.setText(R.id.tv_state, getResources().getString(R.string.has_ended));
				}
			}
		};
		lv_meeting.setAdapter(mMeetingAdapter);
	}

	@Override
	public void showDialog() {
		if (mDialog == null) {
			mDialog = new LoadingDialog(getActivity());
		}
		mDialog.show(false);
	}

	@Override
	public void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == 1) {
			getPresenter().requestMeetingList(getBundle().getInt("room_id"));
		}
	}
}

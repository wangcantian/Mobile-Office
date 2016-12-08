package com.mo.mobileoffice.function.meeting.ui;

import android.view.View;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MeetingDetailContract;
import com.mo.mobileoffice.function.meeting.contract.MeetingDetailContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.MeetingDetailPresenter;

/** 会议室详情页 **/
public class MeetingDetailFragment extends
		MvpIdeaFragment<MeetingDetailContract.Presenter> implements
		MeetingDetailContract.View {

	@Override
	protected void init() {
		MeetingBean bean = (MeetingBean) getBundle().getSerializable("meeting_info");
		((TextView)findViewById(R.id.tv_them)).setText(bean.getTitle());
		((TextView)findViewById(R.id.tv_content)).setText(bean.getContent());
		((TextView)findViewById(R.id.tv_start_time)).setText(getResources().getString(R.string.meeting_start_time, bean.getStart()));
		((TextView)findViewById(R.id.tv_end_time)).setText(getResources().getString(R.string.meeting_end_time, bean.getEnd()));
		((TextView)findViewById(R.id.tv_apply)).setText(getResources().getString(R.string.apply_info, bean.getUser_name() + "  " + bean.getApp_time()));
		((TextView)findViewById(R.id.tv_approval)).setText(getResources().getString(R.string.approval_info, bean.getApprover() 
				+ "  " + (StringTool.isEmpty(bean.getApproval_time()) ? "" : bean.getApproval_time())));
		if (bean.getState() == 0) {
			((TextView)findViewById(R.id.tv_state)).setText(R.string.already_apply);
		} else if (bean.getState() == 1) {
			((TextView)findViewById(R.id.tv_state)).setText(R.string.already_approval);
		} else if (bean.getState() == 2) {
			((TextView)findViewById(R.id.tv_state)).setText(R.string.carried_out);
		} else if (bean.getState() == 3) {
			((TextView)findViewById(R.id.tv_state)).setText(R.string.has_ended);
		}
		RoomBean roombean = getPresenter().getRoomBeanById(bean.getRoom_id() + "");
		((TextView)findViewById(R.id.tv_room)).setText(roombean.getRoom_num());
		FloorBean floorBean = getPresenter().getFloorBeanById(roombean.getFloor_id() + "");
		((TextView)findViewById(R.id.tv_floor_num)).setText(getResources().getString(R.string.num_L, floorBean.getFloor_num()));
		((TextView)findViewById(R.id.tv_floor)).setText(floorBean.getFloor_name());
		setTitle(getResources().getString(R.string.meeting_detail));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_meeting_detail;
	}

	@Override
	protected Presenter createPresenter() {
		return new MeetingDetailPresenter(getActivity());
	}

}

package com.mo.mobileoffice.function.meeting.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.widget.DefaultListView;
import com.mo.mobileoffice.common.widget.DefaultListView.RequestRefreshListener;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApplyBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MyMeetingApplyContract;
import com.mo.mobileoffice.function.meeting.contract.MyMeetingApplyContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.MyMeetingApplyPresenter;

public class MyMeetingApplyFragment extends
		MvpIdeaFragment<MyMeetingApplyContract.Presenter> implements
		MyMeetingApplyContract.View, RequestRefreshListener, OnItemClickListener {
	private DefaultListView mListView;
	private CommonAdapter<MeetingApplyBean> mAdapter;

	@Override
	protected void init() {
		mListView = findViewById(R.id.lv_meeting_appr);
		mListView.setRequestRefreshListView(this);
		mListView.setOnItemClickListener(this);
		setTitle(getResources().getString(R.string.my_meeting_apply));
		getPresenter().initAdapter();
		getPresenter().requestMeetingApprHist(false);
	}
	
	@Override
	public void onClick(View v) {

	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected Presenter createPresenter() {
		return new MyMeetingApplyPresenter(getActivity());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_my_meeting_appr;
	}

	@Override
	public void onRefresh() {
		getPresenter().requestMeetingApprHist(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MeetingApplyBean bean = mAdapter.getItem(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("meeting_apply_info", bean);
		bundle.putBoolean("isFromAppr", false);
		openIdeaActivity(FragmentEnum.FRAGMENT_MEETING_APPR_DETAIL, bundle);
	}

	@Override
	public void notifyDataSetChange() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initAdapter(List<MeetingApplyBean> lists) {
		mAdapter = new CommonAdapter<MeetingApplyBean>(getActivity(), lists, R.layout.listitem_meeting_appr) {
			
			@Override
			public void convert(ViewHolder holder, MeetingApplyBean item, int position) {
				holder.setText(R.id.tv_title, item.getTitle());
				holder.setText(R.id.tv_time, item.getApp_time());
				holder.setText(R.id.tv_apply_name, item.getUser_name());
				RoomBean roomBean = getPresenter().getRoomBeanById(item.getRoom_id());
				FloorBean floorBean = getPresenter().getFloorBeanById(roomBean.getFloor_id());
				String roomInfo = floorBean.getFloor_name() + "-" + roomBean.getRoom_num();
				holder.setText(R.id.tv_room, roomInfo);
				if (item.getState() == 0) {
					holder.setText(R.id.tv_state, "未审批");
				} else {
					holder.setText(R.id.tv_state, "已审批");
				}
			}
		};
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void resetListViewHead() {
		mListView.resetHeaderView();
	}

}

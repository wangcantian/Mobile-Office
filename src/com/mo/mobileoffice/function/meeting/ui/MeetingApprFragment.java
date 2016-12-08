package com.mo.mobileoffice.function.meeting.ui;

import java.util.List;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.widget.DefaultListView;
import com.mo.mobileoffice.common.widget.DefaultListView.RequestRefreshListener;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.MeetingApprBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprContract;
import com.mo.mobileoffice.function.meeting.contract.MeetingApprContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.MeetingApprPresenter;

/** 会议审批页 **/
public class MeetingApprFragment extends
		MvpIdeaFragment<MeetingApprContract.Presenter> implements
		MeetingApprContract.View, RequestRefreshListener, OnItemClickListener {
	
	private View mScrollBar; 
	private TextView waitApproval;
	private TextView historyAproval;
	private DefaultListView mListView;
	private RelativeLayout mScrollBarContainer;
	
	private int scrollBarWidth = 0;
	private CommonAdapter<MeetingApprBean> mAdapter;

	@Override
	protected void init() {
		mCurrSwitchId = R.id.tv_waitApproval;
		mScrollBar = findViewById(R.id.scrollbar);
		waitApproval = findViewById(R.id.tv_waitApproval);
		historyAproval = findViewById(R.id.tv_historyApproval);
		mListView = findViewById(R.id.lv_meeting);
		mScrollBarContainer = (RelativeLayout) mScrollBar.getParent();
		waitApproval.setOnClickListener(this);
		historyAproval.setOnClickListener(this);
		mListView.setRequestRefreshListView(this);
		mListView.setOnItemClickListener(this);
		setTitle(getResources().getString(R.string.meeting_approval));
		initiScrollBar();
		initViewAnimation();
		getPresenter().initAdapterData();
		getPresenter().menuTitleClick(true);
	}
	
	private ObjectAnimator toWaitAnimator;
	private ObjectAnimator toHistoryAnimator;

	private ObjectAnimator waitNormalAnimator;
	private ObjectAnimator waitActiveAnimator;

	private ObjectAnimator hisNormalAnimator;
	private ObjectAnimator hisActiveAnimator;

	private void initViewAnimation() {
		toWaitAnimator = ObjectAnimator.ofFloat(mScrollBar, "translationX",
				scrollBarWidth / 2, 0);
		toWaitAnimator.setDuration(300);
		toWaitAnimator.setInterpolator(new DecelerateInterpolator());
		toHistoryAnimator = ObjectAnimator.ofFloat(mScrollBar, "translationX",
				0, scrollBarWidth / 2);
		toHistoryAnimator.setDuration(300);
		toHistoryAnimator.setInterpolator(new DecelerateInterpolator());

		waitNormalAnimator = ObjectAnimator.ofInt(waitApproval, "textColor",
				Color.parseColor("#ffffff"), Color.parseColor("#7a7774"));
		waitNormalAnimator.setDuration(300);
		waitNormalAnimator.setInterpolator(new DecelerateInterpolator());
		waitNormalAnimator.setEvaluator(new ArgbEvaluator());
		waitActiveAnimator = ObjectAnimator.ofInt(waitApproval, "textColor",
				Color.parseColor("#7a7774"), Color.parseColor("#ffffff"));
		waitActiveAnimator.setDuration(300);
		waitActiveAnimator.setInterpolator(new DecelerateInterpolator());
		waitActiveAnimator.setEvaluator(new ArgbEvaluator());

		hisNormalAnimator = ObjectAnimator.ofInt(historyAproval, "textColor",
				Color.parseColor("#ffffff"), Color.parseColor("#7a7774"));
		hisNormalAnimator.setDuration(300);
		hisNormalAnimator.setEvaluator(new ArgbEvaluator());
		hisNormalAnimator.setInterpolator(new DecelerateInterpolator());
		hisActiveAnimator = ObjectAnimator.ofInt(historyAproval, "textColor",
				Color.parseColor("#7a7774"), Color.parseColor("#ffffff"));
		hisActiveAnimator.setDuration(300);
		hisActiveAnimator.setInterpolator(new DecelerateInterpolator());
		hisActiveAnimator.setEvaluator(new ArgbEvaluator());
	}

	private void initiScrollBar() {
		mScrollBarContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						int width = mScrollBarContainer.getWidth();
						scrollBarWidth = width;
						RelativeLayout.LayoutParams lp = (LayoutParams) mScrollBar
								.getLayoutParams();
						lp.width = width / 2;
						mScrollBar.setLayoutParams(lp);
						initViewAnimation();
						mScrollBarContainer.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}
	
	private int mCurrSwitchId = -1;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_waitApproval:
			if (mCurrSwitchId == R.id.tv_waitApproval) return;
			getPresenter().menuTitleClick(true);
			mCurrSwitchId = R.id.tv_waitApproval;
			toWaitAnimator.start();
			waitActiveAnimator.start();
			hisNormalAnimator.start();
			break;
		case R.id.tv_historyApproval:
			if (mCurrSwitchId == R.id.tv_historyApproval) return;
			getPresenter().menuTitleClick(false);
			mCurrSwitchId = R.id.tv_historyApproval;
			toHistoryAnimator.start();
			hisActiveAnimator.start();
			waitNormalAnimator.start();
			break;
		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_meeting_approval;
	}

	@Override
	protected Presenter createPresenter() {
		return new MeetingApprPresenter(getActivity());
	}

	@Override
	public void initAdapter(List<MeetingApprBean> lists) {
		mAdapter = new CommonAdapter<MeetingApprBean>(getActivity(), lists, R.layout.listitem_meeting_appr) {
			
			@Override
			public void convert(ViewHolder holder, MeetingApprBean item, int position) {
				holder.setText(R.id.tv_title, item.getTitle());
				holder.setText(R.id.tv_time, item.getApp_time());
				holder.setText(R.id.tv_apply_name, item.getUser_name());
				RoomBean roomBean = getPresenter().getRoomBeanById(item.getRoom_id());
				FloorBean floorBean = getPresenter().getFloorBeanById(roomBean.getFloor_id());
				String roomInfo = floorBean.getFloor_name() + "-" + roomBean.getRoom_num();
				holder.setText(R.id.tv_room, roomInfo);
				if (item.getState() == 0) {
					holder.setText(R.id.tv_state, getResources().getString(R.string.not_approved));
				} else {
					holder.setText(R.id.tv_state, getResources().getString(R.string.already_approval));
				}
			}
		};
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void notifyDataSetChange() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onRefresh() {
		if (mCurrSwitchId == R.id.tv_waitApproval) {
			getPresenter().requestWaitAppr(true);
		} else if (mCurrSwitchId == R.id.tv_historyApproval) {
			getPresenter().requestAlreadyAppr(true);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("meeting_appr", mAdapter.getItem(position));
		bundle.putBoolean("isFromAppr", true);
		openIdeaActivityForResult(FragmentEnum.FRAGMENT_MEETING_APPR_DETAIL, 1, bundle);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 1) {
			getPresenter().requestWaitAppr(false);
		}
	}

	@Override
	public void resetListViewHead() {
		mListView.resetHeaderView();
	}

}

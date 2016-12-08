package com.mo.mobileoffice.function.checkin.ui;

import java.util.ArrayList;

import android.view.View;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.common.widget.DefaultSprinner;
import com.mo.mobileoffice.common.widget.DefaultSprinner.OnItemClickToRefresh;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryDataFragment_Respond.Data;
import com.mo.mobileoffice.function.checkin.contract.CheckInHistoryContract;
import com.mo.mobileoffice.function.checkin.contract.CheckInHistoryContract.Presenter;
import com.mo.mobileoffice.function.checkin.presenter.CheckInHistoryPresenter;

public class CheckinHistoryFragment extends
		MvpIdeaFragment<CheckInHistoryContract.Presenter> implements
		CheckInHistoryContract.View, OnItemClickToRefresh {

	private DefaultSprinner sprinner;
	boolean isLoading;

	@Override
	protected void init() {
		sprinner = findViewById(R.id.sprinner);
		getPresenter().requestData();
		sprinner.setOnItemClickRefresh(this);
		setTitle(getResources().getString(R.string.checkin_history));
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void rightOnClick() {
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_checkin_history;
	}

	@Override
	public void setRespondData(ArrayList<Data> list) {
		if (sprinner != null && list != null && list.size() > 0) {
			sprinner.setFreshData(getActivity(), list,
					R.layout.fragment_checkinhistory_item);
			sprinner.setSecondViewGone();
			sprinner.setViewVisibility();
		} else {
			sprinner.setViewGone();
			sprinner.setSecondViewVisiable();
		}
	}

	// 请求网络操作
	@Override
	public void clickToRefresh(int currentFlag) {
		switch (currentFlag) {
		case DefaultSprinner.RECENT_ONE:
			String date = StringTool.transformDate1(1);
			getPresenter().requestRecentData(date);
			break;
		case DefaultSprinner.RECENT_TWO:
			String date1 = StringTool.transformDate1(2);
			getPresenter().requestRecentData(date1);
			break;
		case DefaultSprinner.RECENT_THREE:
			String date2 = StringTool.transformDate1(3);
			getPresenter().requestRecentData(date2);
			break;

		default:
			break;
		}
	}

	@Override
	public void notifyData(ArrayList<Data> data) {
		sprinner.notifyAdapter(data);
	}

	@Override
	protected Presenter createPresenter() {
		return new CheckInHistoryPresenter(getActivity());
	}

}

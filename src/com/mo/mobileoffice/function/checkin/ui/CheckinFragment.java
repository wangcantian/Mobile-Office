package com.mo.mobileoffice.function.checkin.ui;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpRadioFragment;
import com.mo.mobileoffice.common.widget.CalendarTest;
import com.mo.mobileoffice.function.checkin.contract.CheckInContract;
import com.mo.mobileoffice.function.checkin.contract.CheckInContract.Presenter;
import com.mo.mobileoffice.function.checkin.presenter.CheckInPresenter;

public class CheckinFragment extends
		MvpRadioFragment<CheckInContract.Presenter> implements
		CheckInContract.View {
	private Button mCheckIn;
	private CalendarTest calendar;
	private TextView date;
	private TextView storeName;
	private TextView addressName;
	private ImageView userSrc;
	private TextView link;

	@Override
	protected void init() {
		calendar = findViewById(R.id.calendar);
		mCheckIn = findViewById(R.id.checkin);
		date = findViewById(R.id.date);
		storeName = findViewById(R.id.storeName);
		addressName = findViewById(R.id.addressName);
		userSrc = findViewById(R.id.mapImage);
		link = findViewById(R.id.link);

		mCheckIn.setOnClickListener(this);
		userSrc.setOnClickListener(this);
		link.setOnClickListener(this);

		getPresenter().setAddress(storeName, addressName);
		getPresenter().initLocation();
		getPresenter().doDateSet();
		getPresenter().getCheckInHistory();
	}

	double lat;
	double lng;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkin:
			getPresenter().doCheckInHttpRequest(
					addressName.getText().toString(), lat, lng);
			break;
		case R.id.mapImage:
			getPresenter().doPageChange();
			break;
		case R.id.link:
			getPresenter().doPageChange();
			break;

		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_checkin;
	}

	@Override
	public void changeCalendarUI() {
		Date date = new Date();
		calendar.addMark(date, R.drawable.calendar_bg_tag);
	}

	@Override
	public void onDestroy() {
		getPresenter().destroyLocationClient();
		super.onDestroy();
	}

	@Override
	public void changeToAmap() {
		openIdeaActivityForResult(FragmentEnum.FRAGMENT_MAP, 2);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void dateSet(Date mDate) {
		int year = mDate.getYear() + 1900;
		int month = mDate.getMonth() + 1;
		date.setText(year + "年" + month + "月");
		date.setTextSize(20);

	}

	@Override
	public void onPause() {
		getPresenter().stopLocation();
		super.onPause();
	}

	@Override
	public void onResume() {
		getPresenter().startLocation();
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2 && resultCode == 200) {
			Bundle extras = data.getExtras();
			String storeNameTxt = extras.getString("storeName");
			String addressNameTxt = extras.getString("addressName");
			this.lat = extras.getDouble("lat");
			this.lng = extras.getDouble("lng");
			storeName.setText(storeNameTxt);
			addressName.setText(addressNameTxt);
		}
	}

	@Override
	public void initLatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public void setCheckInButtonStyle() {
		mCheckIn.setClickable(false);
		AlphaAnimation mAnimation = new AlphaAnimation(1.0f, 0.3f);
		mAnimation.setDuration(30);
		mAnimation.setFillAfter(true);
		mCheckIn.startAnimation(mAnimation);
	}

	@Override
	public void addCalendarHistory(ArrayList<String> dateList) {
		calendar.addMarks(dateList, R.drawable.calendar_bg_tag);
	}

	@Override
	protected Presenter createPresenter() {
		return new CheckInPresenter(getActivity());
	}

	@Override
	public void RightOnClick() {

	}

}

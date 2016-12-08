package com.mo.mobileoffice.function.meeting.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.adapter.ViewHolder;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.mvp.MvpRadioFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.common.widget.DropDownMenu;
import com.mo.mobileoffice.function.meeting.bean.FloorBean;
import com.mo.mobileoffice.function.meeting.bean.RoomBean;
import com.mo.mobileoffice.function.meeting.bean.SearchInfo;
import com.mo.mobileoffice.function.meeting.contract.MeetingContract;
import com.mo.mobileoffice.function.meeting.contract.MeetingContract.Presenter;
import com.mo.mobileoffice.function.meeting.presenter.MeetingPresenter;

public class MeetingFragment extends MvpRadioFragment<MeetingContract.Presenter> implements OnItemClickListener, MeetingContract.View {
	private DropDownMenu mContainer;
	private GridView mGridView;
	private List<View> mTabViews;
	private TextView tv_search_day;
	private TextView tv_search_time;
	private TextView tv_min_time;
	private RadioGroup mProjectorRadioGroup;
	private RadioGroup mWIFIRadioGroup;
	private RadioGroup mAirConditioningRadioGroup;
	private ImageView iv_all;
	private ImageView iv_search_by_time;
	private LoadingDialog mDialog;
	
	private CommonAdapter<RoomBean> mRoomAdapter;
	private CommonAdapter<FloorBean> mFloorAdapter;

	private Calendar mCalendar = Calendar.getInstance();
	private int mMinTime = 40;
	private boolean isAllTime = true;
	private int mFoolrSelectedPos = 0;
	
	@Override
	protected Presenter createPresenter() {
		return new MeetingPresenter(getActivity());
	}

	@Override
	protected void init() {
		if (getPresenter() == null) {
			createPresenter();
		}
		mContainer = findViewById(R.id.ddm_container);
		getPresenter().initTabViews();
		getPresenter().initContainerView();
		mContainer.setDropDownMenu(getPresenter().getTabTexts(), mTabViews, mGridView);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			mContainer.closeMenu();
			getPresenter().searchByCondition(createSearchInfo());
			break;
		case R.id.tv_day:
			showDateDialog();
			break;
		case R.id.tv_start_time:
			showTimeDialog();
			break;
		case R.id.tv_min_time:
			showNumPick();
			break;
		case R.id.rl_all:
			isAllTime = true;
			refreshTimeView(isAllTime);
			break;
		case R.id.rl_search_by_time:
			isAllTime = false;
			refreshTimeView(isAllTime);
			break;
		default:
			break;
		}
	}
	
	/** 刷新时间pop的视图 **/
	private void refreshTimeView(boolean isAllTime) {
		iv_all.setVisibility(isAllTime ? View.VISIBLE : View.INVISIBLE);
		iv_search_by_time.setVisibility(!isAllTime ? View.VISIBLE : View.INVISIBLE);
		tv_search_day.setEnabled(!isAllTime);
		tv_search_time.setEnabled(!isAllTime);
		tv_min_time.setEnabled(!isAllTime);
	}
	
	/** 将筛选的信息封装成对象 **/
	private SearchInfo createSearchInfo() {
		SearchInfo info = new SearchInfo();
		info.floor_id = mFloorAdapter.getItem(mFoolrSelectedPos).getId();
		info.floor_num = mFloorAdapter.getItem(mFoolrSelectedPos).getFloor_num();
		info.isAll = isAllTime;
		info.time = mCalendar;
		info.minTime = mMinTime;
		for (int i = 0; i < 3; i ++) {
			if (mWIFIRadioGroup.getCheckedRadioButtonId() == mWIFIRadioGroup.getChildAt(i).getId()) {
				info.isWIFI = Integer.valueOf((String) mWIFIRadioGroup.getChildAt(i).getTag());
			}
			if (mAirConditioningRadioGroup.getCheckedRadioButtonId() == mAirConditioningRadioGroup.getChildAt(i).getId()) {
				info.isAir_Conditioner = Integer.valueOf((String) mAirConditioningRadioGroup.getChildAt(i).getTag());
			}
			if (mProjectorRadioGroup.getCheckedRadioButtonId() == mProjectorRadioGroup.getChildAt(i).getId()) {
				info.isProjector = Integer.valueOf((String) mProjectorRadioGroup.getChildAt(i).getTag());
			}
		}
 		return info;
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_meeting;
	}

	@Override
	public void RightOnClick() {
		openIdeaActivity(FragmentEnum.FRAGMENT_APPLY_MEETING);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent == mGridView) {
			Bundle bundle = new Bundle();
			bundle.putInt("room_id", mRoomAdapter.getItem(position).getId());
			openIdeaActivity(FragmentEnum.FRAGMENT_ROOM_DETAIL, bundle);
		}
		if (parent == mTabViews.get(0)) {
			mFoolrSelectedPos = position;
			mFloorAdapter.notifyDataSetChanged();
			mContainer.closeMenu();
			mContainer.setTabText(mFloorAdapter.getItem(mFoolrSelectedPos).getFloor_name());
			getPresenter().searchByCondition(createSearchInfo());
		}
	}

	@SuppressLint("InflateParams")
	@Override
	public void initTabViews(List<FloorBean> floorLists) {
		mFloorAdapter = new CommonAdapter<FloorBean>(getActivity(), floorLists, R.layout.listitem_dropdown_text) {

			@Override
			public void convert(ViewHolder holder, FloorBean item, int position) {
				holder.setText(R.id.tv_info, item.getFloor_name());
				if (mFoolrSelectedPos == position) {
					holder.setImageViewVisiable(R.id.iv_check, View.VISIBLE);
				} else {
					holder.setImageViewVisiable(R.id.iv_check, View.INVISIBLE);
				}
			}

		};
		mTabViews = new ArrayList<View>();

		ListView floor = new ListView(getActivity());
		floor.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		floor.setAdapter(mFloorAdapter);
		floor.setOnItemClickListener(this);
		mTabViews.add(floor);

		final View time = LayoutInflater.from(getActivity()).inflate(R.layout.layout_meeting_time, null);
		iv_all = (ImageView) time.findViewById(R.id.iv_all);
		iv_search_by_time = (ImageView) time.findViewById(R.id.iv_search_by_time);
		time.findViewById(R.id.rl_all).setOnClickListener(this);
		time.findViewById(R.id.rl_search_by_time).setOnClickListener(this);
		((Button) time.findViewById(R.id.btn_ok)).setOnClickListener(this);
		tv_search_day = (TextView) time.findViewById(R.id.tv_day);
		tv_search_time = (TextView) time.findViewById(R.id.tv_start_time);
		tv_min_time = (TextView) time.findViewById(R.id.tv_min_time);
		tv_search_day.setOnClickListener(this);
		tv_search_time.setOnClickListener(this);
		tv_min_time.setOnClickListener(this);
		tv_search_day.setText(getResources().getString(R.string.search_data, StringTool.DataToString2(mCalendar.getTime())));
		tv_search_time.setText(getResources().getString(R.string.search_time, 
				StringTool.to2DigitNum(mCalendar.get(Calendar.HOUR_OF_DAY)) + " : " 
				+ StringTool.to2DigitNum(mCalendar.get(Calendar.MINUTE))));
		tv_min_time.setText(getResources().getString(R.string.search_min_time, mMinTime));
		mTabViews.add(time);

		View more = LayoutInflater.from(getActivity()).inflate(R.layout.layout_meeting_more, null);
		((Button) more.findViewById(R.id.btn_ok)).setOnClickListener(this);
		mProjectorRadioGroup = (RadioGroup) more.findViewById(R.id.rg_projector);
		mWIFIRadioGroup = (RadioGroup) more.findViewById(R.id.rg_WIFI);
		mAirConditioningRadioGroup = (RadioGroup) more.findViewById(R.id.rg_air_conditioner);
		((RadioButton)mProjectorRadioGroup.getChildAt(0)).setChecked(true);
		((RadioButton)mWIFIRadioGroup.getChildAt(0)).setChecked(true);
		((RadioButton)mAirConditioningRadioGroup.getChildAt(0)).setChecked(true);
		mTabViews.add(more);
	}

	@Override
	public void initContainerView(List<RoomBean> roomLists) {
		mRoomAdapter = new CommonAdapter<RoomBean>(getActivity(), roomLists,
				R.layout.listitem_room) {

			@Override
			public void convert(ViewHolder holder, RoomBean item, int position) {
				holder.setText(R.id.tv_floor, getPresenter().searchFloorNameById(item.getFloor_id()));
				holder.setText(R.id.tv_room, item.getRoom_num());
				if (item.getWifi() == 1) {
					holder.setImageViewVisiable(R.id.iv_wifi, View.VISIBLE);
				}
				if (item.getAir_con() == 1) {
					holder.setImageViewVisiable(R.id.iv_air, View.VISIBLE);
				}
				if (item.getProjector() == 1) {
					holder.setImageViewVisiable(R.id.iv_pro, View.VISIBLE);
				}
			}
		};
		
		mGridView = new GridView(getActivity());
		mGridView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mGridView.setNumColumns(2);
		mGridView.setGravity(Gravity.CENTER);
		mGridView.setHorizontalSpacing(4);
		mGridView.setVerticalSpacing(4);
		mGridView.setPadding(4, 4, 4, 4);
		mGridView.setAdapter(mRoomAdapter);
		mGridView.setCacheColorHint(Color.TRANSPARENT);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void popupViewRefresh(String text) {
		mContainer.setTabText(text);
	}

	/** 弹出日期选择器 **/
	private void showDateDialog() {
		final DatePickerDialog mdialog = new DatePickerDialog(getActivity(),
				null, mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH));
		mdialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources()
				.getString(R.string.finish), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatePicker dataPicker = mdialog.getDatePicker();
				mCalendar.set(dataPicker.getYear(), dataPicker.getMonth(), dataPicker.getDayOfMonth());
				String tiemText = StringTool.transformDate(dataPicker.getYear(), dataPicker.getMonth() + 1, dataPicker.getDayOfMonth());
				String text = getResources().getString(R.string.search_data, tiemText);
				tv_search_day.setText(text);
				mdialog.dismiss();
			}
		});
		mdialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources()
				.getString(R.string.cancel), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mdialog.dismiss();
			}
		});
		mdialog.show();
	}

	private boolean isPosition = false;

	/** 弹出时间选择器 **/
	private void showTimeDialog() {
		isPosition = false;
		final TimePickerDialog mDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						if (isPosition) {
							mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
							mCalendar.set(Calendar.MINUTE, minute);
							String text = getResources().getString(R.string.search_time, StringTool.to2DigitNum(hourOfDay) + " : " + StringTool.to2DigitNum(minute));
							tv_search_time.setText(text);
						}
					}
				}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
		mDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.finish), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				isPosition = true;
				mDialog.dismiss();
			}
		});
		mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	/** 弹出数字选择器 **/
	private void showNumPick() {
		final NumberPicker picker = new NumberPicker(getActivity());
		picker.setMinValue(0);
		picker.setMaxValue(200);
		picker.setValue(mMinTime);
		new AlertDialog.Builder(getActivity()).setView(picker)
			.setPositiveButton(getResources().getString(R.string.ok), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mMinTime = picker.getValue();
					tv_min_time.setText(getResources().getString(R.string.search_min_time, mMinTime));
				}
			}).show();
	}

	@Override
	public void notifyViewUpdate() {
		mRoomAdapter.notifyDataSetChanged();
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



}

package com.mo.mobileoffice.function.approval.ui;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.approval.contract.LeaveContract;
import com.mo.mobileoffice.function.approval.contract.LeaveContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.LeavePresenter;

/**
 * 请假页
 * 
 * @author Paul
 * 
 */
public class LeaveFragment extends MvpIdeaFragment<LeaveContract.Presenter>
		implements LeaveContract.View {
	private ArrayList<String> mPicPathList;
	private final int MAX_PIC_COUNT = 3; // 最多上传图pain数量
	private final int REQUESTCODE_PHOTO = 2;

	private RelativeLayout picture;
	private GridLayout mGridLayout;
	private RelativeLayout typeSelect;
	private TextView typeName;
	private RelativeLayout mStartTime;
	private RelativeLayout mEndTime;
	private TextView mStart_time;
	private TextView mEnd_time;
	private EditText dayCount;
	private EditText cause;

	@Override
	protected void init() {
		picture = findViewById(R.id.picture);
		mGridLayout = findViewById(R.id.le_gl_pic);
		typeSelect = findViewById(R.id.type);
		typeName = findViewById(R.id.typename);
		mStartTime = findViewById(R.id.startime);
		mEndTime = findViewById(R.id.endtime);
		mStart_time = findViewById(R.id.start_time);
		mEnd_time = findViewById(R.id.end_time);
		dayCount = findViewById(R.id.dayCount);
		cause = findViewById(R.id.cause);
		picture.setOnClickListener(this);
		typeSelect.setOnClickListener(this);
		mStartTime.setOnClickListener(this);
		mEndTime.setOnClickListener(this);
		setTitle("我的请假");
		setRight("提交");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.picture:
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("pic_list", mPicPathList);
			bundle.putInt("pic_count", MAX_PIC_COUNT);
			openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_SELECTOR,
					REQUESTCODE_PHOTO, bundle);
			break;
		case R.id.type:
			getPresenter().showSingleDialog();
			break;
		case R.id.startime:
			getPresenter().showTimeDialog(R.id.startime);
			break;
		case R.id.endtime:
			getPresenter().showTimeDialog(R.id.endtime);
			break;
		default:
			break;
		}
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK
				&& requestCode == REQUESTCODE_PHOTO) {
			mPicPathList = data.getStringArrayListExtra("pic_list");
			notifyGridLayoutDraw();
		}
	}

	HashMap<String, Bitmap> bitmapLists = new HashMap<String, Bitmap>();

	@SuppressLint("InflateParams")
	private void notifyGridLayoutDraw() {
		mGridLayout.removeAllViews();
		if (bitmapLists != null && bitmapLists.size() > 0) {
			for (Entry<String, Bitmap> entry : bitmapLists.entrySet()) {
				bitmapLists.remove(entry.getValue());
			}
		}
		for (String path : mPicPathList) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.listitem_del_pic, null);
			ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			final ImageView iv_del = (ImageView) view.findViewById(R.id.iv_del);
			iv_del.setTag(path);
			iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String path = (String) iv_del.getTag();
					mPicPathList.remove(path);
					notifyGridLayoutDraw();
				}
			});
			bitmapLists.put(path, ImageTool.getCompressBitmap(path, 120, 120));
			iv_pic.setImageBitmap(bitmapLists.get(path));
			mGridLayout.addView(view);
		}
	}

	@Override
	public void rightOnClick() {
		if (checkCanSubmit()) {
			if (mPicPathList != null && mPicPathList.size() > 0) {
				String[] list = FileTool.getPicCompressPaths(FileTool
						.getStringArray(mPicPathList));
				File[] files = new File[mPicPathList.size()];
				String[] fileNmaes = new String[mPicPathList.size()];
				for (int i = 0; i < mPicPathList.size(); i++) {
					files[i] = new File(list[i]);
					fileNmaes[i] = files[i].getName();
				}
				getPresenter().doHttpSubmit(dayCount.getText().toString(),
						cause.getText().toString(),
						mStart_time.getText().toString(),
						mEnd_time.getText().toString(),
						typeName.getText().toString(), files, fileNmaes);
			} else {
				// 进行网络操作
				getPresenter().doHttpSubmit(dayCount.getText().toString(),
						cause.getText().toString(),
						mStart_time.getText().toString(),
						mEnd_time.getText().toString(),
						typeName.getText().toString(), null, null);
			}
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_leave;
	}

	@Override
	protected Presenter createPresenter() {
		return new LeavePresenter(getActivity());
	}

	String[] MulDialogList = new String[] { "请选择", "事假", "病假", "年假", "调休",
			"婚假", "产假", "陪产假", "路途假", "其他" };
	AlertDialog.Builder buider;
	int currentDialog = -1;

	@Override
	public void showSingleChoiceDialog() {
		buider = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(
				MulDialogList, currentDialog,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							currentDialog = -1;
							typeName.setText("请选择(必填)");
						} else {
							currentDialog = which;
							typeName.setText(MulDialogList[currentDialog]);
						}
						dialog.dismiss();
					}
				});
		buider.show();
	}

	private Calendar calendar = Calendar.getInstance();
	String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六" };

	@Override
	public void showTimeChoiceDialog(final int id) {
		String time = StringTool.DataToString2(new Date());
		time += " " + weeks[new Date().getDay()];
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_datetimedialog, null);
		final DatePicker mDatePicker = (DatePicker) view
				.findViewById(R.id.datePicker);
		final TimePicker mTimePicker = (TimePicker) view
				.findViewById(R.id.timepicker);
		mTimePicker.setIs24HourView(true);
		AlertDialog.Builder buider = new AlertDialog.Builder(getActivity())
				.setView(view).setTitle(time);
		buider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int year = mDatePicker.getYear();
				String month = (mDatePicker.getMonth() + 1) < 10 ? "0"
						+ (mDatePicker.getMonth() + 1) : ""
						+ (mDatePicker.getMonth() + 1);
				String day = mDatePicker.getDayOfMonth() < 10 ? "0"
						+ mDatePicker.getDayOfMonth() : ""
						+ mDatePicker.getDayOfMonth();
				int hour = mTimePicker.getCurrentHour();
				String currentHour = hour < 10 ? "0" + hour : "" + hour;
				int minute = mTimePicker.getCurrentMinute();
				String currentMinute = minute < 10 ? "0" + minute : "" + minute;
				String allTime = year + "-" + month + "-" + day + " "
						+ currentHour + ":" + currentMinute;
				if (id == R.id.startime) {
					mStart_time.setText(allTime);
				} else if (id == R.id.endtime) {
					mEnd_time.setText(allTime);
				}
				dialog.dismiss();
			}
		});
		buider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (id == R.id.startime) {
					mStart_time.setText("请选择(必填)");
				} else if (id == R.id.endtime) {
					mEnd_time.setText("请选择(必填)");
				}
				dialog.dismiss();
			}
		});
		buider.show();
	}

	@Override
	public boolean checkCanSubmit() {
		if (typeName.getText().toString().equals("请选择(必填)")) {
			ToastShow("请选择请假类型");
			return false;
		} else if (mStart_time.getText().toString().equals("请选择(必填)")) {
			ToastShow("请选择开始时间");
			return false;
		} else if (mEnd_time.getText().toString().equals("请选择(必填)")) {
			ToastShow("请选择结束时间");
			return false;
		} else if (mEnd_time.getText().toString().equals("请选择(必填)")) {
			ToastShow("请选择结束时间");
			return false;
		} else if (dayCount.getText().toString().equals("")) {
			ToastShow("请填写请假天数");
			return false;
		} else if (cause.getText().toString().equals("")) {
			ToastShow("请填写请假事由");
			return false;
		}
		boolean flag = false;
		try {
			flag = StringTool.compareTime(mStart_time.getText().toString(),
					mEnd_time.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!flag) {
			ToastShow("开始时间不能大于结束时间");
			return false;
		}
		int days = Integer.parseInt(dayCount.getText().toString());
		if (days <= 0) {
			ToastShow("输入请假天数不得小于0");
		}
		return true;
	}

	@Override
	public void showDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		mBuilder.setMessage("数据已成功发送");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					getActivity().finish();
			}
		});
		mBuilder.show();
	}

	private LoadingDialog mDialog = null;

	@Override
	public void showProgressDialog() {
		if (mDialog == null) {
			mDialog = new LoadingDialog(getActivity());
		}
		mDialog.show(false);
	}

	@Override
	public void dismissProgressDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	public void showErrorDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		mBuilder.setMessage("数据发送失败");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
			}
		});
		mBuilder.show();
	}

}

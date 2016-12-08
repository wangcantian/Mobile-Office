package com.mo.mobileoffice.function.approval.ui;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.helper.DBHelper;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.approval.contract.EvectionContract;
import com.mo.mobileoffice.function.approval.contract.EvectionContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.EvectionPresenter;

/**
 * 出差碎片
 * 
 * @author guo
 * 
 */

public class EvectionFragment extends
		MvpIdeaFragment<EvectionContract.Presenter> implements
		EvectionContract.View {
	private ArrayList<String> mPicPathList;
	private final int MAX_PIC_COUNT = 3; // 最多上传图pain数量
	private final int REQUESTCODE_PHOTO = 2;

	private RelativeLayout picture;
	private GridLayout mGridLayout;
	private LinearLayout container;
	private TextView addDetail;
	private RelativeLayout startTime;
	private RelativeLayout endTime;
	private TextView start_time;
	private TextView end_Time;
	private EditText ev_cause;
	private EditText evection_day;
	private EditText address;

	@Override
	protected void init() {
		picture = findViewById(R.id.ev_picture);
		mGridLayout = findViewById(R.id.ev_gl_pic);
		container = findViewById(R.id.ev_container);
		addDetail = findViewById(R.id.add_detail);
		startTime = findViewById(R.id.ev_startime);
		endTime = findViewById(R.id.ev_endtime);
		start_time = findViewById(R.id.evection_startTime);
		end_Time = findViewById(R.id.evection_endtime);
		ev_cause = findViewById(R.id.evection_cause);
		evection_day = findViewById(R.id.evection_day);
		address = findViewById(R.id.address);
		picture.setOnClickListener(this);
		addDetail.setOnClickListener(this);
		startTime.setOnClickListener(this);
		endTime.setOnClickListener(this);
		setTitle("我的出差");
		setRight("提交");
	}

	private ArrayList<View> viewLists = new ArrayList<>();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ev_picture:
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("pic_list", mPicPathList);
			bundle.putInt("pic_count", MAX_PIC_COUNT);
			openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_SELECTOR,
					REQUESTCODE_PHOTO, bundle);
			break;
		case R.id.add_detail:
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.include_evection_detail_view, null);
			view.findViewById(R.id.evection_del).setOnClickListener(this);
			view.findViewById(R.id.ev_startime).setOnClickListener(this);
			view.findViewById(R.id.ev_endtime).setOnClickListener(this);
			container.addView(view);
			viewLists.add(view);
			break;
		case R.id.evection_del:
			container.removeView((View) (v.getParent().getParent()));
			viewLists.remove((View) (v.getParent().getParent()));
			break;
		case R.id.ev_startime:
			RelativeLayout parent = (RelativeLayout) v;
			TextView textView = (TextView) parent.getChildAt(1);
			getPresenter().showTimeDialog(textView, R.id.ev_startime);
			break;
		case R.id.ev_endtime:
			RelativeLayout endParent = (RelativeLayout) v;
			TextView endTv = (TextView) endParent.getChildAt(1);
			getPresenter().showTimeDialog(endTv, R.id.ev_endtime);
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

	HashMap<String, Bitmap> bitmapLists = new HashMap();

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
				File[] files = new File[list.length];
				String[] fileNames = new String[mPicPathList.size()];
				for (int i = 0; i < list.length; i++) {
					File file = new File(list[i]);
					files[i] = file;
					fileNames[i] = file.getName();
				}
				getPresenter().dohttpSubmit(address, start_time, end_Time,
						evection_day, ev_cause, viewLists, files, fileNames);
			} else {
				getPresenter().dohttpSubmit(address, start_time, end_Time,
						evection_day, ev_cause, viewLists, null, null);
			}
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_evection;
	}

	@Override
	protected Presenter createPresenter() {
		return new EvectionPresenter(getActivity());
	}

	String[] weeks = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六" };

	@Override
	public void showTimeChoiceDialog(final TextView tv, final int id) {
		String time = StringTool.DataToString2(new Date());
		time += " " + weeks[new Date().getDay()];
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_evection_timedialog, null);
		final DatePicker mDatePicker = (DatePicker) view
				.findViewById(R.id.evection_datePicker);
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
				String allTime = year + "-" + month + "-" + day;
				if (id == R.id.ev_startime) {
					// start_time.setText(allTime);
					tv.setText(allTime);
				} else if (id == R.id.ev_endtime) {
					// end_Time.setText(allTime);
					tv.setText(allTime);
				}
				dialog.dismiss();
			}

		});

		buider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (id == R.id.startime) {
					tv.setText("请选择(必填)");
				} else if (id == R.id.endtime) {
					tv.setText("请选择(必填)");
				}
				dialog.dismiss();
			}
		});
		buider.show();
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

	private LoadingDialog mDialog;

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
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
		mBuilder.show();
	}

	// 暂时以文字的形式写
	public boolean checkCanSubmit() {
		for (int i = 0; i < viewLists.size(); i++) {
			if (!isViewNull(viewLists.get(i))) {
				return false;
			}
		}
		if (address.getText().toString().trim().equals("")) {
			ToastShow("地点不能为空");
			return false;
		} else if (start_time.getText().toString().trim().equals("请选择(必填)")) {
			ToastShow("请选择开始时间");
			return false;
		} else if (end_Time.getText().toString().trim().equals("请选择(必填)")) {
			ToastShow("请选择结束时间");
			return false;
		} else if (evection_day.getText().toString().equals("")) {
			ToastShow("请填写请假天数");
			return false;
		} else if (Integer.parseInt(evection_day.getText().toString()) < 0) {
			ToastShow("天数不得小于0");
			return false;
		} else if (ev_cause.getText().toString().equals("")) {
			ToastShow("请输入请假事由");
			return false;
		}

		boolean flag = false;
		try {
			flag = StringTool.compareTime1(start_time.getText().toString(),
					end_Time.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!flag) {
			ToastShow("开始时间不能大于结束时间");
			return false;
		}

		return true;
	}

	public boolean isViewNull(View view) {
		TextView starTime = (TextView) view.findViewById(R.id.start_time);
		TextView endTime = (TextView) view.findViewById(R.id.end_time);
		EditText editText = (EditText) view.findViewById(R.id.address);
		if (editText.getText().toString().trim().equals("")) {
			ToastShow("地点不能为空");
			return false;
		} else if (starTime.getText().toString().trim().equals("请选择(必填)")) {
			ToastShow("请选择开始时间");
			return false;
		} else if (endTime.getText().toString().trim().equals("请选择(必填)")) {
			ToastShow("请选择结束时间");
			return false;
		}
		boolean flag = false;
		try {
			flag = StringTool.compareTime1(starTime.getText().toString(),
					endTime.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!flag) {
			ToastShow("开始时间不能大于结束时间");
			return false;
		}
		return true;
	}

}

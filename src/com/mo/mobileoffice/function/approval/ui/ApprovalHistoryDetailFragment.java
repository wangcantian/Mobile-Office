package com.mo.mobileoffice.function.approval.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.function.approval.bean.HistoryApproval_Respond.HistoryData;
import com.mo.mobileoffice.function.approval.contract.ApprovalHistoryDetailContract;
import com.mo.mobileoffice.function.approval.contract.ApprovalHistoryDetailContract.Presenter;
import com.mo.mobileoffice.function.approval.presenter.ApprovalHistoryDetailPresenter;
import com.squareup.picasso.Picasso;

public class ApprovalHistoryDetailFragment extends
		MvpIdeaFragment<ApprovalHistoryDetailContract.Presenter> implements
		ApprovalHistoryDetailContract.View {

	@Override
	public void onClick(View v) {

	}

	private TextView userName;
	private TextView appType;
	private TextView appState;
	private TextView app_Type;
	private LinearLayout appContainer;
	private TextView appPlace;
	private TextView appStartTime;
	private TextView appEndTime;
	private TextView appCause;
	private TextView appDay;
	private TextView appCost;
	private TextView costDetail;
	private TextView costTotal;
	private TextView app_Time;
	private TextView app_State;
	private TextView app_View;
	private GridLayout leave_imageContainer;

	@Override
	protected void init() {
		if (data.getApp_type().equals("1")) {
			userName = findViewById(R.id.resummerName);
			appType = findViewById(R.id.resumeType);
			appState = findViewById(R.id.resume_state);
			app_Type = findViewById(R.id.leave_type);
			appStartTime = findViewById(R.id.leave_start);
			appEndTime = findViewById(R.id.leave_end);
			appDay = findViewById(R.id.leave_day);
			appCause = findViewById(R.id.leave_cause);
			app_Time = findViewById(R.id.app_time);
			app_State = findViewById(R.id.app_state);
			app_View = findViewById(R.id.app_view);
			leave_imageContainer = findViewById(R.id.leave_imageContainer);

		} else if (data.getApp_type().equals("2")) {
			userName = findViewById(R.id.resummerName);
			appType = findViewById(R.id.resumeType);
			appState = findViewById(R.id.resume_state);
			appContainer = findViewById(R.id.detail_container);
			appCost = findViewById(R.id.resuim_cost);
			app_Type = findViewById(R.id.resuim_type);
			costDetail = findViewById(R.id.resuim_cost_detail);
			costTotal = findViewById(R.id.remui_total_cost);
			app_Time = findViewById(R.id.app_time);
			app_State = findViewById(R.id.app_state);
			app_View = findViewById(R.id.app_view);
			leave_imageContainer = findViewById(R.id.leave_imageContainer);
		} else {
			userName = findViewById(R.id.resummerName);
			appType = findViewById(R.id.resumeType);
			appState = findViewById(R.id.resume_state);
			appContainer = findViewById(R.id.re_container);
			appPlace = findViewById(R.id.re_place);
			appStartTime = findViewById(R.id.re_start_time);
			appEndTime = findViewById(R.id.re_end_time);
			appDay = findViewById(R.id.re_day);
			appCause = findViewById(R.id.re_cause);
			app_Time = findViewById(R.id.app_time);
			app_State = findViewById(R.id.app_state);
			app_View = findViewById(R.id.app_view);
			leave_imageContainer = findViewById(R.id.leave_imageContainer);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (data.getApp_type().equals("1")) {
			userName.setText(data.getUser_name());
			appType.setText(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			Log.e("test", data.getState());
//			appState.setText("未完成审批");
			appState.setText("审批通过");
			app_Type.setText(data.getType());
			appStartTime.setText(data.getStart());
			appEndTime.setText(data.getEnd());
			appDay.setText(data.getDay());
			appCause.setText(data.getReason());
			app_Time.setText(data.getApp_time());
			app_State.setText(stateArr[Integer.parseInt(data.getState()) - 1]);
			app_View.setText(data.getView());

		} else if (data.getApp_type().equals("2")) {
			userName.setText(data.getUser_name());
			appType.setText(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			Log.e("test", data.getState());
//			appState.setText("未完成审批");
			appState.setText("审批通过");
			appCost.setText(data.getExplain().get(0).getMoney());
			app_Type.setText(data.getExplain().get(0).getType());
			costDetail.setText(data.getExplain().get(0).getDetail());
			costTotal.setText(data.getTotal());
			for (int i = 1; i < data.getExplain().size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.layout_approval_detail_item, null);
				TextView appCost = (TextView) view
						.findViewById(R.id.resuim_cost);
				TextView app_Type = (TextView) view
						.findViewById(R.id.resuim_type);
				TextView costDetail = (TextView) view
						.findViewById(R.id.resuim_cost_detail);
				appCost.setText(data.getExplain().get(i).getMoney());
				app_Type.setText(data.getExplain().get(i).getType());
				costDetail.setText(data.getExplain().get(i).getDetail());
				appContainer.addView(view);
			}
			app_Time.setText(data.getApp_time());
			app_State.setText(stateArr[Integer.parseInt(data.getState()) - 1]);
			app_View.setText(data.getView());
		} else {
			userName.setText(data.getUser_name());
			appType.setText(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			appState.setText("审批通过");
			appPlace.setText(data.getExplain().get(0).getPlace());
			appStartTime.setText(data.getExplain().get(0).getStart());
			appEndTime.setText(data.getExplain().get(0).getEnd());
			appDay.setText(data.getDay());
			appCause.setText(data.getReason());
			for (int i = 1; i < data.getExplain().size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.layout_approval_evection_detail_item, null);

				TextView appPlace = (TextView) view.findViewById(R.id.re_place);
				TextView appStartTime = (TextView) view
						.findViewById(R.id.re_start_time);
				TextView appEndTime = (TextView) view
						.findViewById(R.id.re_end_time);
				appPlace.setText(data.getExplain().get(i).getPlace());
				appStartTime.setText(data.getExplain().get(i).getStart());
				appEndTime.setText(data.getExplain().get(i).getEnd());
				appContainer.addView(view);
			}
			app_Time.setText(data.getApp_time());
			app_State.setText(stateArr[Integer.parseInt(data.getState()) - 1]);
			app_View.setText(data.getView());
		}

		if (leave_imageContainer.getChildCount() == 0) {

			String mUrls = data.getPic_url();
			if (mUrls != null && !mUrls.equals("")) {
				arrImages = mUrls.split(",");
			}
			if (arrImages != null && arrImages.length > 0) {
				for (int i = 0; i < arrImages.length; i++) {
					ImageView imageView = new ImageView(getActivity());
					int width = DisplayTool.px2dp(getActivity(), 200);
					int height = DisplayTool.px2dp(getActivity(), 200);
					final String url = arrImages[i];
					list.add(url);
					GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
					lp.width = width;
					lp.height = height;
					if (i == 0) {
						lp.leftMargin = DisplayTool.px2dp(getActivity(), 20);
					}
					lp.topMargin = DisplayTool.px2dp(getActivity(), 20);
					lp.rightMargin = DisplayTool.px2dp(getActivity(), 20);
					imageView.setLayoutParams(lp);
					imageView.setTag("" + i);
					Picasso.with(getActivity()).load(arrImages[i])
							.resize(width, height).centerCrop().into(imageView);
					leave_imageContainer.addView(imageView);
					imageView.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (list.size() > 0) {
								Bundle bundle = new Bundle();
								bundle.putStringArrayList("imageurl", list);
								bundle.putInt("imageId",
										Integer.parseInt((String) v.getTag()));
								openIdeaActivity(
										FragmentEnum.FRAGMENT_APPROVALDETAIL_IMAGE_LOOK,
										bundle);
							}
						}
					});
				}
			}
		}

	}

	ArrayList<String> list = new ArrayList<>();
	String[] arrImages = null;

	private HistoryData data;
	private String[] arr = new String[] { "的请假申请", "的报销申请", "的出差申请" };
	private String[] stateArr = new String[] { "同意", "拒絶" };

	@Override
	protected int setContentViewId() {
		Bundle bundle = getIntent().getExtras();
		data = (HistoryData) bundle.getSerializable("historyData");
		if (data.getApp_type().equals("1")) {
			setTitle(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			return R.layout.layout_approval_leavehisdetail;
		} else if (data.getApp_type().equals("2")) {
			setTitle(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			return R.layout.layout_approval_resuimhisdetail;
		} else {
			setTitle(data.getUser_name()
					+ arr[Integer.parseInt(data.getApp_type()) - 1]);
			return R.layout.layout_approval_hisdetail;
		}
	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected void setTitle(String title) {
		super.setTitle(title);
	}

	@Override
	protected Presenter createPresenter() {
		return new ApprovalHistoryDetailPresenter(getActivity());
	}

}
